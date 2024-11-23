package com.projetjava.population;

public interface Population {
  public int getTotal();

  public int getUnemployed();

  public int getEmployed();

  public void addEmployed(int employed);

  public void addPop(int pop);

  public void foodConsumption(int food);

  public void removeEmployed(int employed);

  public void addUnemployed(int unemployed);

  /**
   * Default method to get the food consumption of the population
   * @return Food consumption
   */
  default int getFoodConsumption() {
    return getUnemployed() + getEmployed() * 2;
  }
}