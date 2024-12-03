package com.projetjava.Controller;

import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.Model.building.Building;
import com.projetjava.View.ImageCache;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class MapController implements Observer{

    @FXML
    private GridPane mapGrid;

    private Image ground;
    private Image woodenCabin;

    @FXML
    public void initialize() {
        loadImages();
        loadMap();
    }

    private void loadImages() {
        try {
            ImageCache imageCache = ImageCache.getInstance();
            ground = imageCache.getImage("/com/projetjava/sprites/ground.png");
            woodenCabin = imageCache.getImage("/com/projetjava/sprites/resources_sprites/Food.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMap() {
        MapManager mapManager = MapManager.getInstance(30, 25); 

        for (int x = 0; x < mapManager.getWidth(); x++) {
            for (int y = 0; y < mapManager.getHeight(); y++) {
                Pane cell = new Pane();
                cell.setPrefSize(25, 25);

                final int finalX = x;
                final int finalY = y;


                ImageView buildingImage = new ImageView();

                Building building = mapManager.getBuilding(new Position(x, y));
               
                if (building != null) {
                    switch (building.getType()) {
                        case WOODEN_CABIN:
                            buildingImage.setImage(woodenCabin);    
                            break;
                        default:
                            buildingImage.setImage(ground);
                            break;
                    }
                } else {
                    buildingImage.setImage(ground);
                }

                buildingImage.setFitHeight(25);
                buildingImage.setFitWidth(25);
            
                cell.getChildren().add(buildingImage);
           
                // Add mouse click event handler
                cell.setOnMouseClicked(event -> handleMouseClick(finalX, finalY));

                mapGrid.add(cell, x, y);
            }
        }
    }

    private void handleMouseClick( int x, int y) {
        System.out.println("Mouse clicked at position: (" + x + ", " + y + ")");
    }

    public void update() {
        // Clear the existing cells
        mapGrid.getChildren().clear();
        // Reload the map data
        loadMap();
    }
}