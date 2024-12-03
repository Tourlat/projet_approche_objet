package com.projetjava.Model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.projetjava.Model.building.Building;
import com.projetjava.Model.building.BuildingFactory;
import com.projetjava.Model.building.BuildingType;
import com.projetjava.Model.map.MapManager;
import com.projetjava.Model.map.Position;
import com.projetjava.Model.population.Workers;
import com.projetjava.Model.resources.ResourceManager;
import com.projetjava.Model.resources.ResourceType;
import com.projetjava.Controller.Observer;

public class GameManager implements Observer {
    private ResourceManager resourceManager;
    private Workers workers;
    private MapManager mapManager;
    private GameTimer gameTimer;

    private static GameManager instance;

    private List<Observer> observers = new ArrayList<>();

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager() {

        this.resourceManager = ResourceManager.getInstance();
        this.workers = Workers.getInstance();
        this.mapManager = MapManager.getInstance(10, 10);
        this.gameTimer = GameTimer.getInstance();
        gameTimer.addObserver(this);

    }

    @Override
    public void update() {
        System.out.println("Game update");
        updateResources();
        consumeFood();
    }

    public void initializeGame(){
        resourceManager.addResource(ResourceType.FOOD, 100);
        resourceManager.addResource(ResourceType.WOOD, 50);
        resourceManager.addResource(ResourceType.STONE, 30);
        gameTimer.start();
        notifyObservers();
    
    }

    public boolean addBuilding(Position position, BuildingType building) {
        Building newBuilding = BuildingFactory.createBuilding(building);

        // check if the player has enough resources to build the building
        for (Map.Entry<ResourceType, Integer> entry : newBuilding.getConstructionCost().entrySet()) {
            if (resourceManager.getResourceQuantity(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }

        boolean success = mapManager.placeBuilding(position, newBuilding);
        if (success) {
            for (Map.Entry<ResourceType, Integer> entry : newBuilding.getConstructionCost().entrySet()) {
                resourceManager.subtractResource(entry.getKey(), entry.getValue());
            }
            ;
            
            workers.addUnemployed(newBuilding.getPopulationCreated());
        }
        notifyObservers();
        return success;
    }

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
        }
        return success;
    }

    public boolean addWorkersToBuilding(Position position, int numberOfWorkers) {
        Building building = mapManager.getBuilding(position);
        if (building != null && workers.getUnemployed() >= numberOfWorkers) {
            building.addWorkers(numberOfWorkers);
            workers.addEmployed(numberOfWorkers);
            return true;
        }
        return false;
    }

    public boolean removeWorkersFromBuilding(Position position, int numberOfWorkers) {
        Building building = mapManager.getBuilding(position);
        if (building != null && building.getCurrentEmployees() >= numberOfWorkers) {
            building.removeWorkers(numberOfWorkers);
            workers.removeEmployed(numberOfWorkers);
            return true;
        }
        return false;
    }

    public void updateResources() {
        for (Map.Entry<Position, Building> entry : mapManager.getBuildings().entrySet()) {
            Building building = entry.getValue();
            for (Map.Entry<ResourceType, Integer> entry2 : building.getCurrentProduction().entrySet()) {
                resourceManager.addResource(entry2.getKey(), entry2.getValue());
            }
        }
        notifyObservers();
    }

    public void consumeFood() {
        int foodAvailable = resourceManager.getResourceQuantity(ResourceType.FOOD);
        workers.foodConsumption(foodAvailable);
        System.out.println("Food consumption: " + workers.getFoodConsumption());
        resourceManager.setResourceQuantity(ResourceType.FOOD,
                Math.max(0, foodAvailable - workers.getFoodConsumption()));
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


    // observer pattern
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

}
