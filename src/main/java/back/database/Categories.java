package back.database;

public enum Categories {
    KRAJ("KRAJ"),
    ZWIERZE("ZWIERZE"),
    MIEJSCE("MIEJSCE"),
    ZAWOD("ZAWOD"),
    SPORT("SPORT"),
    TRANSPORT("TRANSPORT"),
    AKTOR("AKTOR"),
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
}
