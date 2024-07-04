module org.example.queingmodel {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.queingmodel to javafx.fxml;
    exports org.example.queingmodel;
}