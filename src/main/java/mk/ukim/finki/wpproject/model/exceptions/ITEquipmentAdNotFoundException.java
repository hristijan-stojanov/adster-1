package mk.ukim.finki.wpproject.model.exceptions;

public class ITEquipmentAdNotFoundException extends RuntimeException{
    public ITEquipmentAdNotFoundException(Long id){
        super(String.format("IT equipment with id: %d not found", id));
    }
}
