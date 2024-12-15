package com.projetjava.Model.resources;

public class Resource {

  private ResourceType type;
  private int quantity;

  /**
   * Constructor of Resource
   * @param type - the type of the resource
   * @param quantity - the quantity of the resource
   */
  public Resource(ResourceType type, int quantity) {
    this.type = type;
    this.quantity = quantity;
  }

  /**
   * Get the type of the resource
   * @return the type of the resource
   */
  public ResourceType getType() {
    return type;
  }

  /**
   * Set the type of the resource
   * @param type - the type of the resource
   */
  public void setType(ResourceType type) {
    this.type = type;
  }

  /**
   * Get the quantity of the resource
   * @return the quantity of the resource
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Set the quantity of the resource
   * @param quantity - the quantity of the resource
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Add a given quantity to the resource
   * @param quantity - the quantity to add
   */
  public void addQuantity(int quantity) {
    this.quantity += quantity;
  }

  /**
   * Subtract a given quantity to the resource
   * If the quantity is negative, the quantity is set to 0
   * @param quantity - the quantity to subtract
   */
  public void subtractQuantity(int quantity) {
    if (this.quantity - quantity < 0) {
      this.quantity = 0;
    } else {
      this.quantity -= quantity;
    }
  }
}
