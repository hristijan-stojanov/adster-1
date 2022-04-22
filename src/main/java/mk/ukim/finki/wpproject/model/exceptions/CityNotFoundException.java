package mk.ukim.finki.wpproject.model.exceptions;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(String id) {
        super(String.format("City %s was not found",id));
    }
}
