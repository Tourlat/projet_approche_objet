package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.Map;

public class SteelMill extends Building {
    public SteelMill() {
        super("Steel Mill", 4, 3, 0, 40, 
              Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50), 
              Map.of(ResourceType.IRON, 4, ResourceType.COAL, 2), 
              Map.of(ResourceType.STEEL, 4), 
              6);
    }
}