package com.projetjava.Controller;

import com.projetjava.App;
import com.projetjava.util.ImageCache;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EndController implements EndObserver {

  @FXML
  private ImageView endImage;

  /**
   * Display the end screen
   * @param hasWon - true if the player has won, false otherwise
   */
  @FXML
  public void gameEnded(Boolean hasWon) {
    Platform.runLater(() -> {
      try {
        ImageCache imageCache = ImageCache.getInstance();
        FXMLLoader loader;
        Image endImg;
        if (hasWon) {
          loader =
            new FXMLLoader(
              getClass().getResource("/com/projetjava/views/WinView.fxml")
            );
          endImg = imageCache.getImage("/com/projetjava/sprites/win.png");
        } else {
          loader =
            new FXMLLoader(
              getClass().getResource("/com/projetjava/views/LoseView.fxml")
            );
          endImg = imageCache.getImage("/com/projetjava/sprites/lose.png");
        }

        Parent endView = loader.load();

        Stage stage = App.getPrimaryStage();
        stage.setScene(new Scene(endView, 1910, 1010));
        stage.setMaximized(true);
        stage.show();

        // Define the image to display after the view has been loaded
        EndController controller = loader.getController();
        controller.endImage.setImage(endImg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Update the end screen
   */
  @Override
  public void updateEnd(boolean hasWon) {
    Platform.runLater(() -> {
      gameEnded(hasWon);
    });
  }

  /**
   * Function update from the Observer interface: does nothing
   */
  @Override
  public void update() {}
}
