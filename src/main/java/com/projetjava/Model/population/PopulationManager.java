package com.projetjava.Model.population;

import java.util.ArrayList;
import java.util.List;

public class PopulationManager {

  private static PopulationManager instance;
  private List<Population> population;

  private PopulationManager() {
    initializePop();
  }

  public static PopulationManager getInstance() {
    if (instance == null) {
      instance = new PopulationManager();
    }
    return instance;
  }

  /**
   * Initialize all populations to 0
   */
  private void initializePop() {
    population = new ArrayList<>();
    for (PopulationType type : PopulationType.values()) {
      population.add(PopulationFactory.createPopulation(type));
    }
  }

  /**
   * Get the list of populations
   * 
   * @return List of populations
   */
  public List<Population> getPopulation() {
    return population;
  }

  /**
   * Get the total population
   * 
   * @return Total population
   */
  public int getTotalPopulation() {
    int total = 0;
    for (Population pop : population) {
      total += pop.getTotal();
    }
    return total;
  }
}
