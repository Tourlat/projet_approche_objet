package com.projetjava.Controller;

import java.io.IOException;

import com.projetjava.Controller.game.GameManager;
import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.map.Position;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController {
    private GameManager gameManager;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ResourcesController resourcesController;

    @FXML
    public void initialize() {
        // Initialize the GameManager
        System.out.println("Initializing GameManager...");
        gameManager = GameManager.getInstance();
        gameManager.initializeGame();

        // Load the ResourcesView
        Pane resourcesView = null;
        try {
            resourcesView = loadView("/com/projetjava/views/ResourcesView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the ResourcesView to the right of the mainPane
        mainPane.setRight(resourcesView);

        gameManager.addObserver(resourcesController);

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

        return pane;
    }
}