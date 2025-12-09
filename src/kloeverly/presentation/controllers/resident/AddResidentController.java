package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.presentation.core.InitializableController;

public class AddResidentController implements InitializableController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField pointFactorField;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @FXML
    private void handleAdd() {
        String name = nameField.getText().trim();
        String factorText = pointFactorField.getText().trim();

        // Simpel validering – samme stil som i undervisningen
        if (name.isEmpty()) {
            showError("Navn mangler.");
            return;
        }

        double factor;
        try {
            factor = Double.parseDouble(factorText);
        } catch (NumberFormatException e) {
            showError("Pointfaktor skal være et tal (f.eks. 1.0).");
            return;
        }

        if (factor < 1.0 || factor > 2.0) {
            showError("Pointfaktor skal være mellem 1.0 og 2.0.");
            return;
        }

        // Opret beboer og gem via DataManager
        Resident resident = new Resident(name, factor);
        dataManager.addResident(resident);

        // Efter vellykket oprettelse: tilbage til beboerlisten
        ViewManager.showView(Views.RESIDENTS);
    }

    @FXML
    private void handleCancel() {
        // Gå bare tilbage til beboerlisten uden at oprette
        ViewManager.showView(Views.RESIDENTS);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
