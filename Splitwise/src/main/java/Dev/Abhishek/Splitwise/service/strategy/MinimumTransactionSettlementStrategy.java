package Dev.Abhishek.Splitwise.service.strategy;

import Dev.Abhishek.Splitwise.dto.UserAmount;
import Dev.Abhishek.Splitwise.entity.*;
import Dev.Abhishek.Splitwise.repository.ExpenseRepository;
import Dev.Abhishek.Splitwise.repository.SettlementTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Component
public class MinimumTransactionSettlementStrategy implements SettleUpStrategy{
    private SettlementTransactionRepository settlementTransactionRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    public MinimumTransactionSettlementStrategy(SettlementTransactionRepository settlementTransactionRepsository, ExpenseRepository expenseRepository) {
        this.settlementTransactionRepository = settlementTransactionRepsository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional
    public List<SettlementTransaction> settleUp(List<Expense> expenses) {
        List<SettlementTransaction>settlementTransactions=new ArrayList<>();
        if(expenses!=null) {

            HashMap<User, Double> expenseMap = getOutstandingBalance(expenses);
            Comparator<UserAmount> minHeapComparator = Comparator.comparingDouble(UserAmount::getAmount);
            Comparator<UserAmount> maxHeapComparator = Comparator.comparingDouble(UserAmount::getAmount).reversed();
            PriorityQueue<UserAmount> minHeap = new PriorityQueue(minHeapComparator);
            PriorityQueue<UserAmount> maxHeap = new PriorityQueue(maxHeapComparator);
            expenseMap.entrySet().
                    stream().
                    forEach(entry -> {
                        User user = entry.getKey();
                        Double amount = entry.getValue();
                        if (amount < 0)
                            minHeap.add(new UserAmount(user, amount));
                        else if (amount > 0)
                            maxHeap.add(new UserAmount(user, amount));
                        else
                            System.out.println(user.getName() + " does not have to participate ");
                    });

            while (!maxHeap.isEmpty() && !minHeap.isEmpty()) {
                UserAmount borrower = minHeap.poll();
                UserAmount lendor = maxHeap.poll();
                double borrowerAmount = Math.abs(borrower.getAmount());
                double lendorAmount = lendor.getAmount();
                SettlementTransaction settlementTransaction;
                // borrower will give money to lendor to settle transaction
                if (lendorAmount > borrowerAmount) {
                  settlementTransaction = new SettlementTransaction(borrowerAmount, lendor.getUser(), borrower.getUser(), Instant.now());
                    lendor.setAmount(lendorAmount - borrowerAmount);
                    maxHeap.add(lendor);
                } else if (lendorAmount < borrowerAmount) {
                  settlementTransaction =new SettlementTransaction(lendorAmount, lendor.getUser(), borrower.getUser(),Instant.now());
                    borrower.setAmount(lendorAmount - borrowerAmount);
                    minHeap.add(borrower);
                } else {
                    System.out.println("Do Nothing ,both are equal");
                     settlementTransaction = new SettlementTransaction(lendorAmount, lendor.getUser(), borrower.getUser(),Instant.now());
                }

                settlementTransactions.add(settlementTransaction);
                settlementTransactionRepository.save(settlementTransaction);

            }
            // Mark all expenses as settled and save them individually
            for (Expense expense : expenses) {
                expense.setSettled(true);
                expenseRepository.save(expense);
            }
        }
        return settlementTransactions;
    }
    public HashMap<User,Double>getOutstandingBalance(List<Expense> expenses){
        HashMap<User,Double>map=new HashMap<>();
        expenses.stream().
                forEach(expense ->  {expense.getUserExpenses().
                        forEach(userExpense->{
                            User participant= userExpense.getUser();
                            double amount = userExpense.getAmount();
                            map.merge(participant,userExpense.getType().equals(UserExpenseType.PAID)?amount:-amount,Double::sum);
                        });
                });
        return map;
    }

}
