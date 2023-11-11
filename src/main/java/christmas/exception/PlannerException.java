package christmas.exception;

public class PlannerException extends IllegalArgumentException {
    public PlannerException(ErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
