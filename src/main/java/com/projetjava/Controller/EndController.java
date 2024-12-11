package com.projetjava.Controller;

import com.projetjava.App;
import com.projetjava.Model.game.GameManager;
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

        // Définir l'image après le chargement de la vue
        EndController controller = loader.getController();
        controller.endImage.setImage(endImg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void updateEnd(boolean hasWon) {
    Platform.runLater(() -> {
      gameEnded(hasWon);
    });
  }

  @Override
  public void update() {
  }

}
