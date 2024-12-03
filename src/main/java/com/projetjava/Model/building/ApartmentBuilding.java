package com.projetjava.Model.building;

import java.util.Map;

import com.projetjava.Model.resources.ResourceType;

public class ApartmentBuilding extends Building {

  public ApartmentBuilding() {
    super(
        "Apartment Building",
        BuildingType.APARTMENT_BUILDING,
        3,
        2,
        6,
        Map.of(ResourceType.WOOD, 50, ResourceType.STONE, 50),
        Map.of(),
        Map.of(),
        0,
        60);
  }
}
