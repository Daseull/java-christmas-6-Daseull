package christmas.domain;

import java.util.Arrays;

public enum Badge {
    SANTA(-20_000, "산타"),
    TREE(-10_000, "트리"),
    STAR(-5_000, "별"),
    NONE(0, "없음");

    private final int benefit;
    private final String displayName;

    Badge(int benefit, String displayName) {
        this.benefit = benefit;
        this.displayName = displayName;
    }

    public static Badge fromBenefit(int benefit) {
        return Arrays.stream(Badge.values())
                .filter(b -> benefit <= b.benefit)
                .findFirst()
                .orElse(NONE);
    }

    public String displayName() {
        return displayName;
    }
}
