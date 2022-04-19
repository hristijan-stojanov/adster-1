package mk.ukim.finki.wpproject.model.exceptions;

public class ITEquipmentNotFoundException extends RuntimeException{
    public ITEquipmentNotFoundException (Long id){
        super(String.format("IT equipment with id: %d not found", id));
    }
}
