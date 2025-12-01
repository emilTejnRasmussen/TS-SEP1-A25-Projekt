package kloeverly.presentation.core;

import kloeverly.persistence.DataManager;
import kloeverly.persistence.FileDataManager;

public class ControllerConfigurator
{
    public static void configure(Object controller) {
        if (controller == null) return;

        if (controller instanceof  InitializableController ctrl){
            ctrl.init(getDataManager());
        } else {
            throw new RuntimeException("Controller '" + controller.getClass().getSimpleName() + "' does not implement InitializableController");
        }
    }

    private static DataManager getDataManager()
    {
        return new FileDataManager();
    }
}
