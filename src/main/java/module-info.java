module org.example.mydocs_4_0 {
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;
    requires java.desktop;

    opens org.example.mydocs_4_0 to javafx.fxml;
    exports org.example.mydocs_4_0;
}