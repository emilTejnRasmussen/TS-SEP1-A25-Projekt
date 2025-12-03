package kloeverly.presentation.controllers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class MainViewController
{
    public void handleViewResidents()
    {

    }

    public void handleViewGreenTasks()
    {

    }

    public void handleViewCommonTasks()
    {
        ViewManager.showView(Views.COMMON_TASKS);
    }

    public void handleViewExchangeTasks()
    {

    }

    public void handleShowHomeView()
    {
        ViewManager.showView(Views.HOME);
    }

    public void handleExit()
    {
        Platform.exit();
    }
}
