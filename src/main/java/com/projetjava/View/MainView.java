package com.projetjava.View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {

  private final Label foodLabel;
  private final Label woodLabel;
  private final Label stoneLabel;

  public MainView(Stage stage) {
    BorderPane root = new BorderPane();
    // Labels pour les ressources
    VBox resourceBox = new VBox();
    foodLabel = new Label("Food: 0");
    woodLabel = new Label("Wood: 0");
    stoneLabel = new Label("Stone: 0");
    resourceBox.getChildren().addAll(foodLabel, woodLabel, stoneLabel);

    // Barre de sélection en bas
    HBox selectionBar = new HBox();
    Button houseButton = new Button("House");
    Button farmButton = new Button("Farm");
    Button mineButton = new Button("Mine");

    // Ajouter des gestionnaires d'événements pour les boutons
    houseButton.setOnAction(event -> placeBuilding("House"));
    farmButton.setOnAction(event -> placeBuilding("Farm"));
    mineButton.setOnAction(event -> placeBuilding("Mine"));

    selectionBar.getChildren().addAll(houseButton, farmButton, mineButton);

    // Ajouter les éléments au BorderPane
    root.setTop(resourceBox);
    root.setBottom(selectionBar);

    Scene scene = new Scene(root, 300, 200);
    stage.setScene(scene);
    stage.show();
  }

  public void updateResources(int food, int wood, int stone) {
    foodLabel.setText("Food: " + food);
    woodLabel.setText("Wood: " + wood);
    stoneLabel.setText("Stone: " + stone);
  }

  private void placeBuilding(String buildingType) {
    // Logique pour placer le bâtiment sur la carte
    System.out.println("Placing building: " + buildingType);
    // Vous pouvez ajouter ici la logique pour placer le bâtiment sur la carte
  }
}
