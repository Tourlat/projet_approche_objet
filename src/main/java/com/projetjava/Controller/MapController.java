package com.projetjava.Controller;

import com.projetjava.Model.building.Building;
import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.game.GameManager;
import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.util.ImageCache;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MapController implements Observer {

  @FXML
  private GridPane mapGrid;

  private Image ground;
  private Image inConstruction;

  private Map<BuildingType, Image> buildingImages = new HashMap<>();

  private BuildingType selectedBuildingType;
  private BuildingOptionsPopup buildingOptionsPopup;

  private GameManager gameManager;

  /**
   * Initialize the MapController.
   */
  @FXML
  public void initialize() {
    loadImages();
    loadMap();
    gameManager = GameManager.getInstance();
    buildingOptionsPopup = new BuildingOptionsPopup(this);
  }

  /**
   * Load the images
   */
  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      ground = imageCache.getImage("/com/projetjava/sprites/ground.png");
      inConstruction =
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/inConstruction.png"
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Set the images for the buildings
   * @param woodenCabinImg - the image for the wooden cabin
   * @param lumberMillImg - the image for the lumber mill
   * @param houseImg  - the image for the house
   * @param apartmentImg  - the image for the apartment
   * @param farmImg - the image for the farm
   * @param quarryImg - the image for the quarry
   * @param steelMillImg  - the image for the steel mill
   * @param cementPlantImg  - the image for the cement plant
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
    buildingImages.put(BuildingType.WOODEN_CABIN, woodenCabinImg);
    buildingImages.put(BuildingType.LUMBER_MILL, lumberMillImg);
    buildingImages.put(BuildingType.HOUSE, houseImg);
    buildingImages.put(BuildingType.APARTMENT_BUILDING, apartmentImg);
    buildingImages.put(BuildingType.FARM, farmImg);
    buildingImages.put(BuildingType.QUARRY, quarryImg);
    buildingImages.put(BuildingType.STEEL_MILL, steelMillImg);
    buildingImages.put(BuildingType.CEMENT_PLANT, cementPlantImg);
    buildingImages.put(BuildingType.GOLD_MINE, goldMineImg);
  }

  /**
   * Load the map
   */
  private void loadMap() {
    MapManager mapManager = MapManager.getInstance();

    for (int x = 0; x < mapManager.getWidth(); x++) {
      for (int y = 0; y < mapManager.getHeight(); y++) {
        StackPane cell = new StackPane();
        cell.setPrefSize(25, 25);

        final int finalX = x;
        final int finalY = y;

        ImageView groundImageView = new ImageView(ground);
        groundImageView.setFitHeight(25);
        groundImageView.setFitWidth(25);

        ImageView buildingImageView = new ImageView();

        Building building = mapManager.getBuilding(new Position(x, y));

        if (building != null) {
          int buildingWidth = building.getWidth();
          int buildingHeight = building.getHeight();

          // Add the ground image under the building image
          for (int i = 0; i < buildingWidth; i++) {
            for (int j = 0; j < buildingHeight; j++) {
              ImageView groundImageViewForBuilding = new ImageView(ground);
              groundImageViewForBuilding.setFitHeight(25);
              groundImageViewForBuilding.setFitWidth(25);
              mapGrid.add(groundImageViewForBuilding, x + i, y + j);
            }
          }

          if (!building.isConstructed()) {
            buildingImageView.setImage(inConstruction);
          } else {
            buildingImageView.setImage(buildingImages.get(building.getType()));
          }

          // Set the size of the building image to fit the building size
          buildingImageView.setFitWidth(buildingWidth * 25);
          buildingImageView.setFitHeight(buildingHeight * 25);

          GridPane.setColumnSpan(cell, buildingWidth);
          GridPane.setRowSpan(cell, buildingHeight);
        } else {
          // No building at this position
          buildingImageView.setImage(null);
        }

        // Add building on top of ground
        if (!mapManager.isOccupied(x, y)) cell
          .getChildren()
          .addAll(groundImageView, buildingImageView); else cell
          .getChildren()
          .add(buildingImageView);

        // Add mouse click event handler
        cell.setOnMouseClicked(event -> handleMouseClick(event, finalX, finalY)
        );

        mapGrid.add(cell, x, y);
      }
    }
  }

  /**
   * Set the selected building type
   * @param selectedBuildingType - the selected building type
   */
  public void setSelectedBuildingType(BuildingType selectedBuildingType) {
    this.selectedBuildingType = selectedBuildingType;
  }

  /**
   * Get the map grid
   * @return the map grid
   */
  public GridPane getMapGrid() {
    return mapGrid;
  }

  /**
   * Get the game manager
   * @return the game manager
   */
  public GameManager getGameManager() {
    return gameManager;
  }

  /**
   * Handle mouse click event:
   * - If a building is already placed at the position, show building options popup
   * - If a building type is selected, place the building on the map
   *
   * @param event - the mouse event
   * @param x - the x position
   * @param y - the y position
   */
  private void handleMouseClick(MouseEvent event, int x, int y) {
    MapManager mapManager = MapManager.getInstance();

    Position origin = mapManager.getBuildingPosition(new Position(x, y));
    Building building = mapManager.getBuilding(origin);
    // Check if a building is already placed at this position
    // If so, show building options popup
    if (building != null && building.isConstructed()) {
      showBuildingOptions(
        building,
        x,
        y,
        event.getScreenX(),
        event.getScreenY()
      );
    } else if (selectedBuildingType != null) {
      // Place the selected building on the map
      boolean success = gameManager.addBuilding(
        new Position(x, y),
        selectedBuildingType
      );
      if (success) {
        System.out.println(
          "Building placed: " + selectedBuildingType + " at " + x + ", " + y
        );
        update();
      } else {
        System.out.println("Failed to place building: " + selectedBuildingType);
      }
    }
  }

  /**
   * Show the building options popup
   * @param building - the building
   * @param x - the x position
   * @param y - the y position
   * @param screenX - the screen x position
   * @param screenY - the screen y position
   */
  private void showBuildingOptions(
    Building building,
    int x,
    int y,
    double screenX,
    double screenY
  ) {
    buildingOptionsPopup.show(building, x, y, screenX, screenY);
  }

  /**
   * Update the map
   */
  @Override
  public void update() {
    Platform.runLater(() -> {
      mapGrid.getChildren().clear();
      loadMap();
    });
  }
}
