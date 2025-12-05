package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class CreateGreenTaskController implements InitializableController
{
    @FXML
    private TextField nameTextField;          // fx:id fra FXML

    @FXML
    private TextField valueTextField;         // fx:id fra FXML

    @FXML
    private TextArea descriptionTextArea;     // fx:id fra FXML

    @FXML
    private Label statusLabel;                // fx:id fra FXML

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        statusLabel.setText(""); // Ingen fejl ved start
    }

    @FXML
    private void handleCreate()
    {
        // Ryd tidligere besked
        statusLabel.setText("");

        String name = nameTextField.getText().trim();
        String valueText = valueTextField.getText().trim();
        String description = descriptionTextArea.getText().trim();

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        // Titel
        if (name.isEmpty())
        {
            errorMsg.append("Titel må ikke være tom.\n");
            hasError = true;
        }

        // Værdi
        int value = 0;
        if (valueText.isEmpty())
        {
            errorMsg.append("Værdi må ikke være tom.\n");
            hasError = true;
        }
        else
        {
            try
            {
                value = Integer.parseInt(valueText);
                if (value <= 0)
                {
                    errorMsg.append("Værdi skal være et positivt tal.\n");
                    hasError = true;
                }
            }
            catch (NumberFormatException e)
            {
                errorMsg.append("Værdi skal være et helt tal.\n");
                hasError = true;
            }
        }

        // Beskrivelse
        if (description.isEmpty())
        {
            errorMsg.append("Beskrivelse må ikke være tom.\n");
            hasError = true;
        }

        if (hasError)
        {
            statusLabel.setText(errorMsg.toString().trim());
            return;
        }

        // Opret og gem den grønne opgave
        Task task = new GreenTask(name, description, value);
        dataManager.addTask(task);

        // Vis succesbesked (UC-02 trin 5)
        statusLabel.setText("Den grønne opgave er oprettet.");

        // Ryd felterne efter succes
        nameTextField.clear();
        valueTextField.clear();
        descriptionTextArea.clear();
        System.out.println("CreateGreenTask loaded!");
    }

    @FXML
    private void handleCancel()
    {
        // Tilbage til hovedview – justér hvis I har et specifikt GREEN_TASKS-view
        ViewManager.showView(Views.MAIN);
    }
}
