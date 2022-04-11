package mk.ukim.finki.wpproject.model.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException (Long id){
        super(String.format("Category with id: %d is not found", id));
    }
}
