package kloeverly.domain;

import java.io.Serializable;

public class Resident implements Serializable
{
  private int id;
  private String name;
  private double pointFactor;
  private int points;

  public Resident(String name, double pointFactor){
    this.name = name;
    this.pointFactor = pointFactor;
    points = 0;
  }

  public void addPoints(int value) {
    points += value;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public double getPointFactor()
  {
    return pointFactor;
  }

  public void setPointFactor(double pointFactor)
  {
    this.pointFactor = pointFactor;
  }

  public int getPoints()
  {
    return points;
  }

  public void resetPoints()
  {
    this.points = 0;
  }
}
