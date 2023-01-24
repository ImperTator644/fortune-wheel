package com.fortunewheel.frontend;

import java.util.Random;

public enum WheelSection {

    WHEEL_300_1("5"),
    WHEEL_300_2("11"),
    WHEEL_300_3("14"),
    WHEEL_300_4("16"),
    WHEEL_300_5("21"),
    WHEEL_350("8"),
    WHEEL_400_1("4"),
    WHEEL_400_2("20"),
    WHEEL_450("9"),
    WHEEL_500_1("17"),
    WHEEL_500_2("23"),
    WHEEL_550("19"),
    WHEEL_600_1("3"),
    WHEEL_600_2("12"),
    WHEEL_600_3("15"),
    WHEEL_700("10"),
    WHEEL_800("7"),
    WHEEL_900_1("1"),
    WHEEL_900_2("22"),
    WHEEL_2500("13"),
    WHEEL_BANKRUPT("2"),
    WHEEL_BANKRUPT_1000("18"),
    WHEEL_FREE_SPIN("24"),
    WHEEL_LOSE_TURN("6");

    private static final Random rng = new Random();

    private String name;
    WheelSection(String n) {
        name = n;
    }
    String getName() {
        return name;
    }

    public static WheelSection wheelSpin() {
        WheelSection[] sections = values();
        return sections[rng.nextInt(sections.length)];
    }
}
