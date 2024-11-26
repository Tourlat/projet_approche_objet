package com.projetjava.Model.building;

public class BuildingFactory {
    public static Building createBuilding(BuildingType type) {
        switch (type) {
            case WOODEN_CABIN:
                return new WoodenCabin();
            case HOUSE:
                return new House();
            case APARTMENT_BUILDING:
                return new ApartmentBuilding();
            case FARM:
                return new Farm();
            case QUARRY:
                return new Quarry();
            case LUMBER_MILL:
                return new LumberMill();
            case CEMENT_PLANT:
                return new CementPlant();
            case STEEL_MILL:
                return new SteelMill();
            case TOOL_FACTORY:
                return new ToolFactory();
            default:
                throw new UnknownBuildingTypeException("Unknown building type: " + type);
        }
    }
}