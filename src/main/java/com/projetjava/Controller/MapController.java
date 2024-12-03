package com.projetjava.Controller;

import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.View.ImageCache;
import com.projetjava.Model.building.Building;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class MapController {

    @FXML
    private GridPane mapGrid;

    private Image ground;

    // Buidling images ...

    private Image woodenCabin;
    // private Image farmImage;
    // private Image mineImage;


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
            
            // Load all images here
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


                ImageView buildingImage = new ImageView();

                Building building = mapManager.getBuilding(new Position(x, y));
               
                if (building != null) {
                    switch (building.getType()) {
                        case WOODEN_CABIN:
                            buildingImage.setImage(woodenCabin);
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
           

                mapGrid.add(cell, x, y);
            }
        }
    }
}