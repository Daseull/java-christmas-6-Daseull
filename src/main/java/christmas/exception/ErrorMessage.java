package christmas.exception;

public enum ErrorMessage {
    INVALID_ORDER_MESSAGE("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    INVALID_DATE_MESSAGE("유효하지 않은 날짜입니다. 다시 입력해 주세요.");

    private final String prefix = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = prefix + message;
    }

    public String message() {
        return message;
    }
}
