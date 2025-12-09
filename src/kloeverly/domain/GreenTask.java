package kloeverly.domain;

import java.io.Serializable;

public class GreenTask extends Task implements Serializable
{
    public GreenTask(String name, String description, int value)
    {
        // Task håndterer name, description og value
        super(name, description, value);
    }

    @Override
    public void completed(Resident byResident)
    {
        // Grønne opgaver giver kun fællespulje-point,
        // ikke personlige point – derfor tom.
    }
}
