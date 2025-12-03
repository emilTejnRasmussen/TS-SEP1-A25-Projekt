package kloeverly.domain;

import java.io.Serializable;

public class ExchangeTask extends Task implements Serializable
{
  private final Resident provider;
  private int amount;

  public ExchangeTask(String name, String description, int value, int amount,
      Resident provider)
  {
    super(name, description, value);
    this.provider = provider;
    this.amount = amount;
  }

  public Resident getProvider()
  {
    return provider;
  }

  public int getAmount()
  {
    return amount;
  }

  @Override
  public void completed(Resident byResident)
  {
    if (byResident.getPoints() < getValue())
    {
      throw new IllegalStateException(
          "Recipient doesn't have enough points to complete.");
    }
    else
    {
      provider.addPoints(getValue());
      byResident.addPoints(-getValue());
      amount--;
    }
  }
}
