package com.projetjava.building;

import com.projetjava.resources.ResourceType;

import java.util.Map;

public class BuildingBuilder {
    String name;
    int cost;
    int width;
    int height;
    int constructionTime;
    Map<ResourceType, Integer> constructionCost;
    Map<ResourceType, Integer> production;
    Map<ResourceType, Integer> consumption;
    int nbWorkers;
    int nbInhabitants;

    public BuildingBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BuildingBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public BuildingBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public BuildingBuilder setConstructionTime(int constructionTime) {
        this.constructionTime = constructionTime;
        return this;
    }

    public BuildingBuilder setConstructionCost(Map<ResourceType, Integer> constructionCost) {
        this.constructionCost = constructionCost;
        return this;
    }

    public BuildingBuilder setProduction(Map<ResourceType, Integer> production) {
        this.production = production;
        return this;
    }

    public BuildingBuilder setConsumption(Map<ResourceType, Integer> consumption) {
        this.consumption = consumption;
        return this;
    }

    public BuildingBuilder setWorkersRequired(int nbWorkers) {
        this.nbWorkers = nbWorkers;
        return this;
    }

    public Building build() {
        return new Building(this);
    }

}