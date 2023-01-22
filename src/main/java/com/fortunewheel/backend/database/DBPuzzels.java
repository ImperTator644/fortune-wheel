package com.fortunewheel.backend.database;


import java.sql.*;
import java.util.Random;

public class DBPuzzels {
    private static DBPuzzels instance = null;


    public DBPuzzels() {
        try {
            Class.forName(DBConstances.DBDRIVER).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static DBPuzzels getInstance() {
        if (instance == null) {
            instance = new DBPuzzels();
        }
        return instance;
    }

    public static String getWordPuzzles(String category) {
        try (Connection connection = DriverManager.getConnection(DBConstances.DBURL, DBConstances.DBUSER, DBConstances.DBPASS);
             Statement statement = connection.createStatement()
        ) {
            String sql = "USE FORTUNE";
            statement.execute(sql);
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM puzzles p WHERE p.category = '" + category + "'");
            resultSet.next();
            if (resultSet.getInt(1) != 0) {

                int random = new Random().nextInt(resultSet.getInt(1)) + 1;

                resultSet = statement.executeQuery("SELECT * FROM puzzles p WHERE p.category = '" + category + "'");
                for (int i = 1; i <= random; i++) {
                    resultSet.next();
                }
                return resultSet.getString(3);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
