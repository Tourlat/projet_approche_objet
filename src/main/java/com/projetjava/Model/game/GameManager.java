package com.projetjava.Model.game;

import com.projetjava.Controller.EndObserver;
import com.projetjava.Controller.Observer;
import com.projetjava.Controller.ResourceObserver;
import com.projetjava.Model.building.Building;
import com.projetjava.Model.building.BuildingFactory;
import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.Model.population.Workers;
import com.projetjava.Model.resources.Resource;
import com.projetjava.Model.resources.ResourceManager;
import com.projetjava.Model.resources.ResourceType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;

public class GameManager implements Observer {

  private ResourceManager resourceManager;
  private Workers workers;
  private MapManager mapManager;
  private GameTimer gameTimer;

  private boolean win;

  private static GameManager instance;

  private List<Observer> observers = new ArrayList<>();
  private List<ResourceObserver> resourceObservers = new ArrayList<>();
  private EndObserver endObserver;

  /**
   *  Singleton pattern : only one instance of GameManager
   * @return the GameManager instance
   */
  public static GameManager getInstance() {
    if (instance == null) {
      instance = new GameManager();
    }
    return instance;
  }

  /**
   * Constructor
   */
  private GameManager() {
    this.resourceManager = ResourceManager.getInstance();
    this.workers = Workers.getInstance();
    this.mapManager = MapManager.getInstance();
    this.gameTimer = GameTimer.getInstance();
    gameTimer.addObserver(this);
  }

  /**
   * Update the game
   */
  @Override
  public void update() {
    System.out.println("Updating the game...");

    Platform.runLater(() -> {
      updateResources();
      consumeFood();
      notifyObservers();
    });
  }

  /**
   * Initialize the game
   */
  public void initializeGame() {
    resourceManager.addResource(ResourceType.FOOD, 20);
    resourceManager.addResource(ResourceType.WOOD, 50);
    resourceManager.addResource(ResourceType.STONE, 30);
    gameTimer.start();
    notifyResourceObservers();
  }

  /**
   * Function to add A LOT of resources to the game (for testing purposes)
   */
  public void adminMode() {
    resourceManager.addResource(ResourceType.FOOD, 50000);
    resourceManager.addResource(ResourceType.WOOD, 10000);
    resourceManager.addResource(ResourceType.STONE, 10000);
    resourceManager.addResource(ResourceType.COAL, 10000);
    resourceManager.addResource(ResourceType.IRON, 10000);
    resourceManager.addResource(ResourceType.STEEL, 10000);
    resourceManager.addResource(ResourceType.CEMENT, 10000);
    resourceManager.addResource(ResourceType.LUMBER, 10000);
    resourceManager.addResource(ResourceType.TOOL, 10000);
    notifyResourceObservers();
  }

  /**
   * Get the quantity of workers
   * @return an array containing the number of employed and total workers
   */
  public int[] getQuantityOfWorkers() {
    return new int[] { workers.getEmployed(), workers.getTotal() };
  }

  /**
   * Check if the player has enough resources to build a building
   * @param building the building to build
   * @return true if the player has enough resources, false otherwise
   */
  public boolean haveResourcesToBuild(BuildingType building) {
    Building newBuilding = BuildingFactory.createBuilding(building);
    for (Map.Entry<ResourceType, Integer> entry : newBuilding
      .getConstructionCost()
      .entrySet()) {
      if (
        resourceManager.getResourceQuantity(entry.getKey()) < entry.getValue()
      ) {
        return false;
      }
    }
    return true;
  }

