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

public class UpdateResidentController implements InitializableController, AcceptsStringArgument
{
    private DataManager dataManager;
    private Integer residentId;
    private Resident resident;

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

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        errorLabel.setText("");
        loadResidentIfReady();
    }

    @Override
    public void setArgument(String argument)
    {
        errorLabel.setText("");

        try
        {
            residentId = Integer.parseInt(argument);
        }
        catch (NumberFormatException e)
        {
            residentId = null;
        }

        loadResidentIfReady();
    }

    private void loadResidentIfReady()
    {
        if (dataManager == null || residentId == null)
        {
            return;
        }

        resident = dataManager.getResidentById(residentId);
        if (resident == null)
        {
            errorLabel.setText("Kunne ikke finde beboer.");
            return;
        }

        idLabel.setText(String.valueOf(resident.getId()));
        pointsLabel.setText(String.valueOf(resident.getPoints()));
        nameField.setText(resident.getName());
        pointFactorField.setText(String.valueOf(resident.getPointFactor()));
    }

    @FXML
    private void handleUpdate()
    {
        errorLabel.setText("");

        if (resident == null)
        {
            errorLabel.setText("Ingen beboer valgt.");
            return;
        }

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

        resident.setName(name);
        resident.setPointFactor(pointFactor);

        dataManager.updateResident(resident);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.VIEW_SINGLE_RESIDENT, residentId + "", "Beboer '" + resident.getName() + "' er opdateret");
    }

    @FXML
    private void handleCancel()
    {
        ViewManager.showView(Views.RESIDENTS);
    }
}
