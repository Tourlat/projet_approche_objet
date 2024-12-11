package com.projetjava.Controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

  @FXML
  private Button playButton;

  @FXML
  public void initialize() {
    // Apply styles to the play button
    playButton.setStyle(
      "-fx-background-color: #d29dfb; " +
      "-fx-text-fill: white; " +
      "-fx-font-size: 18px; " +
      "-fx-padding: 10px 20px; " +
      "-fx-background-radius: 15px; " +
      "-fx-border-radius: 15px; " +
      "-fx-cursor: hand;"
    );

    // Add hover effect
    playButton.setOnMouseEntered(e ->
      playButton.setStyle(
        "-fx-background-color: #d4a8f7; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 18px; " +
        "-fx-padding: 10px 20px; " +
        "-fx-background-radius: 15px; " +
        "-fx-border-radius: 15px; " +
        "-fx-cursor: hand;"
      )
    );
    playButton.setOnMouseExited(e ->
      playButton.setStyle(
        "-fx-background-color: #d29dfb; " +
        "-fx-text-fill: white; " +
        "-fx-font-size: 18px; " +
        "-fx-padding: 10px 20px; " +
        "-fx-background-radius: 15px; " +
        "-fx-border-radius: 15px; " +
        "-fx-cursor: hand;"
      )
    );
  }

  @FXML
  private void handlePlayButtonAction(javafx.event.ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/com/projetjava/views/MainView.fxml")
      );
      Parent mainView = loader.load();

      Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene()
        .getWindow();
      stage.setScene(new Scene(mainView, 1910, 1010));
      stage.setMaximized(true);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
