package com.projetjava.Controller;

import com.projetjava.Model.building.Building;
import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.game.GameManager;
import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.Model.resources.ResourceType;
import com.projetjava.util.ImageCache;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

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
   * Handle mouse click event
   *
   * @param event the mouse event
   * @param x     the x position
   * @param y     the y position
   */
  private void handleMouseClick(MouseEvent event, int x, int y) {
    MapManager mapManager = MapManager.getInstance();

    System.out.println("Mouse clicked at position: (" + x + ", " + y + ")");
    Position origin = mapManager.getBuildingPosition(new Position(x, y));
    Building building = mapManager.getBuilding(origin);
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
        System.out.println("Building placed: " + selectedBuildingType);
        update();
      } else {
        System.out.println("Failed to place building: " + selectedBuildingType);
      }
      selectedBuildingType = null;
    }
  }

  /**
   * Display building options popup, such as adding/removing workers, removing
   * building
   *
   * @param building the building
   * @param x        the x position
   * @param y        the y position
   * @param screenX  the screen x position
   * @param screenY  the screen y position
   */
  private void showBuildingOptions(
    Building building,
    int x,
    int y,
    double screenX,
    double screenY
  ) {
    Popup popup = new Popup();

    VBox vbox = createVBoxPopUp();

    String buildingName = building.getType().toString().replace("_", " ");
    Label buildingNameLabel = new Label(buildingName);
    buildingNameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    Label workerLabel = createWorkerLabel(building);

    String producString = createProductionString(building);

    Position origin = MapManager
      .getInstance()
      .getBuildingPosition(new Position(x, y));

    if (building.getMaxEmployees() != 0) {
      Label productionLabel = new Label(producString);
      productionLabel.setStyle("-fx-font-size: 14px;");

      Button removeWorkerButton = createRemoveWorkerButton(
        workerLabel,
        productionLabel,
        building,
        origin
      );

      Button addWorkerButton = createAddWorkerButton(
        workerLabel,
        productionLabel,
        building,
        origin
      );

      HBox workerButtons = new HBox(10);
      workerButtons.getChildren().addAll(removeWorkerButton, addWorkerButton);
      workerButtons.setAlignment(Pos.CENTER);

      Button removeBuildingButton = createRemoveBuildingButton(origin, popup);
      Button cancelButton = createCancelButton(popup);

      vbox
        .getChildren()
        .addAll(
          buildingNameLabel,
          workerLabel,
          productionLabel,
          workerButtons,
          removeBuildingButton,
          cancelButton
        );
    } else {
      Button removeBuildingButton = createRemoveBuildingButton(origin, popup);
      Button cancelButton = createCancelButton(popup);

      vbox
        .getChildren()
        .addAll(buildingNameLabel, removeBuildingButton, cancelButton);
    }

    popup.getContent().add(vbox);
    popup.setAutoHide(true);
    popup.show(mapGrid.getScene().getWindow(), screenX, screenY);
  }

  private VBox createVBoxPopUp() {
    VBox vbox = new VBox(10);
    vbox.setStyle(
      "-fx-background-color: white; " +
      "-fx-padding: 20; " +
      "-fx-border-color: black; " +
      "-fx-border-width: 1; " +
      "-fx-background-radius: 10; " + // Rounded corners
      "-fx-border-radius: 10;" // Rounded corners
    );
    vbox.setAlignment(Pos.CENTER);
    return vbox;
  }

  private Label createWorkerLabel(Building building) {
    Label workerLabel = new Label(
      "Workers: " +
      building.getCurrentEmployees() +
      "/" +
      building.getMaxEmployees()
    );
    workerLabel.setStyle("-fx-font-size: 14px;");
    return workerLabel;
  }

  private String createProductionString(Building building) {
    StringBuilder productionText = new StringBuilder("Production:\n");
    for (Map.Entry<ResourceType, Integer> entry : building
      .getCurrentProduction()
      .entrySet()) {
      productionText
        .append(entry.getKey())
        .append(": ")
        .append(entry.getValue())
        .append("\n");
    }
    return productionText.toString();
  }

  private Button createRemoveWorkerButton(
    Label workerLabel,
    Label productionLabel,
    Building building,
    Position origin
  ) {
    Button removeWorkerButton = new Button("-1");
    removeWorkerButton.setStyle(
      "-fx-background-color: #f44336; " + // Red background
      "-fx-text-fill: white; " + // White text
      "-fx-background-radius: 10; " + // Rounded corners
      "-fx-border-radius: 10;" // Rounded corners
    );
    removeWorkerButton.setOnAction(e -> {
      if (building.getCurrentEmployees() > 0) {
        gameManager.removeWorkersFromBuilding(origin, 1);
        workerLabel.setText(
          "Workers: " +
          building.getCurrentEmployees() +
          "/" +
          building.getMaxEmployees()
        );
        productionLabel.setText(createProductionString(building));
      }
    });

    return removeWorkerButton;
  }

  private Button createAddWorkerButton(
    Label workerLabel,
    Label productionLabel,
    Building building,
    Position origin
  ) {
    Button addWorkerButton = new Button("+1");
    addWorkerButton.setStyle(
      "-fx-background-color: #4CAF50; " + // Green background
      "-fx-text-fill: white; " + // White text
      "-fx-background-radius: 10; " + // Rounded corners
      "-fx-border-radius: 10;" // Rounded corners
    );
    addWorkerButton.setOnAction(e -> {
      if (
        building.getCurrentEmployees() < building.getMaxEmployees() &&
        gameManager.getAvailableWorkers() > 0
      ) {
        gameManager.addWorkersToBuilding(origin, 1);
        workerLabel.setText(
          "Workers: " +
          building.getCurrentEmployees() +
          "/" +
          building.getMaxEmployees()
        );
      }
      productionLabel.setText(createProductionString(building));
    });
    return addWorkerButton;
  }

  private Button createRemoveBuildingButton(Position origin, Popup popup) {
    Button removeBuildingButton = new Button("Remove Building");
    removeBuildingButton.setStyle(
      "-fx-background-color: #f44336; " + // Red background
      "-fx-text-fill: white; " + // White text
      "-fx-background-radius: 10; " + // Rounded corners
      "-fx-border-radius: 10;" // Rounded corners
    );
    removeBuildingButton.setOnAction(e -> {
      gameManager.removeBuilding(origin);
      update();
      popup.hide();
    });
    return removeBuildingButton;
  }

  private Button createCancelButton(Popup popup) {
    Button cancelButton = new Button("Cancel");
    cancelButton.setStyle(
      "-fx-background-color: #9E9E9E; " + // Grey background
      "-fx-text-fill: white; " + // White text
      "-fx-background-radius: 10; " + // Rounded corners
      "-fx-border-radius: 10;" // Rounded corners
    );
    cancelButton.setOnAction(e -> popup.hide());
    return cancelButton;
  }

  @Override
  public void update() {
    Platform.runLater(() -> {
      mapGrid.getChildren().clear();
      loadMap();
    });
  }

  public void setSelectedBuildingType(BuildingType selectedBuildingType) {
    this.selectedBuildingType = selectedBuildingType;
  }

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
}
