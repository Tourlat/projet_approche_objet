package com.projetjava.Controller;

import com.projetjava.Model.building.BuildingType;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BuildingController {

  @FXML
  private ImageView lumberMillImage;

  @FXML
  private ImageView apartmentImage;

  @FXML
  private ImageView steelMillImage;

  @FXML
  private ImageView quarryImage;

  @FXML
  private VBox lumberMillVBox;

  @FXML
  private VBox apartmentVBox;

  @FXML
  private VBox steelMillVBox;

  @FXML
  private VBox quarryVBox;

  private VBox lastClickedVBox;

  @FXML
  MapController mapController;

  @FXML
  public void initialize() {}

  public void initializeEventHandlers() {
    lumberMillImage.setOnMouseClicked(event ->
      handleBuildingClick(lumberMillVBox)
    );
    apartmentImage.setOnMouseClicked(event -> handleBuildingClick(apartmentVBox)
    );
    steelMillImage.setOnMouseClicked(event -> handleBuildingClick(steelMillVBox)
    );
    quarryImage.setOnMouseClicked(event -> handleBuildingClick(quarryVBox));
  }

  private void resetScales() {
    if (lastClickedVBox != null) {
      lastClickedVBox.setScaleX(1.0);
      lastClickedVBox.setScaleY(1.0);
      lastClickedVBox = null;
    }
  }

  public void setImages(
    Image lumberMillImg,
    Image apartmentImg,
    Image steelMillImg,
    Image quarryImg
  ) {
    if (lumberMillImg != null) {
      lumberMillImage.setImage(lumberMillImg);
    } else {
      System.out.println("Lumber Mill image in BuildingController is null");
    }
    if (apartmentImg != null) {
      apartmentImage.setImage(apartmentImg);
    } else {
      System.out.println("Apartment image in BuildingController is null");
    }

    if (steelMillImg != null) {
      steelMillImage.setImage(steelMillImg);
    } else {
      System.out.println("Steel Mill image in BuildingController is null");
    }
    if (quarryImg != null) {
      quarryImage.setImage(quarryImg);
    } else {
      System.out.println("Quarry image in BuildingController is null");
    }
  }

  public void setMapController(MapController mapController) {
    this.mapController = mapController;
  }

  private void handleBuildingClick(VBox BuildingVBox) {
    resetScales();
    BuildingVBox.setScaleX(0.9);
    BuildingVBox.setScaleY(0.9);

    this.mapController.setSelectedBuildingType(convertVBoxToType(BuildingVBox));

    lastClickedVBox = BuildingVBox;
  }

  private BuildingType convertVBoxToType(VBox BuildingVBox) {
    if (BuildingVBox == lumberMillVBox) {
      return BuildingType.LUMBER_MILL;
    } else if (BuildingVBox == apartmentVBox) {
      return BuildingType.APARTMENT_BUILDING;
    } else if (BuildingVBox == steelMillVBox) {
      return BuildingType.STEEL_MILL;
    } else if (BuildingVBox == quarryVBox) {
      return BuildingType.QUARRY;
    } else {
      return null;
    }
  }
}
