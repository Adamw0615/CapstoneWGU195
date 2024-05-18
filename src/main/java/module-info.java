module a.wright.wguc195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires junit;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Model to javafx.base;
    exports Model;
    opens Main to javafx.fxml;
    exports Main;
    opens UnitTest;


}