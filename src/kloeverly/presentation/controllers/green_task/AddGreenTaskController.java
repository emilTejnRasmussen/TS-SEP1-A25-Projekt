package kloeverly.presentation.controllers.green_task;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddGreenTaskController implements InitializableController
{
    public TextField nameTxtField;
    public TextField pointsTxtField;
    public TextArea descriptionTxtArea;

    public Label nameErrorLabel;
    public Label pointsErrorLabel;
    public Label descriptionErrorLabel;

    public Button addBtn;
    public Button cancelBtn;

    private DataManager dataManager;

    private boolean nameTouched;
    private boolean pointsTouched;
    private boolean descriptionTouched;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;

        nameTouched = false;
        pointsTouched = false;
        descriptionTouched = false;

        clearErrors();


        if (addBtn != null)
        {
            addBtn.setDisable(false);
            addBtn.setOpacity(1.0);
        }
    }

    public void handleAdd()
    {

        nameTouched = true;
        pointsTouched = true;
        descriptionTouched = true;

        clearErrors();

        boolean valid = validateName() & validatePoints() & validateDescription();
        if (!valid)
        {
            return;
        }

        String name = nameTxtField.getText().trim();
        String description = descriptionTxtArea.getText().trim();
        int value = Integer.parseInt(pointsTxtField.getText().trim());

        GreenTask task = new GreenTask(name, description, value);
        dataManager.addTask(task);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.GREEN_TASKS);
    }

    public void handleCancel()
    {
        ViewManager.showView(Views.GREEN_TASKS);
    }

    public void handleLiveValidate()
    {

        Object focusOwner = null;

        if (nameTxtField != null && nameTxtField.isFocused()) focusOwner = nameTxtField;
        else if (pointsTxtField != null && pointsTxtField.isFocused()) focusOwner = pointsTxtField;
        else if (descriptionTxtArea != null && descriptionTxtArea.isFocused()) focusOwner = descriptionTxtArea;

        if (focusOwner == nameTxtField)
        {
            nameTouched = true;
            nameErrorLabel.setText("");
            validateName();
        }
        else if (focusOwner == pointsTxtField)
        {
            pointsTouched = true;
            pointsErrorLabel.setText("");
            validatePoints();
        }
        else if (focusOwner == descriptionTxtArea)
        {
            descriptionTouched = true;
            descriptionErrorLabel.setText("");
            validateDescription();
        }
    }

    private boolean validateName()
    {
        if (!nameTouched) return true;

        String name = safeTrim(nameTxtField.getText());
        if (!containsLetter(name))
        {
            nameErrorLabel.setText("Navn skal indeholde bogstaver");
            return false;
        }

        nameErrorLabel.setText("");
        return true;
    }

    private boolean validatePoints()
    {
        if (!pointsTouched) return true;

        String points = safeTrim(pointsTxtField.getText());
        if (!isInteger(points))
        {
            pointsErrorLabel.setText("Point skal være et tal");
            return false;
        }

        pointsErrorLabel.setText("");
        return true;
    }

    private boolean validateDescription()
    {
        if (!descriptionTouched) return true;

        String desc = safeTrim(descriptionTxtArea.getText());
        if (!containsLetter(desc))
        {
            descriptionErrorLabel.setText("Beskrivelse skal indeholde bogstaver");
            return false;
        }

        descriptionErrorLabel.setText("");
        return true;
    }

    private void clearErrors()
    {
        if (nameErrorLabel != null) nameErrorLabel.setText("");
        if (pointsErrorLabel != null) pointsErrorLabel.setText("");
        if (descriptionErrorLabel != null) descriptionErrorLabel.setText("");
    }

    private String safeTrim(String s)
    {
        return s == null ? "" : s.trim();
    }

    private boolean isInteger(String s)
    {
        String t = safeTrim(s);
        if (t.isEmpty()) return false;

        for (int i = 0; i < t.length(); i++)
        {
            if (!Character.isDigit(t.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    private boolean containsLetter(String s)
    {
        String t = safeTrim(s);
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
