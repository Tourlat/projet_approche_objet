package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.Map;

public class LumberMill extends Building {

  public LumberMill() {
    super(
      "Lumber Mill",
      3,
      3,
      4,
      Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50),
      Map.of(ResourceType.WOOD, 4),
      Map.of(ResourceType.LUMBER, 4),
      10,
      0
    );
  }
}
