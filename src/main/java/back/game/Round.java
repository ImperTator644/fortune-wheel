package back.game;

import back.database.Categories;
import back.database.DBPuzzels;

import java.util.Random;

public class Round {
    private final String wordAndCategory;

    public Round() {
        String[] data = Categories.getAllCategories();
        int random = new Random().nextInt(data.length);
        this.wordAndCategory = DBPuzzels.getWordPuzzles(data[random]) + " " + data[random];
        System.out.println(wordAndCategory);
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
