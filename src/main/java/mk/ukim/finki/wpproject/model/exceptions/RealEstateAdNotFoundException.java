package mk.ukim.finki.wpproject.model.exceptions;

public class RealEstateAdNotFoundException extends RuntimeException{
    public RealEstateAdNotFoundException(Long id){
        super(String.format("Real estate with id: %d not found", id));
    }
}
