package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public class SteelMill extends Building {

  public SteelMill() {
    super(
        "Steel Mill",
        4,
        3,
        6,
        Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50),
        Map.of(ResourceType.IRON, 4, ResourceType.COAL, 2),
        Map.of(ResourceType.STEEL, 4),
        40,
        0);
  }
}
