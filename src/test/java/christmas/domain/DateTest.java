package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.Constant;
import christmas.exception.ErrorMessage;
import christmas.exception.PlannerException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @DisplayName("날짜 -> 요일 테스트")
    @ParameterizedTest
    @CsvSource({"1,FRIDAY", "25,MONDAY", "31,SUNDAY"})
    void dayOfWeek(int date, String expected) {
        DayOfWeek dayOfWeek = new Date(date).dayOfWeek();

        assertThat(dayOfWeek.name()).isEqualTo(expected);
    }

    @DisplayName("특정날들에 해당 날짜가 포함되는지 테스트")
    @ParameterizedTest
    @CsvSource({"1,true", "25,false"})
    void isIncluded(int dateSource, boolean expected) {
        Date date = new Date(dateSource);
        List<Integer> dates = List.of(1, 2);

        assertThat(date.isIncluded(dates)).isEqualTo(expected);
    }

    @DisplayName("특정 기간에 해당날짜가 포함되는지 테스트")
    @ParameterizedTest
    @CsvSource({"1,true", "26,false"})
    void isInRange(int dateSource, boolean expected) {
        Date date = new Date(dateSource);

        assertThat(date.isInRange(1, 25))
                .isEqualTo(expected);
    }

    @DisplayName("해당 날짜가 특정날로부터 몇번째 날인지 구하는 기능 테스트")
    @ParameterizedTest
    @CsvSource({"1,1,0", "31,25,6"})
    void isInRange(int dateSource, int from, int expectedDay) {
        Date date = new Date(dateSource);

        assertThat(date.dayFromDate(from))
                .isEqualTo(expectedDay);
    }
}
