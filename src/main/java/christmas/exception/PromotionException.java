package christmas.exception;

public class PromotionException extends IllegalArgumentException {
    public PromotionException(ErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
