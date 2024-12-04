package com.projetjava.Controller;

import com.projetjava.Model.building.Building;
import com.projetjava.Model.building.Building;
import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.game.GameManager;
import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.util.ImageCache;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MapController {

  @FXML
  private GridPane mapGrid;

  private Image ground;
  private Image woodenCabin;
  private BuildingType selectedBuildingType;

  @FXML
  public void initialize() {
    loadImages();
    loadMap();
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      ground = imageCache.getImage("/com/projetjava/sprites/ground.png");
      woodenCabin =
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/apartment.png"
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadMap() {
    MapManager mapManager = MapManager.getInstance(50, 30);

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
          switch (building.getType()) {
            case WOODEN_CABIN:
              System.out.println(
                "Wooden Cabin found at position: (" + x + ", " + y + ")"
              );
              buildingImageView.setImage(woodenCabin);
              break;
            default:
              buildingImageView.setImage(woodenCabin);
              break;
          }
        } else {
          buildingImageView.setImage(null);
        }

        buildingImageView.setFitHeight(25);
        buildingImageView.setFitWidth(25);

        cell.getChildren().addAll(groundImageView, buildingImageView);

        // Add mouse click event handler
        cell.setOnMouseClicked(event -> handleMouseClick(finalX, finalY));

        mapGrid.add(cell, x, y);
      }
    }
  }

  private void handleMouseClick(int x, int y) {
    System.out.println("Mouse clicked at position: (" + x + ", " + y + ")");
    update();
    if (selectedBuildingType != null) {
      // Place the selected building on the map
      GameManager gameManager = GameManager.getInstance();
      boolean success = gameManager.addBuilding(
        new Position(x, y),
        selectedBuildingType
      );
      if (success) {
        System.out.println("Building placed: " + selectedBuildingType);
        update();
      } else {
        System.out.println("Failed to place building: " + selectedBuildingType);
      }
    }
  }

  public void update() {
    // Clear the existing cells
    mapGrid.getChildren().clear();
    // Reload the map data
    loadMap();
  }

  public void setSelectedBuildingType(BuildingType selectedBuildingType) {
    System.out.println("Selected building type: " + selectedBuildingType);
    this.selectedBuildingType = selectedBuildingType;
  }
}
