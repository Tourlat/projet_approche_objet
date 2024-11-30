package com.projetjava.View;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
    private final Label foodLabel;
    private final Label woodLabel;
    private final Label stoneLabel;

    public MainView(Stage stage) {
        VBox root = new VBox();
        foodLabel = new Label("Food: 0");
        woodLabel = new Label("Wood: 0");
        stoneLabel = new Label("Stone: 0");

        root.getChildren().addAll(foodLabel, woodLabel, stoneLabel);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    public void updateResources(int food, int wood, int stone) {
        foodLabel.setText("Food: " + food);
        woodLabel.setText("Wood: " + wood);
        stoneLabel.setText("Stone: " + stone);
    }
}