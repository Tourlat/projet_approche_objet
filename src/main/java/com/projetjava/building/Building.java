package com.projetjava.building;
import com.projetjava.resources.ResourceType;
import java.util.Map;

public abstract class Building {
    private String name;
    private int width;
    private int height;
    private int constructionTime;
    private Map<ResourceType, Integer> constructionCost;
    private Map<ResourceType, Integer> production;
    private Map<ResourceType, Integer> consumption;
    private int nbWorkers;
    private int nbInhabitants;


    public Building(String name, int width, int height, int nbInhabitants, int nbWorkers, Map<ResourceType, Integer> constructionCost, Map<ResourceType, Integer> consumption, Map<ResourceType, Integer> production, int constructionTime) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.constructionTime = constructionTime;
        this.constructionCost = constructionCost;
        this.production = production;
        this.consumption = consumption;
        this.nbWorkers = nbWorkers;
        this.nbInhabitants = nbInhabitants;
    
    }

    public String getName() { return name; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getConstructionTime() { return constructionTime; }
    public Map<ResourceType, Integer> getConstructionCost() { return constructionCost; }
    public Map<ResourceType, Integer> getProduction() { return production; }
    public Map<ResourceType, Integer> getConsumption() { return consumption; }
    public int getNbWorkers() { return nbWorkers; }
    public int getNbInhabitants() { return nbInhabitants; }
}
