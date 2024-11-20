package com.projetjava.population;

public class Workers implements Population {

  private static Workers instance;
  private int employedWorkers;
  private int unemployedWorkers;

  // Constructeur privé pour empêcher la création d'instances depuis l'extérieur
  private Workers() {
    employedWorkers = 0;
    unemployedWorkers = 0;
  }

  // Méthode publique et statique pour obtenir l'instance unique
  public static Workers getInstance() {
    if (instance == null) {
      instance = new Workers();
    }
    return instance;
  }

  /**
   * Get the total number of workers
   * @return Total number of workers
   */
  @Override
  public int getTotal() {
    return getUnemployed() + getEmployed();
  }

  /**
   * Get the number of unemployed workers
   * @return Number of unemployed workers
   */
  @Override
  public int getUnemployed() {
    return unemployedWorkers;
  }

  /**
   * Get the number of employed workers
   * @return Number of employed workers
   */
  @Override
  public int getEmployed() {
    return employedWorkers;
  }

  /**
   * Add employed workers
   * @param employed Number of workers to add
   */
  @Override
  public void addEmployed(int employed) {
    if (employed < 0) {
      throw new IllegalArgumentException("Employed workers can't be negative");
    } else if (employed > unemployedWorkers) {
      throw new IllegalArgumentException("Not enough unemployed workers");
    } else {
      this.employedWorkers += employed;
      this.unemployedWorkers -= employed;
    }
  }

  /**
   * Add workers to the population
   * @param pop Number of workers to add
   */
  @Override
  public void addPop(int pop) {
    this.unemployedWorkers += pop;
  }

  /**
   * Remove workers depending on the food available
   * @param food Food available
   */
  @Override
  public void foodComsumption(int food) {
    if (food < getFoodConsumption()) {
      int foodComsumptionForEmployed = getEmployed() * 2;
      if (food > foodComsumptionForEmployed) { // Remove unemployed workers first
        unemployedWorkers -= food - foodComsumptionForEmployed;
      } else {
        unemployedWorkers = 0;
        employedWorkers -= Math.abs((food - foodComsumptionForEmployed) / 2); // If it's odd, let a worker live
      }
    }
  }
}
