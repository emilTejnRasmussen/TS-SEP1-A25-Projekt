package kloeverly.presentation.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

public class MainViewController
{
    @FXML private Button residentBtn;
    @FXML private Button greenBtn;
    @FXML private Button commonBtn;
    @FXML private Button exchangeBtn;

    private List<Button> menuButtons;

    public void initialize()
    {
        menuButtons = List.of(residentBtn, greenBtn, commonBtn, exchangeBtn);
    }

    public void handleViewResidents()
    {
      ViewManager.showView(Views.RESIDENTS);
    }

    public void handleViewGreenTasks()
    {
        ViewManager.showView(Views.RESIDENTS);
    }

    public void handleViewCommonTasks()
    {
        ViewManager.showView(Views.COMMON_TASKS);
        removeAllActive();
        commonBtn.getStyleClass().add("active");
    }

    public void handleViewExchangeTasks()
    {
        ViewManager.showView(Views.EXCHANGE_TASKS);
        removeAllActive();
        exchangeBtn.getStyleClass().add("active");
    }

    public void handleShowHomeView()
    {
        ViewManager.showView(Views.HOME);
        removeAllActive();
    }

    public void handleExit()
    {
        Platform.exit();
    }

    private void removeAllActive()
    {
        menuButtons.forEach(b -> b.getStyleClass().remove("active"));
    }
}