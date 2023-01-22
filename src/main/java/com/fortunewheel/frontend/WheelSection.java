package com.fortunewheel.frontend;

import java.util.Random;

public enum WheelSection {

    WHEEL_300_1("1"),
    WHEEL_300_2("2"),
    WHEEL_300_3("3"),
    WHEEL_300_4("4"),
    WHEEL_300_5("5"),
    WHEEL_350("6"),
    WHEEL_400_1("7"),
    WHEEL_400_2("8"),
    WHEEL_450("9"),
    WHEEL_500_1("10"),
    WHEEL_500_2("11"),
    WHEEL_550("12"),
    WHEEL_600_1("13"),
    WHEEL_600_2("14"),
    WHEEL_600_3("15"),
    WHEEL_700("16"),
    WHEEL_800("17"),
    WHEEL_900_1("18"),
    WHEEL_900_2("19"),
    WHEEL_2500("20"),
    WHEEL_BANKRUPT("21"),
    WHEEL_BANKRUPT_1000("22"),
    WHEEL_FREE_SPIN("23"),
    WHEEL_LOSE_TURN("24");

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
