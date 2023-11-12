package christmas.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BadgeTest {

    @DisplayName("혜택금액에 따른 배지 테스트")
    @ParameterizedTest
    @CsvSource({"4999,NONE", "5000,STAR", "9999,STAR", "10000,TREE", "19999,TREE", "20000,SANTA"})
    void fromBenefit(int benefit, String name) {
        Assertions.assertThat(Badge.fromBenefit(benefit).name())
                .isEqualTo(name);
    }
}
