package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewResidentController
        implements InitializableController, AcceptsStringArgument {

    @FXML
    private Label titleLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label pointFactorLabel;

    @FXML
    private Label pointsLabel;

    private DataManager dataManager;
    private int residentId;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void setArgument(String argument) {
        // argument er ID sendt fra ViewAllResidentsController
        this.residentId = Integer.parseInt(argument);
        loadResident();
    }

    private void loadResident() {
        Resident r = dataManager.getResidentById(residentId);

        titleLabel.setText("Detaljer for: " + r.getName());
        idLabel.setText("ID: " + r.getId());
        nameLabel.setText("Navn: " + r.getName());
        pointFactorLabel.setText("Pointfaktor: " + r.getPointFactor());
        pointsLabel.setText("Point: " + r.getPoints());
    }

    @FXML
    private void handleBack() {
        ViewManager.showView(Views.RESIDENTS);
    }
}
