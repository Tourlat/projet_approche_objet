package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public class House extends Building {

  public House() {
    super(
        "House",
        BuildingType.HOUSE,
        2,
        2,
        4,
        Map.of(ResourceType.WOOD, 2, ResourceType.STONE, 2),
        Map.of(),
        Map.of(),
        0,
        4);
  }
}
