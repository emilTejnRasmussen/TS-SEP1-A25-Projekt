package kloeverly.domain;

import java.io.Serializable;

public class CommonTask extends Task implements Serializable
{
    public CommonTask(String name, String description, int value)
    {
        super(name, description, value);
    }

    @Override
    public void completed(Resident byResident)
    {
        byResident.addPoints(this.getValue());
    }
}
