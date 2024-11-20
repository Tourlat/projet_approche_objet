package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.Map;

public class Farm extends Building {

  public Farm() {
    super(
      "Farm",
      3,
      3,
      2,
      Map.of(ResourceType.WOOD, 5, ResourceType.STONE, 5),
      Map.of(),
      Map.of(ResourceType.FOOD, 10),
      3,
      5
    );
  }
}
