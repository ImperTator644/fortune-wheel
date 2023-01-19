module com.example.fortunewheel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.example.fortunewheel to javafx.fxml;
    exports com.example.fortunewheel;
}