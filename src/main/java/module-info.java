module com.example.fortunewheel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.java;

    opens com.fortunewheel.frontend to javafx.fxml;
    exports com.fortunewheel.frontend;
    exports com.fortunewheel.backend.connection;
    opens com.fortunewheel.backend.connection to javafx.fxml;
}