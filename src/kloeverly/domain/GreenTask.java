package kloeverly.domain;

import java.io.Serializable;

public class GreenTask extends Task implements Serializable
{
    private int amount;

    public GreenTask(String name, String description, int value) {
        super(name, description, value);
    }

    @Override
    public void completed(Resident byResident) {

    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    @Override
    public String formatTaskCompleted(Resident byResident)
    {
        if (amount < 1) return super.formatTaskCompleted(byResident);
        if (amount == 1) return "Den grønne opgave"  + " \"" + getName() + "\" er udført.";
        return "Den grønne opgave"  + " \"" + getName() + "\" er udført " + amount + " gange";
    }
}


