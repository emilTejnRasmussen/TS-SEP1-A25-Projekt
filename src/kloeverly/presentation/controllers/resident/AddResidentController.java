package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddResidentController implements InitializableController
{
    private DataManager dataManager;

    @FXML
    private TextField nameField;

    @FXML
    private TextField pointFactorField;

    @FXML
    private Label errorLabel;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        errorLabel.setText("");
    }

    @FXML
    private void handleAdd()
    {
        errorLabel.setText("");

        String name = nameField.getText() == null ? "" : nameField.getText().trim();
        String factorText = pointFactorField.getText() == null ? "" : pointFactorField.getText().trim();

        if (name.isEmpty())
        {
            errorLabel.setText("Navn må ikke være tomt.");
            return;
        }

        if (factorText.isEmpty())
        {
            errorLabel.setText("Pointfaktor må ikke være tom.");
            return;
        }

        double pointFactor;
        try
        {
            pointFactor = Double.parseDouble(factorText);
        }
        catch (NumberFormatException e)
        {
            errorLabel.setText("Pointfaktor skal være et tal, fx 1.0 eller 1.5.");
            return;
        }

        if (pointFactor <= 0)
        {
            errorLabel.setText("Pointfaktor skal være større end 0.");
            return;
        }

        Resident resident = new Resident(name, pointFactor);
        dataManager.addResident(resident);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.RESIDENTS, null, "Beboer '" + resident.getName() + "' er oprettet");
    }

    @FXML
    private void handleCancel()
    {
        ViewManager.showView(Views.RESIDENTS);
    }
}
