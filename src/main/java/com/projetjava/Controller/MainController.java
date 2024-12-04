package com.projetjava.Controller;

import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.game.GameManager;
import com.projetjava.Model.map.Position;
import com.projetjava.customexceptions.InvalidResourceLoadException;
import com.projetjava.util.ImageCache;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController {

  private GameManager gameManager;

  @FXML
  private BorderPane mainPane;

  @FXML
  private ResourcesController resourcesController;

  @FXML
  private MapController mapController;

  @FXML
  private BuildingController buildingController;

  private Image lumberMillImage;
  private Image apartmentImage;
  private Image steelMillImage;
  private Image quarryImage;

  @FXML
  public void initialize() {
    // Initialize the GameManager
    System.out.println("Initializing GameManager...");
    gameManager = GameManager.getInstance();
    gameManager.initializeGame();

    loadImages();

    // Load the ResourcesView

    Pane resourcesView = null;
    try {
      resourcesView = loadView("/com/projetjava/views/ResourcesView.fxml");
    } catch (IOException e) {
      throw new InvalidResourceLoadException(
        "Error loading resourceView in MainController",
        e
      );
    }

    // Set the ResourcesView to the right of the mainPane
    mainPane.setRight(resourcesView);

    gameManager.addResourceObserver(resourcesController);

    // Load the MapView
    Pane mapView = null;
    try {
      mapView = loadView("/com/projetjava/views/MapView.fxml");
    } catch (IOException e) {
      throw new InvalidResourceLoadException(
        "Error loading mapView in MainController",
        e
      );
    }
    mapController.setImages(
      lumberMillImage,
      apartmentImage,
      steelMillImage,
      quarryImage
    );

    Pane buildingView = null;
    try {
      buildingView = loadView("/com/projetjava/views/BuildingView.fxml");
    } catch (IOException e) {
      throw new InvalidResourceLoadException(
        "Error loading buildingView in MainController",
        e
      );
    }

    buildingController.setMapController(mapController);
    buildingController.initializeEventHandlers();
    buildingController.setImages(
      lumberMillImage,
      apartmentImage,
      steelMillImage,
      quarryImage
    );

    mainPane.setBottom(buildingView);
    // Set the MapView to the center of the mainPane
    mainPane.setCenter(mapView);

    // test building creation

    gameManager.addBuilding(new Position(1, 1), BuildingType.WOODEN_CABIN);

    gameManager.addWorkersToBuilding(new Position(1, 1), 2);
  }

  private Pane loadView(String fxmlPath) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
    Pane pane = loader.load();

    if (fxmlPath.contains("ResourcesView")) {
      resourcesController = loader.getController();
    }
    if (fxmlPath.contains("MapView")) {
      mapController = loader.getController();
    }
    if (fxmlPath.contains("BuildingView")) {
      buildingController = loader.getController();
    }

    return pane;
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      apartmentImage =
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/apartment.png"
        );
      if (apartmentImage == null) {
        throw new InvalidResourceLoadException("Error loading apartment image");
      }
      lumberMillImage =
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/lumber_mill.png"
        );
      if (lumberMillImage == null) {
        throw new InvalidResourceLoadException(
          "Error loading lumber mill image"
        );
      }

      steelMillImage =
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/steel_mill.png"
        );
      if (steelMillImage == null) {
        throw new InvalidResourceLoadException(
          "Error loading steel mill image"
        );
      }

      quarryImage =
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/quarry.png"
        );
      if (quarryImage == null) {
        throw new InvalidResourceLoadException("Error loading quarry image");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
