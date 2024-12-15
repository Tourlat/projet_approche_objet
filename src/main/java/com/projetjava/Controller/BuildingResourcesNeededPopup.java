package com.projetjava.Controller;

import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.resources.ResourceType;
import java.util.Iterator;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class BuildingResourcesNeededPopup {

  private Popup popup;
  private VBox vbox;
  private MapController mapController;

  /**
   * Constructor of BuildingResourcesNeededPopup
   * @param mapController - the map controller
   */
  public BuildingResourcesNeededPopup(MapController mapController) {
    this.popup = new Popup();
    this.vbox = createVBoxPopUp();
    popup.getContent().add(vbox);
    popup.setAutoHide(true);
    this.mapController = mapController;
  }

  /**
   * Update the popup content
   * @param buildingType - the building type
   * @param screenX - the screen X position
   * @param screenY - the screen Y position
   */
  public void show(BuildingType buildingType, double screenX, double screenY) {
    vbox.getChildren().clear(); // Clear existing children to avoid duplicates
    updatePopupContent(buildingType);

    // Adjust the screen X and Y positions
    double adjustedScreenX = screenX - 150;
    double adjustedScreenY = screenY - 70;

    popup.show(
      mapController.getMapGrid().getScene().getWindow(),
      adjustedScreenX,
      adjustedScreenY
    );
  }

  /**
   * Update the popup content
   * @param buildingType - the building type
   */
  private void updatePopupContent(BuildingType buildingType) {
    Label titleLabel = new Label(
      "Resources needed to build a " + buildingType.toString().replace("_", " ")
    );
    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    vbox.getChildren().add(titleLabel);

    Map<ResourceType, Integer> resourcesNeeded = mapController
      .getGameManager()
      .getConstructionCost(buildingType);
    Map<ResourceType, Integer> playerResources = mapController
      .getGameManager()
      .getPlayerResources();

    Iterator<Map.Entry<ResourceType, Integer>> iterator = resourcesNeeded
      .entrySet()
      .iterator();
    while (iterator.hasNext()) {
      HBox resourceRow = new HBox(20);
      resourceRow.setAlignment(Pos.CENTER);

      for (int i = 0; i < 2 && iterator.hasNext(); i++) {
        Map.Entry<ResourceType, Integer> entry = iterator.next();
        ResourceType resourceType = entry.getKey();
        int needed = entry.getValue();
        int available = playerResources.getOrDefault(resourceType, 0);

        Label resourceLabel = new Label(
          resourceType + ": " + available + "/" + needed
        );
        if (available < needed) {
          resourceLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        } else {
          resourceLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
        }

        VBox resourceBox = new VBox(5);
        resourceBox.setAlignment(Pos.CENTER);
        resourceBox.getChildren().add(resourceLabel);

        resourceRow.getChildren().add(resourceBox);
      }

      vbox.getChildren().add(resourceRow);
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
}
