package Dev.Abhishek.Splitwise.exception;

public class UserExpenseNotFoundException extends RuntimeException{
    public UserExpenseNotFoundException(String message) {
        super(message);
    }
}
