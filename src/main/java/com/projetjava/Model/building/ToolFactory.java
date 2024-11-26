package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public class ToolFactory extends Building {

  public ToolFactory() {
    super(
        "Tool Factory",
        4,
        3,
        8,
        Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50),
        Map.of(ResourceType.STEEL, 4, ResourceType.COAL, 4),
        Map.of(ResourceType.TOOL, 4),
        12,
        0);
  }
}
