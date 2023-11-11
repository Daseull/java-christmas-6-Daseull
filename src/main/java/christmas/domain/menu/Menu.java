package christmas.domain.menu;

import static christmas.domain.menu.Category.APPETIZER;
import static christmas.domain.menu.Category.BEVERAGE;
import static christmas.domain.menu.Category.DESSERT;
import static christmas.domain.menu.Category.MAIN;

import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP(APPETIZER, "양송이수프", 6_000),
    TAPAS(APPETIZER, "타파스", 5_500),
    CAESAR_SALAD(APPETIZER, "시저샐러드", 8_000),

    T_BONE_STEAK(MAIN, "티본스테이크", 55_000),
    BARBECUE_RIBS(MAIN, "바비큐립", 54_000),
    SEAFOOD_PASTA(MAIN, "해산물파스타", 35_000),
    CHRISTMAS_PASTA(MAIN, "크리스마스파스타", 25_000),

    CHOCOLATE_CAKE(DESSERT, "초코케이크", 15_000),
    ICE_CREAM(DESSERT, "아이스크림", 5_000),

    ZERO_COLA(BEVERAGE, "제로콜라", 3_000),
    RED_WINE(BEVERAGE, "레드와인", 60_000),
    CHAMPAGNE(BEVERAGE, "샴페인", 25_000);

    private final Category category;
    private final String description;
    private final int price;

    Menu(Category category, String description, int price) {
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public static boolean exists(String description) {
        return Arrays.stream(Menu.values())
                .anyMatch(m -> description.equals(m.description()));
    }

    public Category category() {
        return category;
    }

    public String description() {
        return description;
    }

    public int price() {
        return price;
    }
}
