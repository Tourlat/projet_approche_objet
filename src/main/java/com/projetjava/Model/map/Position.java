package com.projetjava.Model.map;

import java.util.Objects;

public class Position {

  private int x;
  private int y;

  /**
   * Constructor of Position
   * @param x - the x coordinate
   * @param y - the y coordinate
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get the x coordinate
   * @return the x coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Get the y coordinate
   * @return the y coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Equals method
   * @param o - the object to compare
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  /**
   * Hashcode method
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
