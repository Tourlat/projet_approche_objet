package com.projetjava.Controller;

import com.projetjava.Model.game.GameManager;
import com.projetjava.customexceptions.ErrorInitController;
import com.projetjava.customexceptions.InvalidResourceLoadException;
import com.projetjava.util.ImageCache;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainController {

  @FXML
  private BorderPane mainPane;

  private GameManager gameManager;
  private ResourcesController resourcesController;
  private MapController mapController;
  private BuildingController buildingController;
  private EndController endController;

  private Image woodenCabinImage;
  private Image lumberMillImage;
  private Image houseImage;
  private Image apartmentImage;
  private Image farmImage;
  private Image quarryImage;
  private Image steelMillImage;
  private Image cementPlantImage;
  private Image goldMineImage;

  @FXML
  public void initialize() {
    loadImages();
    System.out.println("Initializing GameManager...");
    gameManager = GameManager.getInstance();

    try {
      Pane resourcesView = loadView("/com/projetjava/views/ResourcesView.fxml");
      mainPane.setRight(resourcesView);

      gameManager.addResourceObserver(resourcesController);

      gameManager.initializeGame();

      Pane mapView = loadView("/com/projetjava/views/MapView.fxml");
      mainPane.setCenter(mapView);

      GridPane buildingView = (GridPane) loadView(
          "/com/projetjava/views/BuildingView.fxml");
      mainPane.setBottom(buildingView);

      // Set the MapController in the BuildingController.
      // We need to do this after the controllers have been initialized.
      if (buildingController != null && mapController != null) {
        System.out.println("Setting MapController in BuildingController");
        buildingController.setMapController(mapController);
        buildingController.setImages(
            woodenCabinImage,
            lumberMillImage,
            houseImage,
            apartmentImage,
            farmImage,
            quarryImage,
            steelMillImage,
            cementPlantImage,
            goldMineImage);
        mapController.setImages(
            woodenCabinImage,
            lumberMillImage,
            houseImage,
            apartmentImage,
            farmImage,
            quarryImage,
            steelMillImage,
            cementPlantImage,
            goldMineImage);
        gameManager.addObserver(mapController);
      } else {
        throw new ErrorInitController("Controllers not initialized properly");
      }

      //Set end controller
      endController = new EndController();
      gameManager.setEndObserver(endController);


      // Set the event handler for the win/lose conditions, if the user presses W or L.
      setEventHandlerForWinLose();

    } catch (IOException e) {
      throw new InvalidResourceLoadException(
          "Error loading views in MainController",
          e);
    }
  }

  private void setEventHandlerForWinLose() {
    Platform.runLater(() -> {
      Scene scene = mainPane.getScene();
      if (scene != null) {
        scene.setOnKeyPressed(event -> {
          switch (event.getCode()) {
            case W:
              gameManager.Win();
              break;
            case L:
              gameManager.Lose();
              break;
            default:
              break;
          }
        });
      }
    });
  }

  private Pane loadView(String fxmlPath) throws IOException {
    System.out.println("Loading view: " + fxmlPath);
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
    Pane pane = loader.load();

    if (fxmlPath.contains("ResourcesView")) {
      resourcesController = loader.getController();
    }
    if (fxmlPath.contains("MapView")) {
      mapController = loader.getController();
      System.out.println("MapController loaded: " + mapController);
    }
    if (fxmlPath.contains("BuildingView")) {
      buildingController = loader.getController();
      System.out.println("BuildingController loaded: " + buildingController);
    }


    return pane;
  }

  private Map<String, String> createImagePathsMap() {
    Map<String, String> imagePaths = new HashMap<>();
    imagePaths.put("woodenCabinImage", "/com/projetjava/sprites/building_sprites/wooden_cabin.png");
    imagePaths.put("lumberMillImage", "/com/projetjava/sprites/building_sprites/lumber_mill.png");
    imagePaths.put("houseImage", "/com/projetjava/sprites/building_sprites/house.png");
    imagePaths.put("apartmentImage", "/com/projetjava/sprites/building_sprites/apartment.png");
    imagePaths.put("farmImage", "/com/projetjava/sprites/building_sprites/farm.png");
    imagePaths.put("quarryImage", "/com/projetjava/sprites/building_sprites/quarry.png");
    imagePaths.put("steelMillImage", "/com/projetjava/sprites/building_sprites/steel_mill.png");
    imagePaths.put("cementPlantImage", "/com/projetjava/sprites/building_sprites/cement_plant.png");
    imagePaths.put("goldMineImage", "/com/projetjava/sprites/building_sprites/gold_mine.png");
    return imagePaths;
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      Map<String, String> imagePaths = createImagePathsMap();

      for (Map.Entry<String, String> entry : imagePaths.entrySet()) {
        Image image = imageCache.getImage(entry.getValue());
        if (image == null) {
          throw new InvalidResourceLoadException("Error loading " + entry.getKey());
        }
        switch (entry.getKey()) {
          case "woodenCabinImage":
            woodenCabinImage = image;
            break;
          case "lumberMillImage":
            lumberMillImage = image;
            break;
          case "houseImage":
            houseImage = image;
            break;
          case "apartmentImage":
            apartmentImage = image;
            break;
          case "farmImage":
            farmImage = image;
            break;
          case "quarryImage":
            quarryImage = image;
            break;
          case "steelMillImage":
            steelMillImage = image;
            break;
          case "cementPlantImage":
            cementPlantImage = image;
            break;
          case "goldMineImage":
            goldMineImage = image;
            break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
