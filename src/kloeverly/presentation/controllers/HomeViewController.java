package kloeverly.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;

import java.util.Optional;

public class HomeViewController implements InitializableController
{
    @FXML
    private Button runExternalScreenBtn;
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
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft nulstilling");
        confirm.setHeaderText("Nulstil individuelle point");
        confirm.setContentText("Er du sikker på, at du vil nulstille alle beboers individuelle point?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            dataManager.resetPointsForAllResidents();
        }
    }

    public void handleShowExternalScreen()
    {
        ViewManager.showExternalScreen(runExternalScreenBtn);
        runExternalScreenBtn.setDisable(true);
    }

    private void loadStats()
    {
        climateScoreLbl.setText(dataManager.getClimateScore().getTotalGreenPoints() + "");
        residentAmountLbl.setText(dataManager.getAllResidents().size() + "");
        greenTaskAmountLbl.setText(dataManager.getAllGreenTasks().size() + "");
        commonTaskAmountLbl.setText(dataManager.getAllCommonTasks().size() + "");
        exchangeTaskAmountLbl.setText(dataManager.getAllExchangeTasks().size() + "");
    }
}
