package kloeverly.domain;

import java.io.Serializable;

public class ExchangeTask extends Task implements Serializable
{
  private Resident provider;
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

  public String getProviderName()
  {
    return provider.getName();
  }

  public void setProvider(Resident provider)
  {
    this.provider = provider;
  }

  public int getAmount()
  {
    return amount;
  }

  public void setAmount(int amount)
  {
    this.amount = amount;
  }

  @Override public void completed(Resident byResident)
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

  @Override public void updateFrom(Task other)
  {
    super.updateFrom(other);

    if (other instanceof ExchangeTask exchangeTask)
    {
      this.amount = exchangeTask.amount;
      this.provider = exchangeTask.provider;
    }
  }

  @Override public String getTypeLabel()
  {
    return "Bytteopgaven";
  }

  @Override public String formatTaskCompleted(Resident byResident)
  {
    int remainingAmount = getAmount();
    String amountInfo;

    if (remainingAmount >= 1)
    {
      amountInfo = "Der er " + remainingAmount + " tilbage.";
    }
    else
    {
      amountInfo = "Der er 0 tilbage. Opgaven slettes.";
    }

    if (byResident == null || byResident.getName() == null
        || byResident.getName().isBlank())
    {
      return getTypeLabel() + " \"" + getName() + "\" er udført. " + amountInfo;
    }
    else
    {
      return getTypeLabel() + " \"" + getName() + "\" er udført af \""
          + byResident.getName() + "\". " + amountInfo;
    }
  }
}
