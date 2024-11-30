module com.projetjava {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.projetjava to javafx.fxml;
    opens com.projetjava.Controller to javafx.fxml;
   
    exports com.projetjava;
    exports com.projetjava.Controller;
   
}