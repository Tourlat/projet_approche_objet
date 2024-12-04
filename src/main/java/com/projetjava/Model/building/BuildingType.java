package com.projetjava.Model.building;

public enum BuildingType {
  WOODEN_CABIN,
  HOUSE,
  APARTMENT_BUILDING,
  FARM,
  QUARRY,
  LUMBER_MILL,
  CEMENT_PLANT,
  STEEL_MILL,
  TOOL_FACTORY;

  public static int getWidth(BuildingType type) {
    switch (type) {
      case WOODEN_CABIN:
        return 1;
      case HOUSE:
        return 2;
      case APARTMENT_BUILDING:
        return 3;
      case FARM:
        return 3;
      case QUARRY:
        return 2;
      case LUMBER_MILL:
        return 3;
      case CEMENT_PLANT:
        return 4;
      case STEEL_MILL:
        return 4;
      case TOOL_FACTORY:
        return 4;
      default:
        return 1;
    }
  }

  public static int getHeight(BuildingType type) {
    switch (type) {
      case WOODEN_CABIN:
        return 1;
      case HOUSE:
        return 2;
      case APARTMENT_BUILDING:
        return 2;
      case FARM:
        return 3;
      case QUARRY:
        return 2;
      case LUMBER_MILL:
        return 3;
      case CEMENT_PLANT:
        return 3;
      case STEEL_MILL:
        return 3;
      case TOOL_FACTORY:
        return 3;
      default:
        return 1;
    }
  }
}
