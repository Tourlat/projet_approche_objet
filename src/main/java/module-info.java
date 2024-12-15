module com.projetjava {
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.base;
  requires transitive javafx.graphics;

  // Open packages to javafx.fxml for FXML loading
  opens com.projetjava to javafx.fxml;
  opens com.projetjava.Controller to javafx.fxml;

  // Export packages that are used by other modules
  exports com.projetjava ;
  exports com.projetjava.Controller ;
  exports com.projetjava.Model.building ;
  exports com.projetjava.Model.resources ;
  exports com.projetjava.Model.game ;
  exports com.projetjava.Model.map ;
}
