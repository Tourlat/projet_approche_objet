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
   * 
   * @return Total number of workers
   */
  @Override
  public int getTotal() {
    return getUnemployed() + getEmployed();
  }

  /**
   * Get the number of unemployed workers
   * 
   * @return Number of unemployed workers
   */
  @Override
  public int getUnemployed() {
    return unemployedWorkers;
  }

  /**
   * Get the number of employed workers
   * 
   * @return Number of employed workers
   */
  @Override
  public int getEmployed() {
    return employedWorkers;
  }

  /**
   * Add employed workers
   * 
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
   * Add unemployed workers
   * 
   * @param unemployed Number of workers to add
   */
  public void addUnemployed(int unemployed) {
    if (unemployed < 0) {
      throw new IllegalArgumentException("Unemployed workers can't be negative");
    } else {
      this.unemployedWorkers += unemployed;
    }
  }

  /**
   * Remove employed workers
   * 
   * @param employed Number of workers to remove
   */
  @Override
  public void removeEmployed(int employed) {
    if (employed < 0) {
      throw new IllegalArgumentException("Cannot substract by a negative number");
    } else if (employed > this.employedWorkers) {
      throw new IllegalArgumentException("Not enough employed workers");
    } else {
      this.employedWorkers -= employed;
      this.unemployedWorkers += employed;
    }
  }

  /**
   * Add workers to the population
   * 
   * @param pop Number of workers to add
   */
  @Override
  public void addPop(int pop) {
    this.unemployedWorkers += pop;
  }

  // /**
  // * Remove workers depending on the food available
  // * @param food Food available
  // */
  // @Override
  // public void foodComsumption(int food) {
  // if (food < getFoodConsumption()) {
  // int foodComsumptionForEmployed = getEmployed() * 2;
  // if (food > foodComsumptionForEmployed) { // Remove unemployed workers first
  // unemployedWorkers -= food - foodComsumptionForEmployed;
  // } else {
  // unemployedWorkers = 0;
  // employedWorkers -= Math.abs((food - foodComsumptionForEmployed) / 2); // If
  // it's odd, let a worker live
  // }
  // }
  // }

  /**
   * Remove workers depending on the food available
   * 
   */
  public void foodConsumption(int foodAvailable) {
    int foodConsumption = getFoodConsumption();
    int foodNeeded = foodConsumption - foodAvailable;
    // if there is not enough food
    if (foodNeeded > 0) {
      //remove unemployed workers first
      if (unemployedWorkers >= foodNeeded) {
        unemployedWorkers -= foodNeeded;
      } else {
        foodNeeded -= unemployedWorkers;
        unemployedWorkers = 0;
        //remove employed workers
        employedWorkers -= foodNeeded;
      }
      if (employedWorkers < 0) {
        employedWorkers = 0;
      }
    }
  }
}
