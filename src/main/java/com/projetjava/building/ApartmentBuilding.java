package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.Map;

public class ApartmentBuilding extends Building {

  public ApartmentBuilding() {
    super(
      "Apartment Building",
      3,
      2,
      6,
      Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50),
      Map.of(),
      Map.of(),
      0,
      60
    );
  }
}
