package com.projetjava.Model.resources;

import java.util.HashMap;

public class ResourceManager {
    private static ResourceManager instance;
    private HashMap<ResourceType, Resource> resources;

    private ResourceManager() {
        resources = new HashMap<>();
        initializeResources();
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    /**
     * Initialize all resources to 0
     * We can change this to initialize with a default value
     */
    private void initializeResources() {
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, new Resource(type, 0));
        }
    }

    /**
     * Add a quantity of a resource
     * 
     * @param type
     * @param quantity
     * @return nothing if the resource is found else throws an exception
     */
    public void addResource(ResourceType type, int quantity) {
        Resource resource = resources.get(type);

        if (resource != null) {
            resource.addQuantity(quantity);
        } else {
            throw new UnknownResourceTypeException("Resource type not found");
        }
    }

    /**
     * Subtracts a quantity of a resource
     * 
     * @param type
     * @param quantity
     * @return nothing if the resource is found else throws an exception
     */
    public void subtractResource(ResourceType type, int quantity) {
        Resource resource = resources.get(type);
        if (resource != null) {
            resource.subtractQuantity(quantity);
        } else {
            throw new UnknownResourceTypeException("Resource type not found");
        }
    }

    public void setResourceQuantity(ResourceType type, int quantity) {
        Resource resource = resources.get(type);
        if (resource != null) {
            resource.setQuantity(quantity);
        } else {
            throw new UnknownResourceTypeException("Resource type not found");
        }
    }

    /**
     * Get the quantity of a resource
     * 
     * @param type
     * @return the quantity of the resource
     */
    public int getResourceQuantity(ResourceType type) {
        Resource resource = resources.get(type);
        if (resource != null) {
            return resource.getQuantity();
        } else {
            throw new UnknownResourceTypeException("Resource type not found");
        }
    }

    /**
     * display the quantity of all resources
     */
    public void showResources() {
        for (Resource resource : resources.values()) {
            System.out.println(resource.getType() + " : " + resource.getQuantity());
        }
    }
}
