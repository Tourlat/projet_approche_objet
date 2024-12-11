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

  @FXML
  private HBox buildingContainer;

  @FXML
  public void initialize() {
    // Initialisez les listes en utilisant lookup pour récupérer les éléments FXML par leur fx:id
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

    initializeEventHandlers();
  }

  /**
   * Initialise les gestionnaires d'événements (onclick) pour les images des bâtiments.
   * Lorsqu'une image est cliquée, la méthode handleBuildingClick est appelée.
   */
  public void initializeEventHandlers() {
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
   * Réinitialise les échelles des VBox des bâtiments (lorsque l'utilisateur clique sur un autre bâtiment).
   */
  private void resetScales() {
    if (lastClickedVBox != null) {
      lastClickedVBox.setScaleX(1.0);
      lastClickedVBox.setScaleY(1.0);
      lastClickedVBox = null;
    }
  }

  /**
   * Définit les images des bâtiments dans les ImageView correspondantes.
   * @param woodenCabinImg
   * @param lumberMillImg
   * @param houseImg
   * @param apartmentImg
   * @param farmImg
   * @param quarryImg
   * @param steelMillImg
   * @param cementPlantImg
   * @param goldMineImg
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
   * Définit le MapController pour ce BuildingController.
   * @param mapController
   */
  public void setMapController(MapController mapController) {
    this.mapController = mapController;
  }

  /**
   * Gère le clic sur un bâtiment.
   * @param buildingVBox
   */
  private void handleBuildingClick(VBox buildingVBox) {
    resetScales();
    buildingVBox.setScaleX(0.9);
    buildingVBox.setScaleY(0.9);

    if (this.mapController != null) {
      this.mapController.setSelectedBuildingType(
          convertVBoxToType(buildingVBox)
        );
    } else {
      System.err.println("MapController is null in handleBuildingClick");
    }

    lastClickedVBox = buildingVBox;
  }

  /**
   * Convertit une VBox en BuildingType.
   * @param buildingVBox
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
