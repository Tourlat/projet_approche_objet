package com.projetjava.Model.map;

import com.projetjava.Model.building.Building;
import java.util.HashMap;

public class MapManager {

  private static int width;
  private static int height;

  private static boolean[][] map;

  private static MapManager instance;

  private static HashMap<Position, Building> buildings;

  private static int MAP_WIDTH = 50;
  private static int MAP_HEIGHT = 30;

  private MapManager(int width, int height) {
    MapManager.width = width;
    MapManager.height = height;
    MapManager.map = new boolean[width][height];
    MapManager.buildings = new HashMap<>();
  }

  public static MapManager getInstance() {
    if (instance == null) {
      instance = new MapManager(MAP_WIDTH, MAP_HEIGHT);
    }
    return instance;
  }

  /**
   * Check if the given area is occupied
   *
   * @param tilesWidth  the width of the area in tiles
   * @param tilesHeight the height of the area in tiles
   * @return true if the area is occupied, false otherwise
   */
  public boolean isOccupied(
    int startX,
    int startY,
    int tilesWidth,
    int tilesHeight
  ) {
    for (int x = startX; x < startX + tilesWidth; x++) {
      for (int y = startY; y < startY + tilesHeight; y++) {
        if (x >= width || y >= height || map[x][y]) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isOccupied(int x, int y) {
    return map[x][y];
  }

  public void setOccupied(int x, int y, boolean occupied) {
    map[x][y] = occupied;
  }

  /**
   * Place a building on the map and add it to the list of buildings
   *
   * @param x        the x coordinate of the top left corner of the building
   * @param y        the y coordinate of the top left corner of the building
   * @param building the building to place
   * @return true if the building was placed, false otherwise
   */
  public boolean placeBuilding(Position pos, Building building) {
    int x = pos.getX();
    int y = pos.getY();
    if (!isOccupied(x, y, building.getWidth(), building.getHeight())) {
      for (int i = 0; i < building.getWidth(); i++) {
        for (int j = 0; j < building.getHeight(); j++) {
          map[x + i][y + j] = true;
        }
      }
      buildings.put(pos, building);
      return true;
    }
    return false;
  }

  public boolean canPlaceBuilding(int x, int y) {
    if (x > width || y > height || map[x][y]) {
      return false;
    }
    return true;
  }

  /**
   * Remove a building from the map
   *
   * @param x the x coordinate of the top left corner of the building
   * @param y the y coordinate of the top left corner of the building
   * @return true if the building was removed, false otherwise
   */
  public boolean removeBuilding(Position pos) {
    int x = pos.getX();
    int y = pos.getY();
    Building building = buildings.get(new Position(x, y));
    if (building == null) {
      return false;
    }
    for (int i = 0; i < building.getWidth(); i++) {
      for (int j = 0; j < building.getHeight(); j++) {
        map[x + i][y + j] = false;
      }
    }
    buildings.remove(new Position(x, y));
    return true;
  }

  public void showMap() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (map[x][y]) {
          System.out.print("X ");
        } else {
          System.out.print("O ");
        }
      }
      System.out.println();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Building getBuilding(Position pos) {
    return buildings.get(pos);
  }

  public HashMap<Position, Building> getBuildings() {
    return buildings;
  }
}
