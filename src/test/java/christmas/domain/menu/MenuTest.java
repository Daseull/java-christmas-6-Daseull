package christmas.domain.menu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuTest {

    @ParameterizedTest
    @CsvSource(value = {"양송이수프,true", "양송이 수프,false"})
    void exists(String menu, boolean expected) {
        Assertions.assertThat(Menu.exists(menu)).isEqualTo(expected);
    }
}
