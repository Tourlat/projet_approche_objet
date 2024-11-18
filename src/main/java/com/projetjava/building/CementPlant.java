package com.projetjava.building;

import com.projetjava.resources.ResourceType;
import java.util.Map;

public class CementPlant extends Building {
    public CementPlant() {
        super("Cement Plant", 4, 3, 0, 10, 
              Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50), 
              Map.of(ResourceType.STONE, 4, ResourceType.COAL, 4), 
              Map.of(ResourceType.CEMENT, 4), 
              4);
    }
}