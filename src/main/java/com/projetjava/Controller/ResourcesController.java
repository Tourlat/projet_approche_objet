package com.projetjava.Controller;

import java.util.HashMap;
import java.util.Map;

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
  private ImageView goldImage;

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
  private Label goldLabel;

  @FXML
  private Label workerLabel;

  @FXML
  public void initialize() {
    loadImages();
  }

  private Map<String, String> createImagePathsMap() {
    Map<String, String> imagePaths = new HashMap<>();
    imagePaths.put("foodImage", "/com/projetjava/sprites/resources_sprites/Food.png");
    imagePaths.put("woodImage", "/com/projetjava/sprites/resources_sprites/Wood.png");
    imagePaths.put("stoneImage", "/com/projetjava/sprites/resources_sprites/Stone.png");
    imagePaths.put("lumberImage", "/com/projetjava/sprites/resources_sprites/Lumber.png");
    imagePaths.put("coalImage", "/com/projetjava/sprites/resources_sprites/Coal.png");
    imagePaths.put("ironImage", "/com/projetjava/sprites/resources_sprites/Iron.png");
    imagePaths.put("steelImage", "/com/projetjava/sprites/resources_sprites/Steel.png");
    imagePaths.put("toolsImage", "/com/projetjava/sprites/resources_sprites/Tools.png");
    imagePaths.put("goldImage", "/com/projetjava/sprites/resources_sprites/Gold.png");
    imagePaths.put("workerImage", "/com/projetjava/sprites/worker.png");
    return imagePaths;
  }

  private void loadImages() {
    try {
      ImageCache imageCache = ImageCache.getInstance();
      Map<String, String> imagePaths = createImagePathsMap();

      for (Map.Entry<String, String> entry : imagePaths.entrySet()) {
        Image image = imageCache.getImage(entry.getValue());
        setImage(entry.getKey(), image);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setImage(String key, Image image) {
    switch (key) {
      case "foodImage":
        setImageView(foodImage, image);
        break;
      case "woodImage":
        setImageView(woodImage, image);
        break;
      case "stoneImage":
        setImageView(stoneImage, image);
        break;
      case "lumberImage":
        setImageView(lumberImage, image);
        break;
      case "coalImage":
        setImageView(coalImage, image);
        break;
      case "ironImage":
        setImageView(ironImage, image);
        break;
      case "steelImage":
        setImageView(steelImage, image);
        break;
      case "toolsImage":
        setImageView(toolsImage, image);
        break;
      case "goldImage":
        setImageView(goldImage, image);
        break;
      case "workerImage":
        setImageView(workerImage, image);
        break;
      default:
        throw new InvalidResourceLoadException("Error loading image: " + key);
    }
  }

  private void setImageView(ImageView imageView, Image image) {
    if (image == null || image.isError()) {
      throw new InvalidResourceLoadException(
          "Error loading image: " + (image != null ? image.getUrl() : "null"));
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
      int gold,
      int workers,
      int inhabitants) {
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
            tools +
            " " +
            gold);
    Platform.runLater(() -> {
      foodLabel.setText("Food: " + food);
      woodLabel.setText("Wood: " + wood);
      stoneLabel.setText("Stone: " + stone);
      lumberLabel.setText("Lumber: " + lumber);
      coalLabel.setText("Coal: " + coal);
      ironLabel.setText("Iron: " + iron);
      steelLabel.setText("Steel: " + steel);
      toolsLabel.setText("Tools: " + tools);
      goldLabel.setText("Gold: " + gold);
      workerLabel.setText("Workers: " + workers + "/" + inhabitants);
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
    int gold = gameManager
        .getResourceManager()
        .getResourceQuantity(ResourceType.GOLD);

    int[] workers = gameManager.getQuantityOfWorkers();

    updateResources(
        food,
        wood,
        stone,
        lumber,
        coal,
        iron,
        steel,
        tools,
        gold,
        workers[0],
        workers[1]);
  }
}
