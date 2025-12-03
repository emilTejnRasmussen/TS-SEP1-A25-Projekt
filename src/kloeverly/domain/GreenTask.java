package kloeverly.domain;

import java.io.Serializable;

public class GreenTask extends Task implements Serializable
{
    public GreenTask(String name, String description, int value) {
        super(name, description, value);
    }

    @Override
    public void completed(Resident byResident) {

    }
}
