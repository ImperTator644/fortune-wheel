package back.game;

public enum Functions {
    LETTER("LETTER"),
    KEY("KEY"),
    SPIN("SPIN"),
    BLOCK("BLOCK"),
    UNBLOCK("UNBLOCK"),
    SETUP_ROUND("SETUP_ROUND"),
    INFO("INFO");

    private final String function;

    Functions(String function){
        this.function = function;
    }

    public String getFunction() {
        return function;
    }
}
