package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public class LumberMill extends Building {

  public LumberMill() {
    super(
        "Lumber Mill",
        BuildingType.LUMBER_MILL,
        3,
        3,
        4,
        Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50),
        Map.of(ResourceType.WOOD, 4),
        Map.of(ResourceType.LUMBER, 4),
        10,
        0);
  }
}
