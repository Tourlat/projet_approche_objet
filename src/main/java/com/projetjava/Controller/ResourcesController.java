// package com.projetjava.Controller;

// import javafx.stage.Stage;

// import com.projetjava.Model.Model;
// import com.projetjava.View.View;
// import com.projetjava.View.ViewListener;

// public class Controller implements ViewListener {
// private final Model model;
// private final View view;
// private final BagOfCommands bagOfCommands;

// public Controller(Stage stage) {
// model = new Model();
// view = new View(stage);
// bagOfCommands = new BagOfCommands(model);
// for (Event event : Event.values()) {
// view.addButton(event.getText(), event.getId(),
// geteventHandlerByEvent(event));
// }
// model.setListener(view);
// }

// @Override
// public void addBuildingButtonClicked() {
// System.out.println("Le bouton 'Add building' a été cliqué.");
// bagOfCommands.add(new addBuildingCommand());
// }

// @Override
// public void addEmployeesToBuildingButtonClicked() {
// System.out.println("Le bouton 'Add Employees' a été cliqué.");
// bagOfCommands.add(new addEmployeesToBuildingCommand());
// }

// /**
// */
// private EventHandler<ActionEvent> geteventHandlerByEvent(Event event) {
// return switch (event) {
// case ADD_BUILDING ->
// _ -> addBuildingButtonClicked();
// case ADD_EMPLOYEES_TO_BUILDING ->
// _ -> addEmployeesToBuildingButtonClicked();
// };
// }

// }
package com.projetjava.Controller;

import com.projetjava.Controller.game.GameManager;
import com.projetjava.Model.resources.ResourceType;
import com.projetjava.View.ImageCache;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourcesController implements Observer {

    @FXML
    private Label foodLabel;

    @FXML
    private Label woodLabel;

    @FXML
    private Label stoneLabel;

    @FXML
    private ImageView foodImage;


    @FXML
    private ImageView woodImage;

    @FXML
    private ImageView stoneImage;

    @FXML
    public void initialize() {
       
        loadImages();
    }

    private void loadImages() {
        try {
            ImageCache imageCache = ImageCache.getInstance();
            Image foodImg = imageCache.getImage("/com/projetjava/sprites/resources_sprites/Food.png");
            Image woodImg = imageCache.getImage("/com/projetjava/sprites/resources_sprites/Wood.png");
            Image stoneImg = imageCache.getImage("/com/projetjava/sprites/resources_sprites/Stone.png");

            if (foodImg.isError()) {
                System.err.println("Error loading food image.");
            } else {
                foodImage.setImage(foodImg);
                foodImage.setFitHeight(100);
                foodImage.setFitWidth(100);
                System.out.println("Food image loaded successfully.");
            }

            if (woodImg.isError()) {
                System.err.println("Error loading wood image.");
            } else {
                woodImage.setImage(woodImg);
                woodImage.setFitHeight(100);
                woodImage.setFitWidth(100);
                System.out.println("Wood image loaded successfully.");
            }

            if (stoneImg.isError()) {
                System.err.println("Error loading stone image.");
            } else {

                stoneImage.setImage(stoneImg);
                stoneImage.setFitHeight(100);
                stoneImage.setFitWidth(100);
                System.out.println("Stone image loaded successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateResources(int food, int wood, int stone) {
        System.out.println(food + " " + wood + " " + stone);
         Platform.runLater(() -> {
            foodLabel.setText("Food: " + food);
            woodLabel.setText("Wood: " + wood);
            stoneLabel.setText("Stone: " + stone);
        });
    }

    @Override
    public void update() {
        System.out.println("Resources update");
        
        int food = GameManager.getInstance().getResourceManager().getResourceQuantity(ResourceType.FOOD);
        int wood = GameManager.getInstance().getResourceManager().getResourceQuantity(ResourceType.WOOD);
        int stone = GameManager.getInstance().getResourceManager().getResourceQuantity(ResourceType.STONE);
        updateResources(food, wood, stone);
    }

    
}