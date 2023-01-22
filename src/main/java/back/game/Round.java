package back.game;

import java.util.Random;

public class Round {
    private final String wordAndCategory;

    public Round() {
        String[] data = new String[]{"MEXICO COUNTRY", "HEDWIG BIRD", "KUAKATA BEACH", "CANADA COUNTRY", "DOCTOR PROFESSION", "FOOTBALL GAME", "TEACHER MENTOR", "LEOPARD ANIMAL", "BICYCLE TRANSPORT", "SALMON FISH", "SPARROW BIRD", "PARROTS BIRD", "EAGLE BIRD", "TRAIN TRANSPORT", "SHIP TRANSPORT", "ENGINEER PROFESSION", "BANKER PROFESSION", "CRICKET GAME"};
        int random = new Random().nextInt(data.length);
        this.wordAndCategory = data[random];
    }

    public String getWordAndCategory() {
        return wordAndCategory;
    }

    @Override
    public String toString() {
        return "Round{" +
                "wordAndCategory='" + wordAndCategory + '\'' +
                '}';
    }
}
