package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class UpdateResidentController implements InitializableController, AcceptsStringArgument {

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
        loadResidentData();
    }

    @FXML
    private Label idLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField pointFactorField;

    @FXML
    private Label errorLabel;

    private void loadResidentData() {
        if (resident == null) return;

        idLabel.setText(String.valueOf(resident.getId()));
        pointsLabel.setText(String.valueOf(resident.getPoints()));
        nameField.setText(resident.getName());
        pointFactorField.setText(String.valueOf(resident.getPointFactor()));
    }

    @FXML
    private void handleUpdate() {
        if (resident == null) return;

        String name = nameField.getText().trim();
        String factorText = pointFactorField.getText().trim();

        if (name.isEmpty() || factorText.isEmpty()) {
            errorLabel.setText("Navn og pointfaktor skal udfyldes.");
            return;
        }

        double pointFactor;
        try {
            pointFactor = Double.parseDouble(factorText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Pointfaktor skal være et tal, fx 1.0 eller 1.5.");
            return;
        }

        resident.setName(name);
        resident.setPointFactor(pointFactor);
        dataManager.updateResident(resident);

        ViewManager.showView(Views.RESIDENTS);
    }

    @FXML
    private void handleCancel() {
        ViewManager.showView(Views.RESIDENTS);
    }
}
