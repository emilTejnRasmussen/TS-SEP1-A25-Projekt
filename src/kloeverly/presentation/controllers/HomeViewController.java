package kloeverly.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;

public class HomeViewController implements InitializableController
{
    @FXML
    private Label residentAmountLbl;
    @FXML
    private Label greenTaskAmountLbl;
    @FXML
    private Label commonTaskAmountLbl;
    @FXML
    private Label exchangeTaskAmountLbl;
    @FXML
    private Label climateScoreLbl;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        loadStats();
    }

    public void handleResetResidentPoints()
    {
        System.out.println("Not implemented");
    }

    public void handleShowExternalScreen()
    {
        System.out.println("Not implemented");
    }

    private void loadStats()
    {
        climateScoreLbl.setText(dataManager.getClimateScore() + "");
        residentAmountLbl.setText(dataManager.getAllResidents().size() + "");
        greenTaskAmountLbl.setText(dataManager.getAllGreenTasks().size() + "");
        commonTaskAmountLbl.setText(dataManager.getAllCommonTasks().size() + "");
        exchangeTaskAmountLbl.setText(dataManager.getAllExchangeTasks().size() + "");
    }
}
