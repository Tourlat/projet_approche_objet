package com.projetjava.game;


import com.projetjava.resources.ResourceManager;

import java.util.Map;

import com.projetjava.map.MapManager;
import com.projetjava.map.Position;
import com.projetjava.population.Workers;
import com.projetjava.building.Building;
import com.projetjava.building.BuildingType;
import com.projetjava.building.BuildingFactory;
import com.projetjava.resources.ResourceType;

public class GameManager {
    private ResourceManager resourceManager;
    private Workers workers;
    private MapManager mapManager;

    public GameManager() {
        this.resourceManager = ResourceManager.getInstance();
        this.workers = Workers.getInstance();
        this.mapManager = MapManager.getInstance(10, 10);
    }

    public boolean addBuilding(Position position, BuildingType building) {
        Building newBuilding = BuildingFactory.createBuilding(building);
        boolean success = mapManager.placeBuilding(position, newBuilding);
        return success;
    }

    public boolean removeBuilding(Position position) {
        Building building = mapManager.getBuilding(position);
        boolean success = mapManager.removeBuilding(position);
        if (success) {
            for(Map.Entry<ResourceType, Integer> entry : building.getConstructionCost().entrySet()) {
                resourceManager.subtractResource(entry.getKey(), entry.getValue());
            };
        }
        return success;
    }

    public boolean addWorkersToBuilding(Position position, int numberOfWorkers) {
        Building building = mapManager.getBuilding(position);
        if(building != null && workers.getUnemployed() >= numberOfWorkers) {
            building.addWorkers(numberOfWorkers);
            workers.addEmployed(numberOfWorkers);
            return true;
        }
        return false;
    }

    public boolean removeWorkersFromBuilding(Position position, int numberOfWorkers) {
        Building building = mapManager.getBuilding(position);
        if(building != null && building.getEmployed() >= numberOfWorkers) {
            building.removeWorkers(numberOfWorkers);
            workers.removeEmployed(numberOfWorkers);
            return true;
        }
        return false;
    }

    public void updateResources() {
        for(Map.Entry<Position, Building> entry : mapManager.getBuildings().entrySet()) {
            Building building = entry.getValue();
            for(Map.Entry<ResourceType, Integer> entry2 : building.getCurrentProduction().entrySet()) {
                resourceManager.addResource(entry2.getKey(), entry2.getValue());
            }
        }
    }



    public void consumeFood() {
        int foodAvailable = resourceManager.getResourceQuantity(ResourceType.FOOD);
        workers.foodConsumption(foodAvailable);
        workers.foodConsumption(foodAvailable);
        resourceManager.addResource(ResourceType.FOOD, Math.max(0, foodAvailable - workers.getFoodConsumption()));
    }
}



