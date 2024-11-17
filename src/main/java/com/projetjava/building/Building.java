package com.projetjava.building;
import com.projetjava.resources.ResourceType;
import java.util.Map;

public class Building {
    private String name;
    private int cost;
    private int width;
    private int height;
    private int constructionTime;
    private Map<ResourceType, Integer> constructionCost;
    private Map<ResourceType, Integer> production;
    private Map<ResourceType, Integer> consumption;
    private int nbWorkers;
    private int nbInhabitants;


    public Building(BuildingBuilder builder) {
        this.name = builder.name;
        this.cost = builder.cost;
        this.width = builder.width;
        this.height = builder.height;
        this.constructionTime = builder.constructionTime;
        this.constructionCost = builder.constructionCost;
        this.production = builder.production;
        this.consumption = builder.consumption;
        this.nbWorkers = builder.nbWorkers;
        this.nbInhabitants = builder.nbInhabitants;
    }

    public String getName() { return name; }
    public int getCost() { return cost; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getConstructionTime() { return constructionTime; }
    public Map<ResourceType, Integer> getConstructionCost() { return constructionCost; }
    public Map<ResourceType, Integer> getProduction() { return production; }
    public Map<ResourceType, Integer> getConsumption() { return consumption; }
    public int getNbWorkers() { return nbWorkers; }
    public int getNbInhabitants() { return nbInhabitants; }
}
