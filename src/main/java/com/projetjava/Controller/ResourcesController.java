package com.projetjava.Controller;

import com.projetjava.Model.game.GameManager;
import com.projetjava.Model.resources.ResourceType;
import com.projetjava.customexceptions.InvalidResourceLoadException;
import com.projetjava.util.ImageCache;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourcesController implements ResourceObserver {

  @FXML
  private ImageView foodImage, woodImage, stoneImage, lumberImage, coalImage, ironImage, steelImage, toolsImage, goldImage, workerImage;

  @FXML
  private Label foodLabel, woodLabel, stoneLabel, lumberLabel, coalLabel, ironLabel, steelLabel, toolsLabel, goldLabel, workerLabel;

  private Map<String, ImageView> imageViewMap = new HashMap<>();
  private Map<String, Label> labelMap = new HashMap<>();

  /**
   * Initialize the ResourcesController.
   */
  @FXML
  public void initialize() {
    initializeImageViewMap();
    initializeLabelMap();
    loadImages();
  }

  /**
   * Initialize the ImageView map.
   */
  private void initializeImageViewMap() {
    imageViewMap.put("foodImage", foodImage);
    imageViewMap.put("woodImage", woodImage);
    imageViewMap.put("stoneImage", stoneImage);
    imageViewMap.put("lumberImage", lumberImage);
    imageViewMap.put("coalImage", coalImage);
    imageViewMap.put("ironImage", ironImage);
    imageViewMap.put("steelImage", steelImage);
    imageViewMap.put("toolsImage", toolsImage);
    imageViewMap.put("goldImage", goldImage);
    imageViewMap.put("workerImage", workerImage);
  }

  /**
   * Initialize the Label map.
   */
  private void initializeLabelMap() {
    labelMap.put("foodLabel", foodLabel);
    labelMap.put("woodLabel", woodLabel);
    labelMap.put("stoneLabel", stoneLabel);
    labelMap.put("lumberLabel", lumberLabel);
    labelMap.put("coalLabel", coalLabel);
    labelMap.put("ironLabel", ironLabel);
    labelMap.put("steelLabel", steelLabel);
    labelMap.put("toolsLabel", toolsLabel);
    labelMap.put("goldLabel", goldLabel);
    labelMap.put("workerLabel", workerLabel);
  }

  /**
   * Create a map of image paths.
   * @return the map of image paths
   */
  private Map<String, String> createImagePathsMap() {
    Map<String, String> imagePaths = new HashMap<>();
    imagePaths.put(
      "foodImage",
      "/com/projetjava/sprites/resources_sprites/Food.png"
    );
    imagePaths.put(
      "woodImage",
      "/com/projetjava/sprites/resources_sprites/Wood.png"
    );
    imagePaths.put(
      "stoneImage",
      "/com/projetjava/sprites/resources_sprites/Stone.png"
    );
    imagePaths.put(
      "lumberImage",
      "/com/projetjava/sprites/resources_sprites/Lumber.png"
    );
    imagePaths.put(
      "coalImage",
      "/com/projetjava/sprites/resources_sprites/Coal.png"
    );
    imagePaths.put(
      "ironImage",
      "/com/projetjava/sprites/resources_sprites/Iron.png"
    );
    imagePaths.put(
      "steelImage",
      "/com/projetjava/sprites/resources_sprites/Steel.png"
    );
    imagePaths.put(
      "toolsImage",
      "/com/projetjava/sprites/resources_sprites/Tools.png"
    );
    imagePaths.put(
      "goldImage",
      "/com/projetjava/sprites/resources_sprites/Gold.png"
    );
    imagePaths.put("workerImage", "/com/projetjava/sprites/worker.png");
    return imagePaths;
  }

  /**
   * Load the images.
   */
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

  /**
   * Set the image of a given key.
   * @param key - the key of the image
   * @param image - the image to set
   */
  private void setImage(String key, Image image) {
    ImageView imageView = imageViewMap.get(key);
    if (imageView != null) {
      setImageView(imageView, image);
    } else {
      throw new InvalidResourceLoadException("Error loading image: " + key);
    }
  }

  /**
   * Set the image of a given ImageView.
   * @param imageView - the ImageView to set
   * @param image - the image to set
   */
  private void setImageView(ImageView imageView, Image image) {
    if (image == null || image.isError()) {
      throw new InvalidResourceLoadException(
        "Error loading image: " + (image != null ? image.getUrl() : "null")
      );
    } else {
      imageView.setImage(image);
      imageView.setFitHeight(50);
      imageView.setFitWidth(50);
    }
  }

  /**
   * Update the resources.
   * @param food - the food quantity
   * @param wood - the wood quantity
   * @param stone - the stone quantity
   * @param lumber - the lumber quantity
   * @param coal - the coal quantity
   * @param iron - the iron quantity
   * @param steel - the steel quantity
   * @param tools - the tools quantity
   * @param gold - the gold quantity
   * @param workers - the workers quantity
   * @param inhabitants - the inhabitants quantity
   * @return the updated resources
   */
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
    int inhabitants
  ) {
    Platform.runLater(() -> {
      labelMap.get("foodLabel").setText("Food: " + food);
      labelMap.get("woodLabel").setText("Wood: " + wood);
      labelMap.get("stoneLabel").setText("Stone: " + stone);
      labelMap.get("lumberLabel").setText("Lumber: " + lumber);
      labelMap.get("coalLabel").setText("Coal: " + coal);
      labelMap.get("ironLabel").setText("Iron: " + iron);
      labelMap.get("steelLabel").setText("Steel: " + steel);
      labelMap.get("toolsLabel").setText("Tools: " + tools);
      labelMap.get("goldLabel").setText("Gold: " + gold);
      labelMap
        .get("workerLabel")
        .setText("Employees: " + workers + "/" + inhabitants);
    });
  }

  /**
   * Update the resources.
   */
  @Override
  public void update() {
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
      workers[1]
    );
  }
}
