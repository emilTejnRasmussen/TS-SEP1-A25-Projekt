package kloeverly.presentation.controllers.resident;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddResidentController implements InitializableController
{
    public TextField nameTxtField;
    public TextField pointFactorTxtField;

    public Label nameErrorLabel;
    public Label pointFactorErrorLabel;

    public Button addButton;

    private DataManager dataManager;

    private boolean nameTouched;
    private boolean factorTouched;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;

        nameTouched = false;
        factorTouched = false;

        clearErrors();

        if (addButton != null)
        {
            addButton.setDisable(false);
            addButton.setOpacity(1.0);
        }
    }

    public void handleInputChanged()
    {
        if (nameTxtField != null && nameTxtField.isFocused())
        {
            nameTouched = true;
            nameErrorLabel.setText("");
            validateName();
        }
        else if (pointFactorTxtField != null && pointFactorTxtField.isFocused())
        {
            factorTouched = true;
            pointFactorErrorLabel.setText("");
            validateFactor();
        }
    }

    public void handleAdd()
    {
        nameTouched = true;
        factorTouched = true;

        clearErrors();

        boolean ok = validateName() & validateFactor();
        if (!ok)
        {
            return;
        }

        String name = nameTxtField.getText().trim();
        double pointFactor = Double.parseDouble(pointFactorTxtField.getText().trim());

        dataManager.addResident(new Resident(name, pointFactor));
        ViewManager.updateExternalView();
        ViewManager.showView(Views.RESIDENTS);
    }

    public void handleCancel()
    {
        ViewManager.showView(Views.RESIDENTS);
    }

    private void clearErrors()
    {
        if (nameErrorLabel != null) nameErrorLabel.setText("");
        if (pointFactorErrorLabel != null) pointFactorErrorLabel.setText("");
    }

    private boolean validateName()
    {
        if (!nameTouched) return true;

        String name = safeTrim(nameTxtField);
        if (!containsLetter(name))
        {
            nameErrorLabel.setText("Navn skal indeholde bogstaver");
            return false;
        }

        nameErrorLabel.setText("");
        return true;
    }

    private boolean validateFactor()
    {
        if (!factorTouched) return true;

        String txt = safeTrim(pointFactorTxtField);
        if (txt.isEmpty())
        {
            pointFactorErrorLabel.setText("Pointfaktor skal være et tal");
            return false;
        }

        try
        {
            double value = Double.parseDouble(txt);
            if (value <= 0)
            {
                pointFactorErrorLabel.setText("Pointfaktor skal være > 0");
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            pointFactorErrorLabel.setText("Pointfaktor skal være et tal");
            return false;
        }

        pointFactorErrorLabel.setText("");
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
