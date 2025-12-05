package kloeverly.presentation.controllers;

import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class MainViewController
{
    public void handleViewResidents()
    {
        ViewManager.showView(Views.RESIDENTS);
    }

    public void handleViewGreenTasks()
    {
        ViewManager.showView(Views.ADD_GREEN_TASK);
    }

    public void handleViewCommonTasks()
    {
        ViewManager.showView(Views.COMMON_TASKS);
    }

    public void handleViewExchangeTasks()
    {

    }
}
