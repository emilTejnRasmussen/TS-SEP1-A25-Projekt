package kloeverly.persistence;

import kloeverly.domain.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileDataManager implements DataManager
{
  private final Path path = Path.of("data", "save_file.bin");

  public FileDataManager()
  {
    try
    {
      Files.createDirectories(path.getParent());
    }
    catch (IOException e)
    {
      throw new RuntimeException("Error creating Data folder", e);
    }
    if (!Files.exists(path))
    {
      saveData(new DataContainer());
    }
  }

  private void saveData(DataContainer data)
  {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(
        new FileOutputStream(path.toFile())))
    {
      outputStream.writeObject(data);
    }
    catch (IOException e)
    {
      throw new RuntimeException("Error saving data container", e);
    }
  }

  private DataContainer loadData()
  {
    try (ObjectInputStream inputStream = new ObjectInputStream(
        new FileInputStream(path.toFile())))
    {
      return (DataContainer) inputStream.readObject();
    }
    catch (IOException | ClassNotFoundException e)
    {
      throw new RuntimeException("Error loading data container", e);
    }
  }

  @Override
  public void addResident(Resident resident)
  {
    DataContainer data = loadData();
    data.addResident(resident);
    saveData(data);
  }

  @Override
  public List<Resident> getAllResidents()
  {
    return loadData().getResidents();
  }

  @Override
  public Resident getResidentById(int id)
  {
    return loadData().getResidentById(id);
  }

  @Override
  public void deleteResident(Resident resident)
  {
    DataContainer data = loadData();
    data.deleteResident(resident);
    saveData(data);
  }

  @Override
  public void updateResident(Resident residentToBeUpdated)
  {
    DataContainer data = loadData();
    data.updateResident(residentToBeUpdated);
    saveData(data);
  }

  @Override
  public void resetPointsForAllResidents() {
    DataContainer data = loadData();
    data.resetPointForAllResidents();
    saveData(data);
  }

  @Override public void addTask(Task task)
  {
    DataContainer data = loadData();
    data.addTask(task);
    saveData(data);
  }

  @Override public List<Task> getAllTasks()
  {
    return loadData().getTasks();
  }

  @Override
  public List<ExchangeTask> getAllExchangeTasks()
  {
    return getAllTasks().stream().filter(ExchangeTask.class::isInstance).map(ExchangeTask.class::cast).toList();
  }

  @Override public List<CommonTask> getAllCommonTasks()
  {
    return getAllTasks().stream().filter(t -> t instanceof CommonTask)
        .map(t -> (CommonTask) t).toList();
  }

  @Override public List<GreenTask> getAllGreenTasks()
  {
    return getAllTasks().stream().filter(t -> t instanceof GreenTask)
        .map(t -> (GreenTask) t).toList();
  }

  @Override public Task getTaskById(int id)
  {
    return loadData().getTaskById(id);
  }

  @Override public void deleteTask(Task task)
  {
    DataContainer data = loadData();
    data.deleteTask(task);
    saveData(data);
  }

  @Override public void updateTask(Task task)
  {
    DataContainer data = loadData();
    data.updateTask(task);
    saveData(data);
  }

    @Override
    public void completeTask(int completedTaskId, Resident byResident)
    {
        DataContainer data = loadData();
        data.completeTask(completedTaskId, byResident.getId());
        saveData(data);
    }

    @Override public void addPointsToClimateScore(int points)
  {
    DataContainer data = loadData();
    data.addPointsToClimateScore(points);
    saveData(data);
  }

  @Override public ClimateScore getClimateScore()
  {
    return loadData().getClimateScore();
  }
}
