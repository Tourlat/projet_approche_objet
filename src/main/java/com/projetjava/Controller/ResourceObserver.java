package com.projetjava.Controller;

public interface ResourceObserver extends Observer {
    void updateResources(int food, int wood, int stone, int lumber, int coal, int iron, int steel, int tools);
}