package kloeverly.domain;

public abstract class Task
{
    private int id;
    private String name;
    private String description;
    private int value;

    public Task(String name, String description, int value)
    {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public void updateFrom(Task other){
        this.name = other.getName();
        this.description = other.getDescription();
        this.value = other.getValue();
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public abstract void completed();
}
