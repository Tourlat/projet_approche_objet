package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public class Farm extends Building {

  public Farm() {
    super(
        "Farm",
        BuildingType.FARM,
        3,
        3,
        2,
        Map.of(ResourceType.WOOD, 5, ResourceType.STONE, 5),
        Map.of(),
        Map.of(ResourceType.FOOD, 10),
        3,
        5);
  }
}
