package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public class Quarry extends Building {

  public Quarry() {
    super(
        "Quarry",
        BuildingType.QUARRY,
        2,
        2,
        2,
        Map.of(ResourceType.WOOD, 50),
        Map.of(),
        Map.of(ResourceType.STONE, 4, ResourceType.IRON, 4, ResourceType.COAL, 4),
        30,
        2);
  }
}
