package kloeverly.domain;

import java.io.Serializable;

public class ExchangeTask extends Task implements Serializable
{
    private Resident provider;

    public ExchangeTask(String name, String description, int value, Resident provider)
    {
        super(name, description, value);
        this.provider = provider;
    }

    public Resident getProvider()
    {
        return provider;
    }

    public void setProvider(Resident provider)
    {
        this.provider = provider;
    }

    @Override
    public void updateFrom(Task other){
        super.updateFrom(other);
        if (other instanceof ExchangeTask exchangeTask){
            this.provider = exchangeTask.getProvider();
        }
    }

    @Override
    public void completed()
    {
        //TODO implement completed exchange task
    }
}
