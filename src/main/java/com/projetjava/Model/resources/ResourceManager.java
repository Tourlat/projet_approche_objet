package com.projetjava.Model.resources;

import com.projetjava.customexceptions.UnknownResourceTypeException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

  private static ResourceManager instance;
  private HashMap<ResourceType, Resource> resources;

  /**
   * Constructor of ResourceManager
   */
  private ResourceManager() {
    resources = new HashMap<>();
    initializeResources();
  }

  /**
   * Singleton pattern : only one instance of ResourceManager
   *
   * @return the instance of ResourceManager
   */
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
   * Add a given quantity of a given resource
   *
   * @param type - the type of the resource
   * @param quantity - the quantity to add
   * @throws UnknownResourceTypeException - exception if the resource is not found
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
   * Subtracts a given quantity of a given resource
   *
   * @param type - the type of the resource
   * @param quantity - the quantity to subtract
   * @throws UnknownResourceTypeException - exception if the resource is not found
   */
  public void subtractResource(ResourceType type, int quantity) {
    Resource resource = resources.get(type);
    if (resource != null) {
      resource.subtractQuantity(quantity);
    } else {
      throw new UnknownResourceTypeException("Resource type not found");
    }
  }

  /**
   * Set the quantity of a given resource
   * @param type - the type of the resource
   * @param quantity - the quantity to set
   * @throws UnknownResourceTypeException - exception if the resource is not found
   */
  public void setResourceQuantity(ResourceType type, int quantity) {
    Resource resource = resources.get(type);
    if (resource != null) {
      resource.setQuantity(quantity);
    } else {
      throw new UnknownResourceTypeException("Resource type not found");
    }
  }

  /**
   * Get the quantity of a given resource
   *
   * @param type - the type of the resource
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
   * Get all resources
   * @return the resources
   */
  public Map<ResourceType, Resource> getResources() {
    return resources;
  }
}
