package back.game;

import back.database.Categories;
import back.database.DBPuzzels;

import java.util.Random;

public class Round {
    private final String wordAndCategory;
    private final DBPuzzels puzzels;

    public Round() {
        puzzels = DBPuzzels.getInstance();
        String[] data = Categories.getAllCategories();
        int random = new Random().nextInt(data.length);
        this.wordAndCategory = puzzels.getWordPuzzles(data[random]) + " " + data[random];
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
