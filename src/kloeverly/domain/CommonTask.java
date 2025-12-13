package kloeverly.domain;

import java.io.Serializable;

public class CommonTask extends Task implements Serializable
{
    private int amount;

    public CommonTask(String name, String description, int value, int amount)
    {
        super(name, description, value);
        this.amount = amount;
    }

    @Override
    public void completed(Resident byResident)
    {
        byResident.addPoints((int) Math.round(this.getValue() * byResident.getPointFactor()));
    }

    @Override
    public void updateFrom(Task other)
    {
        super.updateFrom(other);
        if (other instanceof CommonTask commonTask){
            this.amount = commonTask.getAmount();
        }
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}
