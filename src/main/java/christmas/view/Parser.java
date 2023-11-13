package christmas.view;

import java.util.Arrays;
import java.util.List;

public class Parser {

    public static int parseDate(String input) {
        return Integer.parseInt(input);
    }

    public static List<String> parseMenus(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter)).toList();
    }
}