  /**
   * Add a building to the map
   * @param position the origin position to place the building (top left corner)
   * @param building the building to add
   * @return true if the building was added, false otherwise
   */
  public boolean addBuilding(Position position, BuildingType building) {
    Building newBuilding = BuildingFactory.createBuilding(building);

    // check if the player has enough resources to build the building
    if (!haveResourcesToBuild(building)) {
      return false;
    }

    boolean success = mapManager.placeBuilding(position, newBuilding);

    // start a thread to build the building
    Thread thread = new Thread(() -> {
      try {
        System.out.println("finished building");
        if ((success)) {
          System.out.println(
            newBuilding.getConstructionTime() +
            "  " +
            newBuilding.isConstructed() +
            " to build"
          );
          Thread.sleep(newBuilding.getConstructionTime() * 1000);
          for (Map.Entry<ResourceType, Integer> entry : newBuilding
            .getConstructionCost()
            .entrySet()) {
            resourceManager.subtractResource(entry.getKey(), entry.getValue());
          }
          workers.addUnemployed(newBuilding.getPopulationCreated());
          newBuilding.setConstructTrue();
          notifyResourceObservers();
          notifyObservers();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    thread.start();

    return success;
  }

  /**
   * Remove a building from the map, and its populations
   * @param position the position of the building to remove
   * @return true if the building was removed correctly, false otherwise
   */
  public boolean removeBuilding(Position position) {
    Building building = mapManager.getBuilding(position);
    boolean success = mapManager.removeBuilding(position);
    if (success) {
      //Remove the population
      workers.removeEmployed(building.getCurrentEmployees());
      workers.removeUnemployed(building.getCurrentPopulation());
      notifyResourceObservers();
    }
    return success;
  }

  /**
   * Add employees to a building
   * @param position the position of the building
   * @param numberOfEmployees the number of employees to add
   * @return true if the employees were added, false otherwise
   */
  public boolean addEmployeesToBuilding(
    Position position,
    int numberOfEmployees
  ) {
    Building building = mapManager.getBuilding(position);
    if (building != null && workers.getUnemployed() >= numberOfEmployees) {
      building.addWorkers(numberOfEmployees);
      workers.addEmployed(numberOfEmployees);
      notifyResourceObservers();
      return true;
    }
    return false;
  }

  /**
   * Remove employees from a building
   * @param position the position of the building
   * @param numberOfEmployees the number of employees to remove
   * @return true if the employees were removed, false otherwise
   */
  public boolean removeEmployeesFromBuilding(
    Position position,
    int numberOfEmployees
  ) {
    Building building = mapManager.getBuilding(position);
    if (
      building != null && building.getCurrentEmployees() >= numberOfEmployees
    ) {
      building.removeWorkers(numberOfEmployees);
      workers.removeEmployed(numberOfEmployees);
      notifyResourceObservers();
      return true;
    }
    return false;
  }

  /**
   * Add the producton of a building to the resources global pool
   * @param building the building that produces resources
   */
  public void addBuildingProduction(Building building) {
    Map<ResourceType, Integer> production = building.getCurrentProduction();
    for (Map.Entry<ResourceType, Integer> entry : production.entrySet()) {
      resourceManager.addResource(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Consume the resources needed for a building maintenance
   * @param building the building that needs maintenance
   */
  public void consumeBuildingMaintenance(Building building) {
    Map<ResourceType, Integer> consumption = building.getConsumption();
    for (Map.Entry<ResourceType, Integer> entry : consumption.entrySet()) {
      resourceManager.subtractResource(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Update the resources global pool with the production and maintenance of the buildings
   */
  public void updateResources() {
    for (Map.Entry<Position, Building> entry : mapManager
      .getBuildings()
      .entrySet()) {
      Building building = entry.getValue();
      // Check if the building can produce with the available resources and is
      // constructed
      if (
        building.canProduce(resourceManager.getResources()) &&
        building.isConstructed()
      ) {
        consumeBuildingMaintenance(building);
        addBuildingProduction(building);
      }
    }

    notifyResourceObservers();
  }

  /**
   * Consume the food needed for the employees / population each period of time
   */
  public void consumeFood() {
    int foodAvailable = resourceManager.getResourceQuantity(ResourceType.FOOD);
    workers.foodConsumption(foodAvailable);
    System.out.println("Food consumption: " + workers.getFoodConsumption());
    resourceManager.setResourceQuantity(
      ResourceType.FOOD,
      Math.max(0, foodAvailable - workers.getFoodConsumption())
    );
    notifyResourceObservers();
  }

  /**
   * Get the resourceManager instance
   * @return the resourceManager
   */
  public ResourceManager getResourceManager() {
    return resourceManager;
  }

  /**
   * Get the gameTimer instance
   * @return the gameTimer
   */
  public GameTimer getGameTimer() {
    return gameTimer;
  }

  /**
   * Get the number of available workers (= unemployed workers)
   * @return the number of available workers
   */
  public int getAvailableWorkers() {
    return workers.getUnemployed();
  }

  //////////////////////////////////////////////////////
  /***************** OBSERVER METHODS *****************/
  ///////////////////////////////////////////////////////

  /**
   * Add an observer to the list of observers
   * @param observer the observer to add
   */
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  /**
   * Remove an observer from the list of observers
   * @param observer the observer to remove
   */
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  /**
   * Update all observers
   */
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }

  /**
   * Add a resource observer
   * @param observer the observer to add
   */
  public void addResourceObserver(ResourceObserver observer) {
    resourceObservers.add(observer);
  }

  /**
   * Remove a resource observer
   * @param observer the observer to remove
   */
  public void removeResourceObserver(ResourceObserver observer) {
    resourceObservers.remove(observer);
  }

  /**
   * Update all resource observers
   */
  public void notifyResourceObservers() {
    for (ResourceObserver observer : resourceObservers) {
      observer.update();
    }
  }

  /**
   * Set the end observer
   * @param observer the observer to set
   */
  public void setEndObserver(EndObserver observer) {
    endObserver = observer;
  }

  /**
   * Notify (update) the end observer
   */
  public void notifyEndObserver() {
    if (endObserver != null) {
      endObserver.updateEnd(win);
    }
  }

  /**
   * Remove the end observer
   */
  public void removeEndObserver() {
    endObserver = null;
  }

  public Map<ResourceType, Integer> getConstructionCost(BuildingType building) {
    return BuildingFactory.createBuilding(building).getConstructionCost();
  }

  public Map<ResourceType, Integer> getPlayerResources() {
    Map<ResourceType, Resource> resources = resourceManager.getResources();
    Map<ResourceType, Integer> resourceQuantities = new HashMap<>();

    for (Map.Entry<ResourceType, Resource> entry : resources.entrySet()) {
      resourceQuantities.put(entry.getKey(), entry.getValue().getQuantity());
    }

    return resourceQuantities;
  }

  ////////////////////////////////////////////////////////////
  /***************** END OFOBSERVER METHODS *****************/
  /////////////////////////////////////////////////////////////

  /**
   * Function to set the game in win condition
   */
  public void Win() {
    System.out.println("Win command called");
    resourceManager.addResource(ResourceType.GOLD, 10);
    notifyResourceObservers();
  }

  /**
   * Function to set the game in lose condition
   */
  public void Lose() {
    System.out.println("Lose command called");
    resourceManager.setResourceQuantity(ResourceType.WOOD, 0);
    resourceManager.setResourceQuantity(ResourceType.FOOD, 0);
    workers.foodConsumption(1000);
  }

  /**
   * Check if the game has ended
   */
  public void hasGameEnded() {
    // check if the player has lost i.e. no more wood/food and no more population
    if (
      (getResourceManager().getResourceQuantity(ResourceType.WOOD) <= 0) &&
      (getQuantityOfWorkers()[1] <= 0)
    ) {
      System.out.println("Game Over...");
      win = false;
      notifyEndObserver();
      gameTimer.stop();
    } else if (
      // check if the player has won i.e. has 10 gold
      getResourceManager().getResourceQuantity(ResourceType.GOLD) >= 10
    ) {
      System.out.println("Game Won!");
      win = true;
      notifyEndObserver();
      gameTimer.stop();
    }
  }
}
