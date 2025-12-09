package kloeverly.presentation.core;

import kloeverly.persistence.DataManager;
//import kloeverly.persistence.FileDataManager;
import kloeverly.persistence.InMemoryDataManager;

public class ControllerConfigurator
{
    public static void configure(Object controller) {
        if (controller == null) return;

        if (controller instanceof  InitializableController ctrl){
            ctrl.init(dataManager());
        } else {
            throw new RuntimeException("Controller '" + controller.getClass().getSimpleName() + "' does not implement InitializableController");
        }
    }

    private static final DataManager DATA_MANAGER = new InMemoryDataManager();

    private static DataManager dataManager() {
        return DATA_MANAGER;
    }
}
