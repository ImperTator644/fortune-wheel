package back.database;


import java.sql.*;

public class DBPuzzels {
    private static DBPuzzels instance = null;


    public DBPuzzels() {
        try{
            Class.forName(DBConstances.DBDRIVER).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static DBPuzzels getInstance(){
        if(instance == null){
            instance = new DBPuzzels();
        }
        return instance;
    }

    private static String getWordPuzzles(){
        try(Connection connection = DriverManager.getConnection(DBConstances.DBURL,DBConstances.DBUSER,DBConstances.DBPASS);
            Statement statement = connection.createStatement()
        ) {
            String sql = "USE FORTUNE";
            statement.execute(sql);
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM puzzles WHERE category ='KRAJ' ");
            resultSet.first();
            if(resultSet.getInt(0) != 0) {
                resultSet = statement.executeQuery("SELECT * FROM puzzles p WHERE category ='KRAJ' ");
                System.out.println(resultSet.getInt(0));
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

}
