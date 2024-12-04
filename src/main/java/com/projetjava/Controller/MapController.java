package com.projetjava.Controller;

import com.projetjava.Model.building.Building;
import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.game.GameManager;
import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.util.ImageCache;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MapController {

  @FXML
  private GridPane mapGrid;

  private Image ground;
  private Image woodenCabin;
  private Image lumberMillImage;
  private Image apartmentImage;
  private Image steelMillImage;
  private Image quarryImage;

  private Image lumber_mill;
  private BuildingType selectedBuildingType;
  private GameManager gameManager;

  @FXML
  public void initialize() {
    loadImages();
    loadMap();
    gameManager = GameManager.getInstance();
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      ground = imageCache.getImage("/com/projetjava/sprites/ground.png");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setImages(
    Image lumberMillImg,
    Image apartmentImg,
    Image steelMillImg,
    Image quarryImg
  ) {
    if (lumberMillImg != null) {
      lumberMillImage = lumberMillImg;
    } else {
      System.out.println("Lumber Mill image in MapController is null");
    }
    if (apartmentImg != null) {
      apartmentImage = apartmentImg;
    } else {
      System.out.println("Apartment image in MapController is null");
    }
    if (steelMillImg != null) {
      steelMillImage = steelMillImg;
    } else {
      System.out.println("Steel Mill image in MapController is null");
    }
    if (quarryImg != null) {
      quarryImage = quarryImg;
    } else {
      System.out.println("Quarry image in MapController is null");
    }
  }

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

          // Afficher le sol des cases que le bâtiment occupe
          for (int i = 0; i < buildingWidth; i++) {
            for (int j = 0; j < buildingHeight; j++) {
              ImageView groundImageViewForBuilding = new ImageView(ground);
              groundImageViewForBuilding.setFitHeight(25);
              groundImageViewForBuilding.setFitWidth(25);
              mapGrid.add(groundImageViewForBuilding, x + i, y + j);
            }
          }

          switch (building.getType()) {
            case WOODEN_CABIN:
              System.out.println(
                "Wooden Cabin found at position: (" + x + ", " + y + ")"
              );
              buildingImageView.setImage(woodenCabin);
              break;
            case APARTMENT_BUILDING:
              System.out.println(
                "Apartment found at position: (" + x + ", " + y + ")"
              );
              buildingImageView.setImage(apartmentImage);
              break;
            case LUMBER_MILL:
              System.out.println(
                "Lumber Mill found at position: (" + x + ", " + y + ")"
              );
              buildingImageView.setImage(lumberMillImage);
              break;
            case STEEL_MILL:
              System.out.println(
                "Steel Mill found at position: (" + x + ", " + y + ")"
              );
              buildingImageView.setImage(steelMillImage);
              break;
            case QUARRY:
              System.out.println(
                "Quarry found at position: (" + x + ", " + y + ")"
              );
              buildingImageView.setImage(quarryImage);
              break;
            default:
              buildingImageView.setImage(woodenCabin);
              break;
          }

          buildingImageView.setFitWidth(buildingWidth * 25);
          buildingImageView.setFitHeight(buildingHeight * 25);

          GridPane.setColumnSpan(cell, buildingWidth);
          GridPane.setRowSpan(cell, buildingHeight);

          // Marquez les cellules occupées par le bâtiment
          for (int i = 0; i < buildingWidth; i++) {
            for (int j = 0; j < buildingHeight; j++) {
              mapManager.setOccupied(x + i, y + j, true);
            }
          }
        } else {
          buildingImageView.setImage(null);
        }

        // Ajouter les ImageView dans l'ordre : sol d'abord, puis bâtiment
        if (!mapManager.isOccupied(x, y)) cell
          .getChildren()
          .addAll(groundImageView, buildingImageView); else cell
          .getChildren()
          .add(buildingImageView);

        // Add mouse click event handler
        cell.setOnMouseClicked(event -> handleMouseClick(finalX, finalY));

        mapGrid.add(cell, x, y);
      }
    }
  }

  private void handleMouseClick(int x, int y) {
    System.out.println("Mouse clicked at position: (" + x + ", " + y + ")");
    if (selectedBuildingType != null) {
      // Place the selected building on the map

      int buildingWidth = BuildingType.getWidth(selectedBuildingType);
      int buildingHeight = BuildingType.getHeight(selectedBuildingType);

      // Vérifiez si le bâtiment peut être placé à la position (x, y)
      boolean canPlace = true;
      for (int i = 0; i < buildingWidth; i++) {
        for (int j = 0; j < buildingHeight; j++) {
          if (!gameManager.canPlaceBuilding((x + i), (y + j))) {
            canPlace = false;
            break;
          }
        }
        if (!canPlace) break;
      }

      if (canPlace) {
        // Placez le bâtiment sur la carte
        boolean success = gameManager.addBuilding(
          new Position(x, y),
          selectedBuildingType
        );
        if (success) {
          System.out.println("Building placed: " + selectedBuildingType);
          update();
        } else {
          System.out.println(
            "Failed to place building: " + selectedBuildingType
          );
        }
      } else {
        System.out.println(
          "Cannot place building at position: (" + x + ", " + y + ")"
        );
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
