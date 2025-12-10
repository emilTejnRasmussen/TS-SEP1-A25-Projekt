package kloeverly.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Platform;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class MainViewController {

    @FXML
    private Button residentsBtn;

    @FXML
    private Button greenTasksBtn;

    @FXML
    private Button commonTasksBtn;

    @FXML
    private Button exchangeTasksBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private void handleViewResidents() {
        ViewManager.showView(Views.RESIDENTS);
    }

    @FXML
    private void handleViewGreenTasks() {
        ViewManager.showView(Views.GREEN_TASKS);
    }

    @FXML
    private void handleViewCommonTasks() {
        ViewManager.showView(Views.COMMON_TASKS);
    }

    @FXML
    private void handleViewExchangeTasks() {
        ViewManager.showView(Views.EXCHANGE_TASKS);
    }

    @FXML
    private void handleShowHomeView() {
        ViewManager.showView(Views.HOME);
    }

    // --- Exit-knap ---

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
