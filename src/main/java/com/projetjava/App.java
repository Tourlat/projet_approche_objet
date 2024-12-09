package com.projetjava;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


import java.net.URL;

public class App extends Application {

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    URL fxmlLocation = getClass().getResource("/com/projetjava/views/MenuView.fxml");
    if (fxmlLocation == null) {
        System.err.println("FXML file not found at the specified path.");
        return;
    } else {
        System.out.println("FXML file found at: " + fxmlLocation);
    }

    FXMLLoader loader = new FXMLLoader(fxmlLocation);

    scene = new Scene(loader.load(), 1920, 1080);
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();

    stage.setOnCloseRequest(event -> {
        System.out.println("Window close request received. Stopping the application...");
        Platform.exit();
        System.exit(0);
    });
  }

  public static void main(String[] args) {
      launch();
  }
}







// package com.projetjava;

// import com.projetjava.Controller.MainController;
// import com.projetjava.Controller.game.GameManager;
// import com.projetjava.Controller.game.GameTimer;
// import com.projetjava.Model.building.*;
// import com.projetjava.Model.map.Position;
// import com.projetjava.Model.resources.ResourceManager;
// import com.projetjava.Model.resources.ResourceType;

// import java.io.IOException;
// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

// /**
//  * JavaFX App
//  */
// public class App extends Application {

//   private static Scene scene;

//    @Override
//     public void start(Stage stage) throws IOException {
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetjava/views/MainView.fxml"));
//         loader.setController(new MainController());
//         scene = new Scene(loader.load(), 640, 480);
//         stage.setScene(scene);
//         stage.show();
      
//     }

//     static void setRoot(String fxml) throws IOException {
//         scene.setRoot(loadFXML(fxml));
//     }

//     private static Parent loadFXML(String fxml) throws IOException {
//         FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//         return fxmlLoader.load();
//     }

//   public static void main(String[] args) {
//     launch();
//     // // Créer une instance de la carte
//     // MapManager mapManager = MapManager.getInstance(10, 10);
//     // PopulationManager populationManager = PopulationManager.getInstance();

//     // // Créer des bâtiments en utilisant la factory
//     // Building quarry = BuildingFactory.createBuilding(BuildingType.QUARRY);
//     // Building house = BuildingFactory.createBuilding(BuildingType.HOUSE);

//     // // Ajouter des bâtiments à la carte
//     // Position position1 = new Position(0, 0);

//     // Position position2 = new Position(4, 4);

//     // mapManager.placeBuilding(position1, quarry);
//     // mapManager.placeBuilding(position2, house);

//     // // Afficher la carte après l'ajout des bâtiments
//     // System.out.println("Map after adding buildings:");
//     // mapManager.showMap();

//     // // Supprimer un bâtiment de la carte
//     // mapManager.removeBuilding(position1);
//     // System.out.println(
//     // "Map after removing building at position " +
//     // position1.getX() +
//     // ", " +
//     // position1.getY() +
//     // ":"
//     // );
//     // mapManager.showMap();

//     // Population workers = populationManager.getPopulation().get(0);
//     // workers.addPop(10);
//     // System.out.println(workers.getFoodConsumption());
//     // workers.addEmployed(7);
//     // System.out.println(workers.getFoodConsumption());
//     // System.out.println(populationManager.getTotalPopulation());

    

//     // Démarrer le timer du jeu
//     // GameTimer gameTimer = GameTimer.getInstance();
//     // gameTimer.start();

//     // Thread pour afficher les ressources périodiquement
//     // Thread resourceDisplayThread = new Thread(() -> {
//     //   while (gameTimer.isRunning()) {
//     //     if (gameTimer.isUpdated()) {
//     //       // Afficher les ressources ici
//     //       System.out.println("Ressources mises à jour à l'heure: " + gameTimer.getTimeOfDay());
//     //       gameManager.updateResources();
//     //       gameManager.consumeFood();
//     //       System.out.println("Food: " + resourceManager.getResourceQuantity(ResourceType.FOOD) + " Wood: "
//     //           + resourceManager.getResourceQuantity(ResourceType.WOOD) + " Stone: "
//     //           + resourceManager.getResourceQuantity(ResourceType.STONE));
//     //       // Réinitialiser l'état de mise à jour
//     //       gameTimer.setUpdated(false);
//     //     }
//     //     try {
//     //       Thread.sleep(1000); // Attendre une seconde avant de vérifier à nouveau
//     //     } catch (InterruptedException e) {
//     //       e.printStackTrace();
//     //     }
//     //   }
//     // });

//     // resourceDisplayThread.start();

//     // // Boucle principale pour simuler le jeu
//     // while (true) {
//     //   try {
//     //     Thread.sleep(1000);

//     //   } catch (InterruptedException e) {
//     //     e.printStackTrace();
//     //   }
//     //   // Vous pouvez ajouter d'autres simulations ou mises à jour ici
//     //   // Par exemple, vérifier l'état du jeu, afficher des informations, etc.
//     // }

//     // GameManager gameManager = new GameManager();

//     // gameManager.initializeGame();

//     // ResourceManager resourceManager = ResourceManager.getInstance();
//     // resourceManager.addResource(ResourceType.FOOD, 100);
//     // resourceManager.addResource(ResourceType.WOOD, 50);
//     // resourceManager.addResource(ResourceType.STONE, 30);

//     // Ajouter un bâtiment pour tester
//     // Position position = new Position(2, 3);
//     // if (gameManager.addBuilding(position, BuildingType.WOODEN_CABIN)) {
//     //   System.out.println("Building added at position: " + position.getX() + ", " + position.getY());
//     // } else {
//     //   System.out.println("Failed to add building at position: " + position.getX() + ", " + position.getY());
//     // }

//     // gameManager.addWorkersToBuilding(position, 2);
//     // GameTimer gameTimer = GameTimer.getInstance(); 
//     // gameTimer.start();

//     // while(true){
//     //   try {
//     //     Thread.sleep(1000);
//     //   } catch (InterruptedException e) {
//     //     e.printStackTrace();
//     //   }
//     //   System.out.println("Time: " + gameTimer.getTimeOfDay());
//     //   if(gameTimer.getTimeOfDay() == 12){
//     //     gameManager.consumeFood();
//     //   }
//     //   if(gameTimer.getTimeOfDay() == 18){
//     //     gameManager.updateResources();
//     // }}

//   }
// }
