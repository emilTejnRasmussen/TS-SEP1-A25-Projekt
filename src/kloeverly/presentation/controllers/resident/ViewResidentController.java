package kloeverly.presentation.controllers.resident;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewResidentController implements InitializableController, AcceptsStringArgument
{
    public TextField nameField;
    public TextField idField;
    public TextField pointFactorField;
    public TextField pointsField;

    public Label errorLabel;

    private DataManager dataManager;
    private Integer residentId;

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

    public void handleUpdate()
    {
        errorLabel.setText("");

        if (residentId == null)
        {
            errorLabel.setText("Kunne ikke finde ID på beboeren.");
            return;
        }

        ViewManager.showView(Views.UPDATE_RESIDENT, String.valueOf(residentId));
    }

    public void handleGoBack()
    {
        ViewManager.showView(Views.RESIDENTS);
    }

    private void loadResidentIfReady()
    {
        if (dataManager == null || residentId == null)
        {
            return;
        }

        Resident resident;
        try
        {
            resident = dataManager.getResidentById(residentId);
        }
        catch (RuntimeException e)
        {
            errorLabel.setText("Kunne ikke finde beboer.");
            return;
        }

        idField.setText(String.valueOf(resident.getId()));
        nameField.setText(resident.getName());
        pointFactorField.setText(String.valueOf(resident.getPointFactor()));
        pointsField.setText(String.valueOf(resident.getPoints()));
    }
}
