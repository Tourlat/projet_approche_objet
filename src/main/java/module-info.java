module com.projetjava {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.projetjava to javafx.fxml;
  opens com.projetjava.Controller to javafx.fxml;

  exports com.projetjava ;
  exports com.projetjava.Controller ;
  exports com.projetjava.Model.building ;
  exports com.projetjava.Model.resources ;

  requires transitive javafx.base;

  requires transitive javafx.graphics;
}
