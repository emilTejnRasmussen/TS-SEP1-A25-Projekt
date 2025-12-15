package kloeverly.presentation.controllers.resident;

import javafx.scene.control.Button;
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
    public Label idLabel;
    public Label pointsLabel;

    public TextField nameField;
    public TextField pointFactorField;

    public Label generalErrorLbl;
    public Label nameErrorLbl;
    public Label factorErrorLbl;

    public Button saveButton;

    private DataManager dataManager;
    private Integer residentId;
    private Resident resident;

    private boolean nameTouched;
    private boolean factorTouched;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;

        nameTouched = false;
        factorTouched = false;

        clearErrors();

        if (saveButton != null)
        {
            saveButton.setDisable(false);
            saveButton.setOpacity(1.0);
        }

        loadResidentIfReady();
    }

    @Override
    public void setArgument(String argument)
    {
        clearErrors();

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

    public void onInputChanged()
    {
        if (nameField != null && nameField.isFocused())
        {
            nameTouched = true;
            nameErrorLbl.setText("");
            validateName();
        }
        else if (pointFactorField != null && pointFactorField.isFocused())
        {
            factorTouched = true;
            factorErrorLbl.setText("");
            validateFactor();
        }
    }

    public void handleUpdate()
    {
        clearErrors();

        if (resident == null)
        {
            generalErrorLbl.setText("Ingen beboer valgt.");
            return;
        }

        nameTouched = true;
        factorTouched = true;

        boolean ok = validateName() & validateFactor();
        if (!ok)
        {
            return;
        }

        String name = nameField.getText().trim();
        double factor = Double.parseDouble(pointFactorField.getText().trim());

        resident.setName(name);
        resident.setPointFactor(factor);

        dataManager.updateResident(resident);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.RESIDENTS);
    }

    public void handleCancel()
    {
        ViewManager.showView(Views.RESIDENTS);
    }

    private void clearErrors()
    {
        if (generalErrorLbl != null) generalErrorLbl.setText("");
        if (nameErrorLbl != null) nameErrorLbl.setText("");
        if (factorErrorLbl != null) factorErrorLbl.setText("");
    }

    private void loadResidentIfReady()
    {
        if (dataManager == null || residentId == null)
        {
            return;
        }

        Resident loaded;
        try
        {
            loaded = dataManager.getResidentById(residentId);
        }
        catch (RuntimeException e)
        {
            generalErrorLbl.setText("Kunne ikke finde beboer.");
            resident = null;
            return;
        }

        resident = loaded;

        idLabel.setText(String.valueOf(resident.getId()));
        pointsLabel.setText(String.valueOf(resident.getPoints()));

        nameField.setText(resident.getName());
        pointFactorField.setText(String.valueOf(resident.getPointFactor()));
    }

    private boolean validateName()
    {
        if (!nameTouched) return true;

        String name = safeTrim(nameField);
        if (!containsLetter(name))
        {
            nameErrorLbl.setText("Navn skal indeholde bogstaver");
            return false;
        }

        nameErrorLbl.setText("");
        return true;
    }

    private boolean validateFactor()
    {
        if (!factorTouched) return true;

        String txt = safeTrim(pointFactorField);
        if (txt.isEmpty())
        {
            factorErrorLbl.setText("Pointfaktor skal være et tal");
            return false;
        }

        try
        {
            double value = Double.parseDouble(txt);
            if (value <= 0)
            {
                factorErrorLbl.setText("Pointfaktor skal være > 0");
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            factorErrorLbl.setText("Pointfaktor skal være et tal");
            return false;
        }

        factorErrorLbl.setText("");
        return true;
    }

    private String safeTrim(TextField field)
    {
        if (field == null || field.getText() == null) return "";
        return field.getText().trim();
    }

    private boolean containsLetter(String s)
    {
        if (s == null) return false;
        String t = s.trim();
        if (t.isEmpty()) return false;

        for (int i = 0; i < t.length(); i++)
        {
            if (Character.isLetter(t.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }
}
