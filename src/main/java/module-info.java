module com.example.starship {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.starship to javafx.fxml;
    exports com.example.starship;
}