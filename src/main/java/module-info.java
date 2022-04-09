module com.example.javav3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javav3 to javafx.fxml;
    exports com.example.javav3;
}