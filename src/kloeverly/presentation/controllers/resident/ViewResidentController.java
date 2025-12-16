package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.*;
import kloeverly.utility.UtilityMethods;

public class ViewResidentController implements InitializableController, AcceptsStringArgument, AcceptsFlashMessage
{
    private DataManager dataManager;
    private Integer residentId;
    private Resident resident;

    @FXML
    private Label nameLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label pointFactorLabel;

    @FXML
    private Label pointsLabel;

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

        nameLabel.setText(resident.getName());
        idLabel.setText(String.valueOf(resident.getId()));
        pointFactorLabel.setText(String.valueOf(resident.getPointFactor()));
        pointsLabel.setText(String.valueOf(resident.getPoints()));
    }

    @FXML
    private void handleUpdate()
    {
        errorLabel.setText("");

        if (residentId == null)
        {
            errorLabel.setText("Kunne ikke finde ID på beboeren.");
            return;
        }

        ViewManager.showView(Views.UPDATE_RESIDENT, String.valueOf(residentId));
    }

    @FXML
    private void handleGoBack()
    {
        ViewManager.showView(Views.RESIDENTS);
    }

    @Override
    public void setFlashMessage(String message)
    {
        UtilityMethods.showFlashMessage(message);
    }
}
