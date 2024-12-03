package com.projetjava.Controller;

import com.projetjava.Model.building.BuildingType;
import com.projetjava.customexceptions.InvalidResourceLoadException;
import com.projetjava.util.ImageCache;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BuildingController {

  @FXML
  private ImageView lumberMillImage;

  @FXML
  private ImageView apartmentImage;

  private BuildingType selectedBuildingType;

  @FXML
  public void initialize() {
    loadImages();
    lumberMillImage.setOnMouseClicked(event -> handleBuildingClick(event, BuildingType.LUMBER_MILL));
    apartmentImage.setOnMouseClicked(event -> handleBuildingClick(event, BuildingType.APARTMENT_BUILDING));
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      Image lumberMillImg = imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/lumber_mill.png");
      Image apartmentImg = imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/apartment.png");

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
      throw new InvalidResourceLoadException("Error loading images in BuildingController", e);
    }
  }

  private void handleBuildingClick(MouseEvent event, BuildingType buildingType) {
    selectedBuildingType = buildingType;
    System.out.println("Selected building: " + buildingType);
  }

  public BuildingType getSelectedBuildingType() {
    return selectedBuildingType;
  }

 
  
}
