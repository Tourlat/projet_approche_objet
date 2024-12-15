package com.projetjava.Controller;

import com.projetjava.Model.building.BuildingType;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuildingController {

  private List<VBox> buildingVBoxes = new ArrayList<>();
  private List<ImageView> buildingImages = new ArrayList<>();

  private MapController mapController;
  private VBox lastClickedVBox;
  private BuildingResourcesNeededPopup buildingResourcesNeededPopup;

  @FXML
  private HBox buildingContainer;

  /**
   * Initialize the BuildingController.
   */
  @FXML
  public void initialize() {
    // Initialize the building VBoxes and images from the FXML file by their IDs
    String[] vboxIds = {
      "woodenCabinVBox",
      "lumberMillVBox",
      "houseVBox",
      "apartmentVBox",
      "farmVBox",
      "quarryVBox",
      "steelMillVBox",
      "cementPlantVBox",
      "goldMineVBox",
    };
    String[] imageIds = {
      "woodenCabinImage",
      "lumberMillImage",
      "houseImage",
      "apartmentImage",
      "farmImage",
      "quarryImage",
      "steelMillImage",
      "cementPlantImage",
      "goldMineImage",
    };

    for (String id : vboxIds) {
      VBox vbox = (VBox) buildingContainer.lookup("#" + id);
      if (vbox != null) {
        buildingVBoxes.add(vbox);
      } else {
        System.err.println("VBox with id " + id + " not found");
      }
    }

    for (String id : imageIds) {
      ImageView imageView = (ImageView) buildingContainer.lookup("#" + id);
      if (imageView != null) {
        buildingImages.add(imageView);
      } else {
        System.err.println("ImageView with id " + id + " not found");
      }
    }

    buildingResourcesNeededPopup = new BuildingResourcesNeededPopup(null);
    initializeEventHandlers();
  }

  /**
   * Initialize the event handlers for the building images.
   * When a building image is clicked, the corresponding VBox is scaled down and the MapController is notified.
   */
  private void initializeEventHandlers() {
    for (int i = 0; i < buildingImages.size(); i++) {
      final int index = i;
      buildingImages
        .get(i)
        .setOnMouseClicked(event ->
          handleBuildingClick(buildingVBoxes.get(index))
        );
    }
  }

  /**
   * Reset the scales of the building VBoxes.
   * This is used to reset the scale of the last clicked building VBox when another building is clicked.
   */
  private void resetScales() {
    if (lastClickedVBox != null) {
      lastClickedVBox.setScaleX(1.0);
      lastClickedVBox.setScaleY(1.0);
      lastClickedVBox = null;
    }
  }

  /**
   * Define the images for the buildings.
   * @param woodenCabinImg - the image for the wooden cabin
   * @param lumberMillImg - the image for the lumber mill
   * @param houseImg - the image for the house
   * @param apartmentImg - the image for the apartment
   * @param farmImg - the image for the farm
   * @param quarryImg - the image for the quarry
   * @param steelMillImg - the image for the steel mill
   * @param cementPlantImg - the image for the cement plant
   * @param goldMineImg - the image for the gold mine
   */
  public void setImages(
    Image woodenCabinImg,
    Image lumberMillImg,
    Image houseImg,
    Image apartmentImg,
    Image farmImg,
    Image quarryImg,
    Image steelMillImg,
    Image cementPlantImg,
    Image goldMineImg
  ) {
    Image[] images = {
      woodenCabinImg,
      lumberMillImg,
      houseImg,
      apartmentImg,
      farmImg,
      quarryImg,
      steelMillImg,
      cementPlantImg,
      goldMineImg,
    };

    for (int i = 0; i < buildingImages.size(); i++) {
      if (images[i] != null) {
        buildingImages.get(i).setImage(images[i]);
      } else {
        System.out.println(
          buildingVBoxes.get(i).getId() + " image in BuildingController is null"
        );
      }
    }
  }

  /**
   * Define the MapController.
   * @param mapController - the MapController
   */
  public void setMapController(MapController mapController) {
    this.mapController = mapController;
    buildingResourcesNeededPopup =
      new BuildingResourcesNeededPopup(mapController);
  }

  /**
   * Handle the click on a building VBox.
   * @param buildingVBox - the building VBox that was clicked
   */
  private void handleBuildingClick(VBox buildingVBox) {
    resetScales();
    buildingVBox.setScaleX(0.9);
    buildingVBox.setScaleY(0.9);

    // Get the screen coordinates of the VBox
    double screenX = buildingVBox
      .localToScreen(buildingVBox.getBoundsInLocal())
      .getMinX();
    double screenY = buildingVBox
      .localToScreen(buildingVBox.getBoundsInLocal())
      .getMinY();

    if (this.mapController != null) {
      this.mapController.setSelectedBuildingType(
          convertVBoxToType(buildingVBox)
        );
      showBuildingResourcesNeeded(
        convertVBoxToType(buildingVBox),
        screenX,
        screenY
      );
    } else {
      System.err.println("MapController is null in handleBuildingClick");
    }

    lastClickedVBox = buildingVBox;
  }

  /**
   * Show the resources needed to build a building.
   * @param building - the building type
   * @param screenX - the screen X position
   * @param screenY - the screen Y position
   */
  private void showBuildingResourcesNeeded(
    BuildingType building,
    double screenX,
    double screenY
  ) {
    buildingResourcesNeededPopup.show(building, screenX, screenY);
  }

  /**
   * Convert a VBox to a BuildingType.
   * @param buildingVBox - the VBox to convert
   * @return BuildingType
   */
  private BuildingType convertVBoxToType(VBox buildingVBox) {
    if (buildingVBox.getId().equals("woodenCabinVBox")) {
      return BuildingType.WOODEN_CABIN;
    } else if (buildingVBox.getId().equals("lumberMillVBox")) {
      return BuildingType.LUMBER_MILL;
    } else if (buildingVBox.getId().equals("houseVBox")) {
      return BuildingType.HOUSE;
    } else if (buildingVBox.getId().equals("apartmentVBox")) {
      return BuildingType.APARTMENT_BUILDING;
    } else if (buildingVBox.getId().equals("farmVBox")) {
      return BuildingType.FARM;
    } else if (buildingVBox.getId().equals("quarryVBox")) {
      return BuildingType.QUARRY;
    } else if (buildingVBox.getId().equals("steelMillVBox")) {
      return BuildingType.STEEL_MILL;
    } else if (buildingVBox.getId().equals("cementPlantVBox")) {
      return BuildingType.CEMENT_PLANT;
    } else if (buildingVBox.getId().equals("goldMineVBox")) {
      return BuildingType.GOLD_MINE;
    } else {
      return null;
    }
  }
}
