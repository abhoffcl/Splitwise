package Dev.Abhishek.Splitwise.exception;

public class InvalidUserCredentials extends RuntimeException{
    public InvalidUserCredentials(String message) {
        super(message);
    }
}
