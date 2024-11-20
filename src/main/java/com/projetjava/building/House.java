package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.Map;

public class House extends Building {

  public House() {
    super(
      "House",
      2,
      2,
      4,
      Map.of(ResourceType.WOOD, 2, ResourceType.STONE, 2),
      Map.of(),
      Map.of(),
      0,
      4
    );
  }
}
