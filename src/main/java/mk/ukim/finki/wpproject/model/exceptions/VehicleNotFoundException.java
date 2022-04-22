package mk.ukim.finki.wpproject.model.exceptions;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException (Long id){
        super(String.format("Vehicle with id: %d not found", id));
    }
}
