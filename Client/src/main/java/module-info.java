module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires Server;
    requires java.sql;

    opens com.gui to javafx.fxml;
    exports com.gui;
    exports com.gui.controller;
    opens com.gui.controller to javafx.fxml;
}