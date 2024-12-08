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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MapController implements Observer {

  @FXML
  private GridPane mapGrid;

  private Image ground;
  private Image inConstruction;

  private Map<BuildingType, Image> buildingImages = new HashMap<>();

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
      inConstruction =
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/inConstruction.png"
        );

      buildingImages.put(
        BuildingType.LUMBER_MILL,
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/lumber_mill.png"
        )
      );
      buildingImages.put(
        BuildingType.APARTMENT_BUILDING,
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/apartment.png"
        )
      );
      buildingImages.put(
        BuildingType.FARM,
        imageCache.getImage("/com/projetjava/sprites/building_sprites/farm.png")
      );
      buildingImages.put(
        BuildingType.QUARRY,
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/quarry.png"
        )
      );
      buildingImages.put(
        BuildingType.STEEL_MILL,
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/steel_mill.png"
        )
      );
      buildingImages.put(
        BuildingType.CEMENT_PLANT,
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/cement_plant.png"
        )
      );
      buildingImages.put(
        BuildingType.GOLD_MINE,
        imageCache.getImage(
          "/com/projetjava/sprites/building_sprites/gold_mine.png"
        )
      );
    } catch (Exception e) {
      e.printStackTrace();
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

          if (!building.isConstructed()) {
            buildingImageView.setImage(inConstruction);
          } else {
            buildingImageView.setImage(buildingImages.get(building.getType()));
          }

          buildingImageView.setFitWidth(buildingWidth * 25);
          buildingImageView.setFitHeight(buildingHeight * 25);

          GridPane.setColumnSpan(cell, buildingWidth);
          GridPane.setRowSpan(cell, buildingHeight);
        } else {
          buildingImageView.setImage(null);
        }

        // Add building on top of ground
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

  @Override
  public void update() {
    Platform.runLater(this::loadMap);
  }

  public void setSelectedBuildingType(BuildingType selectedBuildingType) {
    this.selectedBuildingType = selectedBuildingType;
  }

  public void setImages(
    Image lumberMillImg,
    Image apartmentImg,
    Image farmImg,
    Image quarryImg,
    Image steelMillImg,
    Image cementPlantImg,
    Image goldMineImg
  ) {
    buildingImages.put(BuildingType.LUMBER_MILL, lumberMillImg);
    buildingImages.put(BuildingType.APARTMENT_BUILDING, apartmentImg);
    buildingImages.put(BuildingType.FARM, farmImg);
    buildingImages.put(BuildingType.QUARRY, quarryImg);
    buildingImages.put(BuildingType.STEEL_MILL, steelMillImg);
    buildingImages.put(BuildingType.CEMENT_PLANT, cementPlantImg);
    buildingImages.put(BuildingType.GOLD_MINE, goldMineImg);
  }
}
