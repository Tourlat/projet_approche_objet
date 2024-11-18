package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.Map;

public class Quarry extends Building {
    public Quarry() {
        super("Quarry", 2, 2, 2, 30, 
              Map.of(ResourceType.WOOD, 50), 
              Map.of(), 
              Map.of(ResourceType.STONE, 4, ResourceType.IRON, 4, ResourceType.COAL, 4), 
              2);
    }
}