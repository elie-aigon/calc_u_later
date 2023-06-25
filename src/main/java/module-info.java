module com.example.calc_u_later {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.calc_u_later to javafx.fxml;
    exports com.example.calc_u_later;
    exports com.example.calc_u_later.controllers;
    exports com.example.calc_u_later.controllers.toolscalculator;
    exports com.example.calc_u_later.controllers.toolsconverter;
    opens com.example.calc_u_later.controllers to javafx.fxml;
}