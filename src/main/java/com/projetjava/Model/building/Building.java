package com.projetjava.Model.building;

import java.util.HashMap;
import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public abstract class Building {

  private String name;
  private int width;
  private int height;
  private int constructionTime;
  private Map<ResourceType, Integer> constructionCost;
  private Map<ResourceType, Integer> consumption;
  private Map<ResourceType, Integer> production;
  private int populationCreated;
  private int currentPopulation;
  private int maxEmployees;
  private int currentEmployees;
  private static Integer uid = 0;
  private boolean constructed = false;

  private BuildingType type;

  public Building(
      String name,
      BuildingType type,
      int width,
      int height,
      int constructionTime,
      Map<ResourceType, Integer> constructionCost,
      Map<ResourceType, Integer> consumption,
      Map<ResourceType, Integer> production,
      int maxEmployees,
      int populationCreated) {
      
    uid++;
    this.name = name + "_" + uid.toString();
    this.type = type;
    this.width = width;
    this.height = height;
    this.constructionTime = constructionTime;
    this.constructionCost = constructionCost;
    this.consumption = consumption;
    this.production = production;
    this.populationCreated = populationCreated;
    this.currentPopulation = populationCreated;
    this.maxEmployees = maxEmployees;
    this.currentEmployees = 0;
    this.constructed = false;
  }

  /**
   * Get the name of the building
   * 
   * @return the name of the building
   */
  public String getName() {
    return name;
  }

  /**
   * Get the width of the building
   * 
   * @return the width of the building
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of the building
   * 
   * @return the height of the building
   */
  public int getHeight() {
    return height;
  }

  /**
   * Get the construction time of the building
   * 
   * @return the construction time of the building
   */
  public int getConstructionTime() {
    return constructionTime;
  }

  /**
   * Get the construction cost of the building
   * 
   * @return the construction cost of the building
   */
  public Map<ResourceType, Integer> getConstructionCost() {
    return constructionCost;
  }

  /**
   * Get the consumption of the building
   * 
   * @return the consumption of the building
   */
  public Map<ResourceType, Integer> getConsumption() {
    return consumption;
  }

  /**
   * Get the max production of the building
   * 
   * @return the max production of the building
   */
  public Map<ResourceType, Integer> getMaxProduction() {
    return production;
  }

  /**
   * Get the current production of the building
   * 
   * @return the current production of the building
   */
  public Map<ResourceType, Integer> getCurrentProduction() {
    System.out.println("currentEmployees: " + currentEmployees);
    Map<ResourceType, Integer> currentProduction = new HashMap<>();
    double proportion = (double) currentEmployees / maxEmployees;

    for (Map.Entry<ResourceType, Integer> entry : production.entrySet()) {
      int adjustedAmount = (int) (entry.getValue() * proportion);
      currentProduction.put(entry.getKey(), adjustedAmount);
    }

    return currentProduction;
  }

  /**
   * Get the population created by the building at the building time
   * 
   * @return the population created by the building at the building time
   */
  public int getPopulationCreated() {
    return populationCreated;
  }

  /**
   * Get the current population created by the building
   * 
   * @return the current population created by the building
   */
  public int getCurrentPopulation() {
    return currentPopulation;
  }

  /**
   * Get the employees needed for the building
   * 
   * @return the employees needed for the building
   */
  public int getMaxEmployees() {
    return maxEmployees;
  }

  /**
   * Get the number of currentEmployees people in the building
   * 
   * @return the number of currentEmployees people in the building
   */
  public int getCurrentEmployees() {
    return currentEmployees;
  }

  /**
   * Add workers to the building
   * 
   * @param workers the number of workers to add
   */
  public void addWorkers(int workers) {
    if (workers < 0) {
      throw new IllegalArgumentException("Workers can't be negative");
    } else if (workers > maxEmployees - currentEmployees) {
      throw new IllegalArgumentException("Not enough space for workers");
    } else {
      this.currentEmployees += workers;
    }
  }

  /**
   * Remove workers from the building
   * 
   * @param workers the number of workers to remove
   */
  public void removeWorkers(int workers) {
    if (workers < 0) {
      throw new IllegalArgumentException("Workers can't be negative");
    } else if (workers > currentEmployees) {
      throw new IllegalArgumentException("Not enough workers to remove");
    } else {
      this.currentEmployees -= workers;
    }
  }

  /*
   * Return building type
   */
  public BuildingType getType(){
    return this.type;
  }

  /**
   * Check if the building is constructed
   * 
   * @return true if the building is constructed, false otherwise
   */
  public boolean isConstructed() {
    return constructed;
  }

  public boolean setConstructTrue() {
    return constructed = true;
  }
}
