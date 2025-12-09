package kloeverly.persistence;

import kloeverly.domain.GreenTask;
import kloeverly.domain.Resident;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDataManager implements DataManager {

    private final List<Resident> residents = new ArrayList<>();
    private int nextId = 1;

    public void addGreenTask(GreenTask task) {
        addTask(task);
    }
    public InMemoryDataManager() {
        // 3 test-beboere som vises ved opstart
        addResident(new Resident("Bølge månelys Solvind", 1.2));
        addResident(new Resident("Hav-Storm Månelys", 1.0));
        addResident(new Resident("Flora-Lys Mælkebøtte", 1.5));

        addGreenTask(new GreenTask("Plant et træ", "Plant et træ i fællesområdet", 10));
        addGreenTask(new GreenTask("Genbrug metal", "Sortér og aflever metal på genbrugsstationen", 5));
        addGreenTask(new GreenTask("Cykel til arbejde", "Drop bilen én dag", 8));

    }

    @Override
    public void addResident(Resident resident) {
        resident.setId(nextId++);
        residents.add(resident);
    }

    @Override
    public List<Resident> getAllResidents() {
        return new ArrayList<>(residents);
    }

    @Override
    public Resident getResidentById(int id) {
        return residents.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteResident(Resident resident) {
        residents.removeIf(r -> r.getId() == resident.getId());
    }

    @Override
    public void updateResident(Resident resident) {
        for (Resident r : residents) {
            if (r.getId() == resident.getId()) {
                r.setName(resident.getName());
                r.setPointFactor(resident.getPointFactor());
                r.setPoints(resident.getPoints());
                return;
            }
        }
    }



    // ---- OPGAVEN KRÆVER ikke task & climate ----
    // men vi implementerer tomme metoder, så DataManager interface opfyldes:

    @Override public void addTask(kloeverly.domain.Task task) {}
    @Override public List<kloeverly.domain.Task> getAllTasks() { return List.of(); }
    @Override public List<kloeverly.domain.CommonTask> getAllCommonTasks() { return List.of(); }
    @Override public List<kloeverly.domain.ExchangeTask> getAllExchangeTasks() { return List.of(); }
    @Override public List<kloeverly.domain.GreenTask> getAllGreenTasks() { return List.of(); }
    @Override public kloeverly.domain.Task getTaskById(int id) { return null; }
    @Override public void deleteTask(kloeverly.domain.Task task) {}
    @Override public void updateTask(kloeverly.domain.Task task) {}
    @Override public void addPointsToClimateScore(int points) {}
    @Override public kloeverly.domain.ClimateScore getClimateScore() { return null; }
}
