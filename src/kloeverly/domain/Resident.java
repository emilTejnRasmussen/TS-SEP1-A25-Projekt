package kloeverly.domain;

import java.io.Serializable;

public class Resident implements Serializable
{
    private int id;
    private String name;
    private double pointFactor;
    private int points;

    // Konstruktør med KUN navn (bruges fx i AddResidentController)
    public Resident(String name) {
        this(name, 1.0);        // standard pointFactor = 1.0
    }

    // eksisterende konstruktør (navn + pointFactor)
    public Resident(String name, double pointFactor){
        this.name = name;
        this.pointFactor = pointFactor;
        this.points = 0;
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

    // hvis vi vil sætte point direkte et sted
    public void setPoints(int points)
    {
        this.points = points;
    }

    public void resetPoints()
    {
        this.points = 0;
    }
}
