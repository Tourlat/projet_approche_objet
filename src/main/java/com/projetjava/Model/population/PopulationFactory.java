package com.projetjava.Model.population;

import com.projetjava.customexceptions.UnknownPopulationTypeException;

public class PopulationFactory {

  /**
   * Create a population of a given type
   * For now, only Workers are implemented, but in the future, there may be more types of populations
   * @param type - the type of the population
   * @return the population
   */
  public static Population createPopulation(PopulationType type) {
    switch (type) {
      case Workers:
        return Workers.getInstance();
      // case Artisans:
      // ....
      default:
        throw new UnknownPopulationTypeException(
          "Unknown population type: " + type
        );
    }
  }
}
