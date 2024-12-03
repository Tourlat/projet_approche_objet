package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;


public class CementPlant extends Building {

  public CementPlant() {
    super(
        "Cement Plant",
        BuildingType.CEMENT_PLANT,
        4,
        3,
        4,
        Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50),
        Map.of(ResourceType.STONE, 4, ResourceType.COAL, 4),
        Map.of(ResourceType.CEMENT, 4),
        10,
        0);
  }
}
