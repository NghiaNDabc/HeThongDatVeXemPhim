module com.example.client_hethongxemphim {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.sql;
    opens com.example.client_hethongxemphim.socket to com.google.gson;
    opens com.example.client_hethongxemphim.models to com.google.gson;
    opens com.example.client_hethongxemphim.controllers to javafx.fxml;
    opens com.example.client_hethongxemphim to javafx.fxml;
    exports com.example.client_hethongxemphim;
}