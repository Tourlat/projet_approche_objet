package com.projetjava;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  private static Scene scene;
  private static Stage primaryStage;

  @Override
  public void start(Stage stage) throws IOException {
    primaryStage = stage;

    URL fxmlLocation = getClass()
      .getResource("/com/projetjava/views/MenuView.fxml");
    if (fxmlLocation == null) {
      System.err.println("FXML file not found at the specified path.");
      return;
    } else {
      System.out.println("FXML file found at: " + fxmlLocation);
    }

    FXMLLoader loader = new FXMLLoader(fxmlLocation);

    scene = new Scene(loader.load(), 1920, 1080);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();

    stage.setOnCloseRequest(event -> {
      System.out.println(
        "Window close request received. Stopping the application..."
      );
      Platform.exit();
      System.exit(0);
    });
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  public static void main(String[] args) {
    launch();
  }
}
