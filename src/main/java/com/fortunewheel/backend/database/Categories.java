package com.fortunewheel.backend.database;

public enum Categories {
    KRAJ("KRAJ"),
    ZWIERZE("ZWIERZE"),
    MIEJSCE("MIEJSCE"),
    ZAWOD("ZAWOD"),
    SPORT("SPORT"),
    TRANSPORT("TRANSPORT"),
    ELEKTRONIKA("ELEKTRONIKA"),
    EPOKA("EPOKA"),
    FILM("FILM"),
    INSTRUMENT("INSTRUMENT"),
    JEZYK("JEZYK");

    private final String category;

    Categories(String function){
        this.category = function;
    }

    public String getCategory() {
        return category;
    }

    public static String[] getAllCategories(){
        Categories[] values = values();
        String[] categories = new String[values.length];
        for (int i = 0;i< values.length;i++){
            categories[i] = values[i].getCategory();
        }
        return categories;
    }
}
