package com.projetjava.building;

import java.util.Map;
import com.projetjava.resources.ResourceType;

public class WoodenCabin extends Building {

    public WoodenCabin() {
        super("Wooden Cabin", 1, 1, 2, 2, Map.of(ResourceType.WOOD, 1), Map.of(), Map.of(ResourceType.WOOD, 2, ResourceType.FOOD, 2), 2);
    }

}
