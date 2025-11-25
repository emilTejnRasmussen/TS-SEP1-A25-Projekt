package kloeverly.domain;

import java.io.Serializable;

public class ClimateScore implements Serializable
{
    private int totalGreenPoints;

    public ClimateScore()
    {
        this.totalGreenPoints = 0;
    }

    public int getTotalGreenPoints()
    {
        return totalGreenPoints;
    }

    public void addPoints(int totalGreenPoints)
    {
        this.totalGreenPoints += totalGreenPoints;
    }

    public void reset()
    {
        this.totalGreenPoints = 0;
    }
}
