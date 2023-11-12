package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.Constant;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DateTest {

    @DisplayName("날짜 도메인 생성 테스트")
    @Test
    void create() {
        IntStream.range(Constant.MIN_DATE, Constant.MAX_DATE + 1)
                .forEach(date -> assertDoesNotThrow(() -> new Date(date)));
    }

    @DisplayName("날짜 도메인_범위 밖의 날짜면 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {0, 32})
    void createOutRange(int date) {
        assertThatThrownBy(() -> new Date(date))
                .isInstanceOf(PlannerException.class)
                .hasMessage(ErrorMessage.INVALID_DATE_MESSAGE.message());
    }
}
