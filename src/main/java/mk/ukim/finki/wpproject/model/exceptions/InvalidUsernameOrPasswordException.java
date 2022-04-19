package mk.ukim.finki.wpproject.model.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException{
    public InvalidUsernameOrPasswordException(){
        super("Invalid username or password exception");
    }
}
