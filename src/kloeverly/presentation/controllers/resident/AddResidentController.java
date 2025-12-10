package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddResidentController implements InitializableController {

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @FXML
    private TextField nameField;

    @FXML
    private TextField pointFactorField;

    @FXML
    private Label errorLabel;


    @FXML
    private void handleAdd() {
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

        Resident resident = new Resident(name, pointFactor);
        dataManager.addResident(resident);

        ViewManager.showView(Views.RESIDENTS);
    }

    @FXML
    private void handleCancel() {
        ViewManager.showView(Views.RESIDENTS);
    }
}
