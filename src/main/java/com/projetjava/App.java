package com.projetjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


import com.projetjava.building.*;
import com.projetjava.map.MapManager;
import com.projetjava.map.Position;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

   public static void main(String[] args) {
        // Créer une instance de la carte
        MapManager mapManager = MapManager.getInstance(10, 10);

        // Créer des bâtiments en utilisant la factory
        Building quarry = BuildingFactory.createBuilding(BuildingType.QUARRY);
        Building house = BuildingFactory.createBuilding(BuildingType.HOUSE);

        // Ajouter des bâtiments à la carte
        Position position1 = new Position(0, 0);
        
        Position position2 = new Position(4, 4);

        mapManager.placeBuilding(position1, quarry);
        mapManager.placeBuilding(position2, house);

        // Afficher la carte après l'ajout des bâtiments
        System.out.println("Map after adding buildings:");
        mapManager.showMap();

        // Supprimer un bâtiment de la carte
        mapManager.removeBuilding(position1);
        System.out.println("Map after removing building at position " + position1.getX() + ", " + position1.getY() + ":");
        mapManager.showMap();

        
    }

}