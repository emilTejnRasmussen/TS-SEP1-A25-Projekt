package kloeverly.presentation.controllers.green_task;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class UpdateGreenTaskController implements InitializableController, AcceptsStringArgument
{
    public TextField nameField;
    public TextArea descriptionField;
    public TextField pointsField;

    public Label generalErrorLbl;
    public Label nameErrorLbl;
    public Label pointsErrorLbl;
    public Label descriptionErrorLbl;

    public Button saveButton;

    private DataManager dataManager;
    private Integer greenTaskId;
    private GreenTask currentTask;

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


        if (saveButton != null)
        {
            saveButton.setDisable(false);
            saveButton.setOpacity(1.0);
        }

        loadTaskIfReady();
    }

    @Override
    public void setArgument(String argument)
    {
        clearErrors();

        try
        {
            greenTaskId = Integer.parseInt(argument);
        }
        catch (NumberFormatException e)
        {
            greenTaskId = null;
        }

        loadTaskIfReady();
    }

    public void onInputChanged()
    {

        if (nameField != null && nameField.isFocused())
        {
            nameTouched = true;
            nameErrorLbl.setText("");
            validateName();
        }
        else if (pointsField != null && pointsField.isFocused())
        {
            pointsTouched = true;
            pointsErrorLbl.setText("");
            validatePoints();
        }
        else if (descriptionField != null && descriptionField.isFocused())
        {
            descriptionTouched = true;
            descriptionErrorLbl.setText("");
            validateDescription();
        }
    }

    public void handleUpdate()
    {
        clearErrors();

        if (currentTask == null)
        {
            generalErrorLbl.setText("Ingen opgave valgt.");
            return;
        }


        nameTouched = true;
        pointsTouched = true;
        descriptionTouched = true;

        boolean ok = validateName() & validatePoints() & validateDescription();
        if (!ok)
        {
            return;
        }

        String name = safeTrim(nameField);
        String description = safeTrim(descriptionField);
        int points = Integer.parseInt(safeTrim(pointsField));

        currentTask.setName(name);
        currentTask.setDescription(description);
        currentTask.setValue(points);

        dataManager.updateTask(currentTask);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.GREEN_TASKS);
    }

    public void handleGoBack()
    {
        ViewManager.showView(Views.GREEN_TASKS);
    }

    private void clearErrors()
    {
        if (generalErrorLbl != null) generalErrorLbl.setText("");
        if (nameErrorLbl != null) nameErrorLbl.setText("");
        if (pointsErrorLbl != null) pointsErrorLbl.setText("");
        if (descriptionErrorLbl != null) descriptionErrorLbl.setText("");
    }

    private void loadTaskIfReady()
    {
        if (dataManager == null || greenTaskId == null)
        {
            return;
        }

        Task task;
        try
        {
            task = dataManager.getTaskById(greenTaskId);
        }
        catch (RuntimeException e)
        {
            generalErrorLbl.setText("Kunne ikke finde grøn opgave.");
            currentTask = null;
            return;
        }

        if (!(task instanceof GreenTask))
        {
            generalErrorLbl.setText("Kunne ikke finde grøn opgave.");
            currentTask = null;
            return;
        }

        currentTask = (GreenTask) task;

        nameField.setText(currentTask.getName());
        descriptionField.setText(currentTask.getDescription());
        pointsField.setText(String.valueOf(currentTask.getValue()));
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

    private boolean validatePoints()
    {
        if (!pointsTouched) return true;

        String txt = safeTrim(pointsField);
        if (!isInteger(txt))
        {
            pointsErrorLbl.setText("Point skal være et tal");
            return false;
        }

        pointsErrorLbl.setText("");
        return true;
    }

    private boolean validateDescription()
    {
        if (!descriptionTouched) return true;

        String desc = safeTrim(descriptionField);
        if (!containsLetter(desc))
        {
            descriptionErrorLbl.setText("Beskrivelse skal indeholde bogstaver");
            return false;
        }

        descriptionErrorLbl.setText("");
        return true;
    }

    private String safeTrim(TextField field)
    {
        if (field == null || field.getText() == null) return "";
        return field.getText().trim();
    }

    private String safeTrim(TextArea area)
    {
        if (area == null || area.getText() == null) return "";
        return area.getText().trim();
    }

    private boolean isInteger(String s)
    {
        if (s == null) return false;
        String t = s.trim();
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
