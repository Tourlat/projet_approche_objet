package com.projetjava.Controller;

import com.projetjava.View.ImageCache;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BuildingController {

  @FXML
  private ImageView lumberMillImage;

  @FXML
  private ImageView apartmentImage;

  @FXML
  public void initialize() {
    loadImages();
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      Image lumberMillImg = imageCache.getImage(
        "/com/projetjava/sprites/building_sprites/lumber_mill.png"
      );
      Image apartmentImg = imageCache.getImage(
        "/com/projetjava/sprites/building_sprites/apartment.png"
      );

      if (lumberMillImg.isError()) {
        System.err.println("Error loading lumber mill image.");
      } else {
        lumberMillImage.setImage(lumberMillImg);
        System.out.println("Lumber mill image loaded successfully.");
      }

      if (apartmentImg.isError()) {
        System.err.println("Error loading apartment image.");
      } else {
        apartmentImage.setImage(apartmentImg);
        System.out.println("Apartment image loaded successfully.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
