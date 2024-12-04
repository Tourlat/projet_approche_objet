package com.projetjava.Controller;

import com.projetjava.Model.building.BuildingType;
import com.projetjava.customexceptions.InvalidResourceLoadException;
import com.projetjava.util.ImageCache;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class BuildingController {

  @FXML
  private ImageView lumberMillImage;

  @FXML
  private ImageView apartmentImage;

  @FXML
  private VBox lumberMillVBox;

  @FXML
  private VBox apartmentVBox;

  private VBox lastClickedVBox;

  @FXML
  MapController mapController;

  @FXML
  public void initialize() {
    loadImages();

  }

  public void initializeEventHandlers() {
    lumberMillImage.setOnMouseClicked(event -> handleLumberMillClick());
    apartmentImage.setOnMouseClicked(event -> handleApartmentClick());
  }

  private void resetScales() {
    System.out.println(lastClickedVBox);
    if (lastClickedVBox != null) {
      lastClickedVBox.setScaleX(1.0);
      lastClickedVBox.setScaleY(1.0);
      lastClickedVBox = null;
    }
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
      throw new InvalidResourceLoadException(
          "Error loading images in BuildingController",
          e);
    }
  }

  private void handleLumberMillClick() {
    // Logique pour gérer le clic sur le Lumber Mill
    System.out.println("Lumber Mill clicked!");
    resetScales();
    lumberMillVBox.setScaleX(0.9);
    lumberMillVBox.setScaleY(0.9);

    mapController.setSelectedBuildingType(BuildingType.LUMBER_MILL);

    lastClickedVBox = lumberMillVBox;
    // Ajoutez ici la logique pour les actions à effectuer lors du clic
  }

  private void handleApartmentClick() {
    // Logique pour gérer le clic sur l'Apartment
    System.out.println("Apartment clicked!");
    resetScales();
    apartmentVBox.setScaleX(0.9);
    apartmentVBox.setScaleY(0.9);

    lastClickedVBox = apartmentVBox;
    // Ajoutez ici la logique pour les actions à effectuer lors du clic
  }

  public void setMapController(MapController mapController) {
    System.out.println("Setting mapController in BuildingController ");
    this.mapController = mapController;
  }
}
