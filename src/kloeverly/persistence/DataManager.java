package kloeverly.persistence;

import kloeverly.domain.*;

import java.util.List;

public interface DataManager
{
    void addResident(Resident resident);
    List<Resident> getAllResidents();
    Resident getResidentById(int id);
    void deleteResident(Resident resident);
    void updateResident(Resident resident);

    void addTask(Task task);
    List<Task> getAllTasks();
    List<CommonTask> getAllCommonTasks();
    List<ExchangeTask> getAllExchangeTasks();
    List<GreenTask> getAllGreenTasks();
    Task getTaskById(int id);
    void deleteTask(Task task);
    void updateTask(Task task);

    void addPointsToClimateScore(int points);
    ClimateScore getClimateScore();
}
