package com.projetjava.resources;

import java.util.HashMap;

import com.projetjava.resources.ResourceType;

public class RessourceManager {
    private static RessourceManager instance;
    private HashMap<ResourceType, Resource> resources;

    private RessourceManager(){
        resources = new HashMap<>();
        initializeResources();
    }

    public static RessourceManager getInstance(){
        if(instance == null){
            instance = new RessourceManager();
        }
        return instance;
    }

    /**
     * Initialize all resources to 0
     * We can change this to initialize with a default value
     */
    private void initializeResources(){
        for(ResourceType type : ResourceType.values()){
            resources.put(type, new Resource(type, 0));
        }
    }

    /**
     * Add a quantity of a resource
     * @param type 
     * @param quantity
     * @return nothing if the resource is found else throws an exception
     */
    public void addResource(ResourceType type, int quantity){
        Resource resource = resources.get(type);

        if(resource != null){
            resource.addQuantity(quantity);
        }else{
            throw new IllegalArgumentException("Resource type not found");
        }
    }

    /**
     * Subtracts a quantity of a resource
     * @param type
     * @param quantity
     * @return nothing if the resource is found else throws an exception
     */
    public void subtractResource(ResourceType type, int quantity){
        Resource resource = resources.get(type);
        if(resource != null){
            resource.subtractQuantity(quantity);
        }else{
            throw new IllegalArgumentException("Resource type not found");
        }
    }
    
}