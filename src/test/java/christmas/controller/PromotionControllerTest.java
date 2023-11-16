package christmas.controller;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionControllerTest extends NsTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void 모든_타이틀_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!",
                    "<주문 메뉴>",
                    "<할인 전 총주문 금액>",
                    "<증정 메뉴>",
                    "<혜택 내역>",
                    "<총혜택 금액>",
                    "<할인 후 예상 결제 금액>",
                    "<12월 이벤트 배지>"
            );
        });
    }

    @Test
    void 헤택_내역_있음_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "크리스마스 디데이 할인",
                    "평일 할인",
                    "특별 할인",
                    "증정 이벤트"
            );
        });
    }

    @Test
    void 혜택_내역_없음_출력() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains(
                    "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!",
                    "<혜택 내역>" + LINE_SEPARATOR + "없음"
            );
        });
    }

    @Test
    void 금액_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "<할인 전 총주문 금액>" + LINE_SEPARATOR + "142,000원",
                    "<증정 메뉴>" + LINE_SEPARATOR + "샴페인 1개",
                    "크리스마스 디데이 할인: -1,200원",
                    "평일 할인: -4,046원",
                    "특별 할인: -1,000원",
                    "증정 이벤트: -25,000원",
                    "<할인 후 예상 결제 금액>" + LINE_SEPARATOR + "135,754원"
            );
        });
    }


    @ParameterizedTest
    @ValueSource(strings = {"a", "0", "32", " "})
    void 날짜_예외_테스트(String date) {
        assertSimpleTest(() -> {
            runException(date);
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "제로콜라-a", "없는메뉴-2", "아이스크림-1,아이스크림-2", "샴페인-2,제로콜라-1", "티본스테이크-10,샴페인-10,제로콜라-10"})
    void 주문_예외_테스트(String menus) {
        assertSimpleTest(() -> {
            runException("3", menus);
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @Override
    protected void runMain() {
        PromotionController promotionController = new PromotionController();
        promotionController.run();
    }
}
