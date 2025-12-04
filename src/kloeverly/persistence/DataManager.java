package kloeverly.persistence;

import kloeverly.domain.*;

import java.util.List;

public interface DataManager
{
  // Residents
  void addResident(Resident resident);
  List<Resident> getAllResidents();
  Resident getResidentById(int id);
  void deleteResident(Resident resident);
  void updateResident(Resident residentToBeUpdated);
  void addPointsToResident(Resident resident, int value);
  void resetPointsForAllResidents();

  // Tasks
    void addTask(Task task);
    List<Task> getAllTasks();
    List<CommonTask> getAllCommonTasks();
    List<ExchangeTask> getAllExchangeTasks();
    List<GreenTask> getAllGreenTasks();
    Task getTaskById(int id);
    void deleteTask(Task task);
    void updateTask(Task task);
    void completeTask(int completedTaskId, Resident byResident);

  // ClimateScore
  void addPointsToClimateScore(int points);
  ClimateScore getClimateScore();
}
