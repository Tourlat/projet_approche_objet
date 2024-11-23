package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.HashMap;
import java.util.Map;

public abstract class Building {

  private String name;
  private int width;
  private int height;
  private int constructionTime;
  private Map<ResourceType, Integer> constructionCost;
  private Map<ResourceType, Integer> consumption;
  private Map<ResourceType, Integer> production;
  private int employeesNeeded;
  private int populationCreated;
  private int employed;
  private static Integer uid = 0;

  public Building(
    String name,
    int width,
    int height,
    int constructionTime,
    Map<ResourceType, Integer> constructionCost,
    Map<ResourceType, Integer> consumption,
    Map<ResourceType, Integer> production,
    int employeesNeeded,
    int populationCreated
  ) {
    uid++;
    this.name = name + "_" + uid.toString();
    this.width = width;
    this.height = height;
    this.constructionTime = constructionTime;
    this.constructionCost = constructionCost;
    this.consumption = consumption;
    this.production = production;
    this.employeesNeeded = employeesNeeded;
    this.populationCreated = populationCreated;
    this.employed = 0;
  }

  /**
   * Get the name of the building
   * @return the name of the building
   */
  public String getName() {
    return name;
  }

  /**
   * Get the width of the building
   * @return the width of the building
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of the building
   * @return the height of the building
   */
  public int getHeight() {
    return height;
  }

  /**
   * Get the construction time of the building
   * @return the construction time of the building
   */
  public int getConstructionTime() {
    return constructionTime;
  }

  /**
   * Get the construction cost of the building
   * @return the construction cost of the building
   */
  public Map<ResourceType, Integer> getConstructionCost() {
    return constructionCost;
  }

  /**
   * Get the consumption of the building
   * @return the consumption of the building
   */
  public Map<ResourceType, Integer> getConsumption() {
    return consumption;
  }

  /**
   * Get the max production of the building
   * @return the max production of the building
   */
  public Map<ResourceType, Integer> getMaxProduction() {
    return production;
  }

  /**
   * Get the current production of the building
   * @return the current production of the building
   */
  public Map<ResourceType, Integer> getCurrentProduction() {
    Map<ResourceType, Integer> currentProduction = new HashMap<>();
    double proportion = (double) employed / employeesNeeded;

    for (Map.Entry<ResourceType, Integer> entry : production.entrySet()) {
      int adjustedAmount = (int) (entry.getValue() * proportion);
      currentProduction.put(entry.getKey(), adjustedAmount);
    }

    return currentProduction;
  }

  /**
   * Get the employees needed for the building
   * @return the employees needed for the building
   */
  public int getEmployeesNeeded() {
    return employeesNeeded;
  }

  /**
   * Get the population created by the building
   * @return the population created by the building
   */
  public int getPopulationCreated() {
    return populationCreated;
  }

  /**
   * Get the number of employed people in the building
   * @return the number of employed people in the building
   */
  public int getEmployed() {
    return employed;
  }

  /**
   * Add workers to the building
   * @param workers the number of workers to add
   */
  public void addWorkers(int workers) {
    if (workers < 0) {
      throw new IllegalArgumentException("Workers can't be negative");
    } else if (workers > employeesNeeded - employed) {
      throw new IllegalArgumentException("Not enough space for workers");
    } else {
      this.employed += workers;
    }
  }

  /**
   * Remove workers from the building
   * @param workers the number of workers to remove
   */
  public void removeWorkers(int workers) {
    if (workers < 0) {
      throw new IllegalArgumentException("Workers can't be negative");
    } else if (workers > employed) {
      throw new IllegalArgumentException("Not enough workers to remove");
    } else {
      this.employed -= workers;
    }
  }
}
