module com.example.downloadfiles {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.downloadfiles to javafx.fxml;
    exports com.example.downloadfiles;
}