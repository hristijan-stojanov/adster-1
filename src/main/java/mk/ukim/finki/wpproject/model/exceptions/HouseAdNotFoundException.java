package mk.ukim.finki.wpproject.model.exceptions;

public class HouseAdNotFoundException extends RuntimeException{
    public HouseAdNotFoundException(Long houseAdId){
        super(String.format("House ad with id: %d not found", houseAdId));
    }
}
