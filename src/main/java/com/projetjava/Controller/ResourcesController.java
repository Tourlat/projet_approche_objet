package com.projetjava.Controller;

import com.projetjava.Model.game.GameManager;
import com.projetjava.Model.resources.ResourceType;
import com.projetjava.customexceptions.InvalidResourceLoadException;
import com.projetjava.util.ImageCache;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourcesController implements ResourceObserver {

  @FXML
  private ImageView foodImage;

  @FXML
  private ImageView woodImage;

  @FXML
  private ImageView stoneImage;

  @FXML
  private ImageView lumberImage;

  @FXML
  private ImageView coalImage;

  @FXML
  private ImageView ironImage;

  @FXML
  private ImageView steelImage;

  @FXML
  private ImageView toolsImage;

  @FXML
  private ImageView workerImage;

  @FXML
  private Label foodLabel;

  @FXML
  private Label woodLabel;

  @FXML
  private Label stoneLabel;

  @FXML
  private Label lumberLabel;

  @FXML
  private Label coalLabel;

  @FXML
  private Label ironLabel;

  @FXML
  private Label steelLabel;

  @FXML
  private Label toolsLabel;

  @FXML 
  private Label workerLabel;

  @FXML
  public void initialize() {
    loadImages();
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      Image foodImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Food.png"
      );
      Image woodImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Wood.png"
      );
      Image stoneImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Stone.png"
      );
      Image lumberImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Lumber.png"
      );
      Image coalImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Coal.png"
      );
      Image ironImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Iron.png"
      );
      Image steelImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Steel.png"
      );
      Image toolsImg = imageCache.getImage(
        "/com/projetjava/sprites/resources_sprites/Tools.png"
      );
      Image workerImg = imageCache.getImage(
        "/com/projetjava/sprites/worker.png"
      );

      setImageView(foodImage, foodImg);
      setImageView(woodImage, woodImg);
      setImageView(stoneImage, stoneImg);
      setImageView(lumberImage, lumberImg);
      setImageView(coalImage, coalImg);
      setImageView(ironImage, ironImg);
      setImageView(steelImage, steelImg);
      setImageView(toolsImage, toolsImg);
      setImageView(workerImage, workerImg);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setImageView(ImageView imageView, Image image) {
    if (image == null || image.isError()) {
      throw new InvalidResourceLoadException("Error loading image: " + (image != null ? image.getUrl() : "null"));
    } else {
      imageView.setImage(image);
      imageView.setFitHeight(50);
      imageView.setFitWidth(50);
    }
  }

  @Override
  public void updateResources(
    int food,
    int wood,
    int stone,
    int lumber,
    int coal,
    int iron,
    int steel,
    int tools,
    int workers,
    int inahbitants
  ) {
    System.out.println(
      food +
      " " +
      wood +
      " " +
      stone +
      " " +
      lumber +
      " " +
      coal +
      " " +
      iron +
      " " +
      steel +
      " " +
      tools
    );
    Platform.runLater(() -> {
      foodLabel.setText("Food: " + food);
      woodLabel.setText("Wood: " + wood);
      stoneLabel.setText("Stone: " + stone);
      lumberLabel.setText("Lumber: " + lumber);
      coalLabel.setText("Coal: " + coal);
      ironLabel.setText("Iron: " + iron);
      steelLabel.setText("Steel: " + steel);
      toolsLabel.setText("Tools: " + tools);
      workerLabel.setText("Workers: " + workers + "/" + inahbitants);
    });
  }

  @Override
  public void update() {
    System.out.println("Resources update");

    GameManager gameManager = GameManager.getInstance();
    int food = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.FOOD);
    int wood = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.WOOD);
    int stone = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.STONE);
    int lumber = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.LUMBER);
    int coal = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.COAL);
    int iron = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.IRON);
    int steel = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.STEEL);
    int tools = gameManager
      .getResourceManager()
      .getResourceQuantity(ResourceType.TOOL);
    
    int[] workers = gameManager.getQuantityOfWorkers();


    updateResources(food, wood, stone, lumber, coal, iron, steel, tools, workers[0], workers[1]);
  }
}
