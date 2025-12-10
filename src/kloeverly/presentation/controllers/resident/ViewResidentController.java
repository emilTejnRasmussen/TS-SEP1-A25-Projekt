package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewResidentController implements InitializableController, AcceptsStringArgument {

    private DataManager dataManager;
    private Resident resident;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void setArgument(String argument) {
        int residentId = Integer.parseInt(argument);
        resident = dataManager.getResidentById(residentId);
        loadResidentDetails();
    }

    @FXML
    private Label nameLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label pointFactorLabel;

    @FXML
    private Label pointsLabel;

    private void loadResidentDetails() {
        if (resident == null) return;

        nameLabel.setText(resident.getName());
        idLabel.setText(String.valueOf(resident.getId()));
        pointFactorLabel.setText(String.valueOf(resident.getPointFactor()));
        pointsLabel.setText(String.valueOf(resident.getPoints()));
    }

    // Kaldt fra ViewTemplate-knappen "Opdater"
    @FXML
    private void handleUpdate() {
        if (resident == null) return;

        ViewManager.showView(
                Views.UPDATE_RESIDENT,
                String.valueOf(resident.getId())
        );
    }

    // Kaldt fra ViewTemplate-knappen "Tilbage"
    @FXML
    private void handleGoBack() {
        ViewManager.showView(Views.RESIDENTS);
    }
}
