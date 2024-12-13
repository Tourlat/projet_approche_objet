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
import com.projetjava.Model.resources.ResourceManager;
import com.projetjava.Model.resources.ResourceType;
import java.util.ArrayList;
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

  public static GameManager getInstance() {
    if (instance == null) {
      instance = new GameManager();
    }
    return instance;
  }

  private GameManager() {
    this.resourceManager = ResourceManager.getInstance();
    this.workers = Workers.getInstance();
    this.mapManager = MapManager.getInstance();
    this.gameTimer = GameTimer.getInstance();
    gameTimer.addObserver(this);
  }

  @Override
  public void update() {
    System.out.println("Game update");

    Platform.runLater(() -> {
      updateResources();
      consumeFood();
      notifyObservers();
    });
  }

  public void initializeGame() {
    resourceManager.addResource(ResourceType.FOOD, 20);
    resourceManager.addResource(ResourceType.WOOD, 50);
    resourceManager.addResource(ResourceType.STONE, 30);
    gameTimer.start();
    notifyResourceObservers();
    adminMode();
  }

  /**
   * "ADMIN MODE" -> to remove
   */
  private void adminMode() {
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
   * Get the ratio of employed and total workers
   * @return the ratio of employed and total workers
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
   * Remove a building from the map
   * @param position the position of the building to remove
   * @return true if the building was removed, false otherwise
   */
  public boolean removeBuilding(Position position) {
    Building building = mapManager.getBuilding(position);
    boolean success = mapManager.removeBuilding(position);
    if (success) {
      // for(Map.Entry<ResourceType, Integer> entry :
      // building.getConsumption().entrySet()) {
      // resourceManager.subtractResource(entry.getKey(), entry.getValue());
      // };
      /**
       * enlever les habitants / workers etc ....................
       */
      workers.removeEmployed(building.getCurrentEmployees());
      workers.removeUnemployed(building.getCurrentPopulation());
      notifyResourceObservers();
    }
    return success;
  }

  /**
   * Add workers to a building
   * @param position the position of the building
   * @param numberOfWorkers the number of workers to add
   * @return true if the workers were added, false otherwise
   */
  public boolean addWorkersToBuilding(Position position, int numberOfWorkers) {
    Building building = mapManager.getBuilding(position);
    if (building != null && workers.getUnemployed() >= numberOfWorkers) {
      building.addWorkers(numberOfWorkers);
      workers.addEmployed(numberOfWorkers);
      notifyResourceObservers();
      return true;
    }
    return false;
  }

  /**
   * Remove workers from a building
   * @param position the position of the building
   * @param numberOfWorkers the number of workers to remove
   * @return true if the workers were removed, false otherwise
   */
  public boolean removeWorkersFromBuilding(
    Position position,
    int numberOfWorkers
  ) {
    Building building = mapManager.getBuilding(position);
    if (building != null && building.getCurrentEmployees() >= numberOfWorkers) {
      building.removeWorkers(numberOfWorkers);
      workers.removeEmployed(numberOfWorkers);
      notifyResourceObservers();
      return true;
    }
    return false;
  }

  /**
   * Add the production of a building to the resources
   *
   * @param building
   */
  public void addProduction(Building building) {
    Map<ResourceType, Integer> production = building.getCurrentProduction();
    for (Map.Entry<ResourceType, Integer> entry : production.entrySet()) {
      resourceManager.addResource(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Consume the resources needed for a building production
   *
   * @param building
   */
  public void consumeBuildingResources(Building building) {
    Map<ResourceType, Integer> consumption = building.getConsumption();
    for (Map.Entry<ResourceType, Integer> entry : consumption.entrySet()) {
      resourceManager.subtractResource(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Update the resources of the game
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
        consumeBuildingResources(building);
        addProduction(building);
      }
    }

    notifyResourceObservers();
  }

  /**
   * Consume the food needed for the workers/ population
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

  public void showResources() {
    resourceManager.showResources();
  }

  public ResourceManager getResourceManager() {
    return resourceManager;
  }

  public GameTimer getGameTimer() {
    return gameTimer;
  }

  public int getAvailableWorkers() {
    return workers.getUnemployed();
  }

  /**-- Observers methods */

  // default observer pattern
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }

  // resource observer pattern
  public void addResourceObserver(ResourceObserver observer) {
    resourceObservers.add(observer);
  }

  public void removeResourceObserver(ResourceObserver observer) {
    resourceObservers.remove(observer);
  }

  public void notifyResourceObservers() {
    for (ResourceObserver observer : resourceObservers) {
      observer.update();
    }
  }

  // end observer pattern
  public void setEndObserver(EndObserver observer) {
    endObserver = observer;
  }

  public void notifyEndObserver() {
    if (endObserver != null) {
      endObserver.updateEnd(win);
    }
  }

  public void removeEndObserver() {
    endObserver = null;
  }



  /**-- End  of Observers methods */


  /**
   * Function to set the game in win condition
   */
  public void Win() {
    System.out.println("Win called");
    resourceManager.addResource(ResourceType.GOLD, 10);
    notifyResourceObservers();
    System.out.println("Gold added");
  }

  /**
   * Function to set the game in lose condition
   */
  public void Lose() {
    resourceManager.setResourceQuantity(ResourceType.WOOD, 0);
    workers.foodConsumption(100000);
  }

  /**
   * Check if the game has ended
   */
  public void hasGameEnded() {
    // check if the player has lost i.e. no more wood and no more workers
    if (
      (getResourceManager().getResourceQuantity(ResourceType.WOOD) <= 0) &&
      (getQuantityOfWorkers()[1] <= 0)
    ) {
      System.out.println("Game Over");
      win = false;
      notifyEndObserver();
      gameTimer.stop();
      
    } else if (
      // check if the player has won i.e. has 10 gold
      getResourceManager().getResourceQuantity(ResourceType.GOLD) >= 10
    ) {
      System.out.println("Game Won");
      win = true;
      notifyEndObserver();
      gameTimer.stop();
     
    }
  }

  public boolean isWin() {
    return win;
  }
}
