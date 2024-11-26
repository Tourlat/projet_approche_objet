package com.projetjava.Model.population;

public class PopulationFactory {

  public static Population createPopulation(PopulationType type) {
    switch (type) {
      case Workers:
        return Workers.getInstance();
      // case Artisans:
      // ....
      default:
        throw new UnknownPopulationTypeException(
            "Unknown population type: " + type);
    }
  }
}
