package kloeverly.persistence;

import kloeverly.domain.ClimateScore;
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
    public void addResident(Resident resident){
        int nextId = residents.stream()
                .mapToInt(Resident::getId)
                .max()
                .orElse(0) + 1;
        resident.setId(nextId);
        this.residents.add(resident);
    }

    public List<Resident> getResidents(){
        return new ArrayList<>(residents);
    }

    public Resident getResidentById(int id){
        return residents.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error no resident with id '" + id + "'"));
    }

    public void deleteResident(Resident resident){
        residents.removeIf(r -> r.getId() == resident.getId());
    }

    public void updateResident(Resident other){
        Resident residentToBeUpdated = getResidentById(other.getId());

        residentToBeUpdated.setName(other.getName());
        residentToBeUpdated.setPointFactor(other.getPointFactor());
        residentToBeUpdated.setPoints(other.getPoints());
    }

    public void addTask(Task task){
        int nextId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
        task.setId(nextId);
        this.tasks.add(task);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public Task getTaskById(int id){
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error no Task with id '" + id + "'"));
    }

    public void deleteTask(Task task){
        tasks.removeIf(t -> t.getId() == task.getId());
    }

    public void updateTask(Task task){
        Task taskToBeUpdated = getTaskById(task.getId());
        if (taskToBeUpdated != null)
            taskToBeUpdated.updateFrom(task);
    }

    public void addPointsToClimateScore(int points){
        this.climateScore.addPoints(points);
    }

    public ClimateScore getClimateScore() {
        return this.climateScore;
    }
}
