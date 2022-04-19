package mk.ukim.finki.wpproject.model.exceptions;

public class AdNotFoundException extends RuntimeException{
    public AdNotFoundException(Long id){
        super(String.format("Ad with id: %d not found", id));
    }
}
