package com.projetjava.Controller;

import com.projetjava.Model.building.Building;
import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.Model.resources.ResourceType;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class BuildingOptionsPopup {

  private Popup popup;
  private VBox vbox;
  private MapController mapController;

  /**
   * Constructor of BuildingOptionsPopup
   * @param mapController - the map controller
   */
  public BuildingOptionsPopup(MapController mapController) {
    this.popup = new Popup();
    this.vbox = createVBoxPopUp();
    popup.getContent().add(vbox);
    popup.setAutoHide(true);
    this.mapController = mapController;
  }

  /**
   * Show the building options popup
   * @param building - the building
   * @param x - the x position
   * @param y - the y position
   * @param screenX - the screen x position
   * @param screenY - the screen y position
   */
  public void show(
    Building building,
    int x,
    int y,
    double screenX,
    double screenY
  ) {
    vbox.getChildren().clear(); // Clear existing children to avoid duplicates
    updatePopupContent(building, x, y);
    popup.show(
      mapController.getMapGrid().getScene().getWindow(),
      screenX,
      screenY
    );
  }

  /**
   * Update the popup content
   * @param building - the building
   * @param x - the x position
   * @param y - the y position
   */
  private void updatePopupContent(Building building, int x, int y) {
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
  }

  /**
   * Create a VBox for the popup
   * @return the VBox
   */
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

  /**
   * Create a label with the number of workers in the building
   * @param building - the building
   * @return the worker label
   */
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

  /**
   * Create a string with the production of the building
   * @param building - the building
   * @return the production string
   */
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

  /**
   * Create a remove worker button
   * @param workerLabel - the worker label
   * @param productionLabel - the production label
   * @param building - the building
   * @param origin - the origin position
   * @return the remove worker button
   */
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
        mapController.getGameManager().removeEmployeesFromBuilding(origin, 1);
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

  /**
   * Create an add worker button
   * @param workerLabel - the worker label
   * @param productionLabel - the production label
   * @param building - the building
   * @param origin - the origin position
   * @return the add worker button
   */
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
        mapController.getGameManager().getAvailableWorkers() > 0
      ) {
        mapController.getGameManager().addEmployeesToBuilding(origin, 1);
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

  /**
   * Create a remove building button
   * @param origin - the origin position
   * @param popup - the popup
   * @return the remove building button
   */
  private Button createRemoveBuildingButton(Position origin, Popup popup) {
    Button removeBuildingButton = new Button("Remove Building");
    removeBuildingButton.setStyle(
      "-fx-background-color: #f44336; " + // Red background
      "-fx-text-fill: white; " + // White text
      "-fx-background-radius: 10; " + // Rounded corners
      "-fx-border-radius: 10;" // Rounded corners
    );
    removeBuildingButton.setOnAction(e -> {
      mapController.getGameManager().removeBuilding(origin);
      mapController.update();
      popup.hide();
    });
    return removeBuildingButton;
  }

  /**
   * Create a cancel button
   * @param popup - the popup
   * @return the cancel button
   */
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
}
