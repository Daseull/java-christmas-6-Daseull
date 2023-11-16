package christmas.controller;

import christmas.dto.MenuCount;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static int parseDate(String input) {
        return Integer.parseInt(input);
    }

    public static List<String> parseMenus(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter)).toList();
    }

    public static MenuCount parseMenu(String input, String delimiter) {
        String[] split = input.split(delimiter);
        return new MenuCount(split[0], Integer.parseInt(split[1]));
    }
}
