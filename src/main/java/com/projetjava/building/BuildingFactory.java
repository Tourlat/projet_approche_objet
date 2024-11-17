package com.projetjava.building;

import com.projetjava.resources.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class BuildingFactory {
    public static Building createBuilding(BuildingType type) {
        BuildingBuilder builder = new BuildingBuilder();
        switch (type) {
            case WOODCABIN:
                return builder.setName("Wooden Cabin")
                              .setWidth(1)
                              .setHeight(1)
                              .setConstructionTime(2)
                              .setConstructionCost(createWoodenCabinCost())
                              .setConsumption(createWoodenCabinConsumption())
                              .setWorkersRequired(2)
                              .build();
            case HOUSE:
                return builder.setName("House")
                              .setWidth(2)
                              .setHeight(2)
                              .setConstructionTime(4)
                              .setConstructionCost(createHouseCost())
                              .setWorkersRequired(4)
                              .build();
            case APERTMENTBUILDING:
                return builder.setName("Apartment Building")
                              .setWidth(4)
                              .setHeight(4)
                              .setConstructionTime(8)
                              .setConstructionCost(createHouseCost())
                              .setWorkersRequired(8)
                              .build();
            default:
                throw new IllegalArgumentException("Unknown building type: " + type);
        }
    }

    private static Map<ResourceType, Integer> createWoodenCabinCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.WOOD, 1);
        return cost;
    }

    private static Map<ResourceType, Integer> createWoodenCabinConsumption() {
        Map<ResourceType, Integer> consumption = new HashMap<>();
        consumption.put(ResourceType.WOOD, 2);
        consumption.put(ResourceType.FOOD, 2);
        return consumption;
    }

    private static Map<ResourceType, Integer> createHouseCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.WOOD, 2);
        cost.put(ResourceType.STONE, 2);
        return cost;
    }

    private static Map<ResourceType, Integer> createHouseConsumption() {
        Map<ResourceType, Integer> consumption = new HashMap<>();
        consumption.put(ResourceType.WOOD, 4);
        consumption.put(ResourceType.FOOD, 4);
        return consumption;
    }
}