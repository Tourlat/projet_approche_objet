package com.projetjava.map;

import java.util.HashMap;


import com.projetjava.building.Building;

public class map {

    private int width;
    private int height;

    private boolean[][] map;

    private static map instance;

    private HashMap<Position, Building> buildings;


    private map(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new boolean[width][height];
    }

    public map getInstance(int width, int height) {
        if (instance == null) {
            instance = new map(width, height);
        }
        return instance;
    }

    /**
     * Check if the given area is occupied
     * @param tilesWidth the width of the area in tiles
     * @param tilesHeight the height of the area in tiles
     * @return true if the area is occupied, false otherwise
     */
    public boolean isOccupied(int tilesWidth, int tilesHeight) {
        for(int x = 0; x < tilesWidth; x++) {
            for(int y = 0; y < tilesHeight; y++) {
                if(map[x][y]) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Place a building on the map and add it to the list of buildings
     * @param x the x coordinate of the top left corner of the building
     * @param y the y coordinate of the top left corner of the building
     * @param building the building to place
     * @return true if the building was placed, false otherwise
     */
    public boolean placeBuilding(int x, int y, Building building){
        if(x + building.getWidth() > width || y + building.getHeight() > height) {
            return false;
        }
        if(isOccupied(building.getWidth(), building.getHeight())) {
            return false;
        }
        for(int i = 0; i < building.getWidth(); i++) {
            for(int j = 0; j < building.getHeight(); j++) {
                map[x + i][y + j] = true;
            }
        }
        buildings.put(new Position(x, y), building);
        return true;
    }

    /**
     * Remove a building from the map
     * @param x the x coordinate of the top left corner of the building
     * @param y the y coordinate of the top left corner of the building
     * @return true if the building was removed, false otherwise
     */
    public boolean removeBuilding(int x, int y) {
        Building building = buildings.get(new Position(x, y));
        if(building == null) {
            return false;
        }
        for(int i = 0; i < building.getWidth(); i++) {
            for(int j = 0; j < building.getHeight(); j++) {
                map[x + i][y + j] = false;
            }
        }
        buildings.remove(new Position(x, y));
        return true;
    }
}
