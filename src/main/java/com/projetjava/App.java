package com.projetjava;

import com.projetjava.building.*;
import com.projetjava.game.GameManager;
import com.projetjava.game.GameTimer;
import com.projetjava.map.MapManager;
import com.projetjava.map.Position;
import com.projetjava.population.Population;
import com.projetjava.population.PopulationManager;
import com.projetjava.resources.ResourceManager;
import com.projetjava.resources.ResourceType;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    FXMLLoader fxmlLoader = new FXMLLoader(
      App.class.getResource(fxml + ".fxml")
    );
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    // // Créer une instance de la carte
    // MapManager mapManager = MapManager.getInstance(10, 10);
    // PopulationManager populationManager = PopulationManager.getInstance();

    // // Créer des bâtiments en utilisant la factory
    // Building quarry = BuildingFactory.createBuilding(BuildingType.QUARRY);
    // Building house = BuildingFactory.createBuilding(BuildingType.HOUSE);

    // // Ajouter des bâtiments à la carte
    // Position position1 = new Position(0, 0);

    // Position position2 = new Position(4, 4);

    // mapManager.placeBuilding(position1, quarry);
    // mapManager.placeBuilding(position2, house);

    // // Afficher la carte après l'ajout des bâtiments
    // System.out.println("Map after adding buildings:");
    // mapManager.showMap();

    // // Supprimer un bâtiment de la carte
    // mapManager.removeBuilding(position1);
    // System.out.println(
    //   "Map after removing building at position " +
    //   position1.getX() +
    //   ", " +
    //   position1.getY() +
    //   ":"
    // );
    // mapManager.showMap();

    // Population workers = populationManager.getPopulation().get(0);
    // workers.addPop(10);
    // System.out.println(workers.getFoodConsumption());
    // workers.addEmployed(7);
    // System.out.println(workers.getFoodConsumption());
    // System.out.println(populationManager.getTotalPopulation());

    GameManager gameManager = new GameManager();

    ResourceManager resourceManager = ResourceManager.getInstance();
    resourceManager.addResource(ResourceType.FOOD, 100);
    resourceManager.addResource(ResourceType.WOOD, 50);
    resourceManager.addResource(ResourceType.STONE, 30);

    // Ajouter un bâtiment pour tester
    Position position = new Position(2, 3);
    if (gameManager.addBuilding(position, BuildingType.WOODEN_CABIN)) {
        System.out.println("Building added at position: " + position.getX() + ", " + position.getY());
    } else {
        System.out.println("Failed to add building at position: " + position.getX() + ", " + position.getY());
    }

    gameManager.addWorkersToBuilding(position, 2);
    // Démarrer le timer du jeu
    GameTimer gameTimer = new GameTimer();
    gameTimer.start();

    // Thread pour afficher les ressources périodiquement
    Thread resourceDisplayThread = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(1000);
                gameManager.updateResources();
            gameManager.showResources();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    });

    resourceDisplayThread.start();

    // Boucle principale pour simuler le jeu
    while (true) {
        try {
            Thread.sleep(1000);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Vous pouvez ajouter d'autres simulations ou mises à jour ici
        // Par exemple, vérifier l'état du jeu, afficher des informations, etc.
    }
    

  }
}
