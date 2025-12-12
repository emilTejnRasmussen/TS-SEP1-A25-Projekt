package kloeverly.persistence;

import kloeverly.domain.ClimateScore;
import kloeverly.domain.ExchangeTask;
import kloeverly.domain.Resident;
import kloeverly.domain.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataContainer implements Serializable
{
  private List<Resident> residents;
  private List<Task> tasks;
  private ClimateScore climateScore;

  public DataContainer()
  {
    this.residents = new ArrayList<>();
    this.tasks = new ArrayList<>();
    this.climateScore = new ClimateScore();
  }

  public void addResident(Resident resident)
  {
    int maxId = residents.stream().mapToInt(Resident::getId).max().orElse(1);
    resident.setId(maxId + 1);
    residents.add(resident);
  }

  public List<Resident> getResidents()
  {
    return new ArrayList<>(residents);
  }

  public Resident getResidentById(int id)
  {
    return residents.stream().filter(r -> r.getId() == id).findFirst()
        .orElseThrow(
            () -> new RuntimeException("No resident by " + id + "found."));
  }

  public void deleteResident(Resident resident)
  {
    residents.remove(getResidentById(resident.getId()));
  }

  public void updateResident(Resident residentToBeUpdated)
  {
    Resident resident = getResidentById(residentToBeUpdated.getId());
    resident.setName(residentToBeUpdated.getName());
    resident.setPointFactor(residentToBeUpdated.getPointFactor());
  }

  public void resetPointForAllResidents()
  {
    residents.forEach(Resident::resetPoints);
  }

  public void addTask(Task task)
  {
    int nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
    task.setId(nextId);
    this.tasks.add(task);
  }

  public List<Task> getTasks()
  {
    return new ArrayList<>(tasks);
  }

  public Task getTaskById(int id)
  {
    return tasks.stream().filter(t -> t.getId() == id).findFirst().orElseThrow(
        () -> new RuntimeException("Error no Task with id '" + id + "'"));
  }

  public void deleteTask(Task task)
  {
    tasks.removeIf(t -> t.getId() == task.getId());
  }

  public void updateTask(Task task)
  {
    Task taskToBeUpdated = getTaskById(task.getId());
    if (taskToBeUpdated != null)
      taskToBeUpdated.updateFrom(task);
  }

  public void completeTask(int completedTaskId, int byResidentId){
      Task completedTask = getTaskById(completedTaskId);
      Resident byResident = getResidentById(byResidentId);

      completedTask.completed(byResident);

      if (completedTask instanceof ExchangeTask exchangeTask){
          int providerId = exchangeTask.getProvider().getId();
          getResidentById(providerId).addPoints(exchangeTask.getValue());
      }
  }

    public void addPointsToClimateScore(int points)
  {
    this.climateScore.addPoints(points);
  }

  public ClimateScore getClimateScore()
  {
    return this.climateScore;
  }

  public void resetClimateScore() {
      this.climateScore.resetTotalGreenPoints();
  }
}
