package Dev.Abhishek.Splitwise.service.strategy;

import Dev.Abhishek.Splitwise.dto.UserAmount;
import Dev.Abhishek.Splitwise.entity.*;

import java.util.*;

public class MinimumTransactionSettlementStrategy implements SettleUpStrategy{
    @Override
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
                // borrower will give money to lendor to settle transaction
                if (lendorAmount > borrowerAmount) {
                    settlementTransactions.add(new SettlementTransaction(borrowerAmount, lendor.getUser(), borrower.getUser()));
                    lendor.setAmount(lendorAmount + borrowerAmount);
                    maxHeap.add(lendor);
                } else if (lendorAmount < borrowerAmount) {
                    settlementTransactions.add(new SettlementTransaction(lendorAmount, lendor.getUser(), borrower.getUser()));
                    borrower.setAmount(lendorAmount + borrowerAmount);
                    minHeap.add(borrower);
                } else {
                    System.out.println("Do Nothing ,both are equal");
                    settlementTransactions.add(new SettlementTransaction(lendorAmount, lendor.getUser(), borrower.getUser()));
                }
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
