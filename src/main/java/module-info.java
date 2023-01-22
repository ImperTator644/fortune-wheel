module com.example.fortunewheel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.fortunewheel to javafx.fxml;
    exports com.example.fortunewheel;
}