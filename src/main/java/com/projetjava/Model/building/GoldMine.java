package com.projetjava.Model.building;

import com.projetjava.Model.resources.ResourceType;
import java.util.Map;

public class GoldMine extends Building {

  public GoldMine() {
    super(
      "Gold Mine",
      BuildingType.GOLD_MINE,
      5,
      4,
      10,
      Map.of(
        ResourceType.FOOD,
        100,
        ResourceType.WOOD,
        100,
        ResourceType.STONE,
        100,
        ResourceType.COAL,
        100,
        ResourceType.IRON,
        100,
        ResourceType.STEEL,
        100,
        ResourceType.CEMENT,
        100,
        ResourceType.LUMBER,
        100,
        ResourceType.TOOL,
        100
      ),
      Map.of(
        ResourceType.FOOD,
        10,
        ResourceType.WOOD,
        10,
        ResourceType.COAL,
        10,
        ResourceType.STEEL,
        10
      ),
      Map.of(ResourceType.GOLD, 1),
      50,
      0
    );
  }
}
