package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.domain.CommonTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddCommonTaskController implements InitializableController
{
    @FXML
    private Label titleErrorLbl;
    @FXML
    private TextField titleTextField;
    @FXML
    private Label valueErrorLbl;
    @FXML
    private TextField valueTextField;
    @FXML
    private Label descriptionErrorLbl;
    @FXML
    private TextArea descriptionTextArea;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    public void handleAdd()
    {
        if (!validateInput()) return;

        String name = titleTextField.getText().trim();
        int value = Integer.parseInt(valueTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();
        Task task = new CommonTask(name, description, value);
        dataManager.addTask(task);

        ViewManager.showView(Views.COMMON_TASKS);
    }

    public void handleCancel()
    {
        ViewManager.showView(Views.COMMON_TASKS);
    }

    private boolean validateInput()
    {
        boolean allInputIsValid = true;
        if (titleTextField.getText().isEmpty()){
            titleErrorLbl.setText("Titel må ikke være tom");
            allInputIsValid = false;
        }
        if (valueTextField.getText().isEmpty()){
            valueErrorLbl.setText("Værdi må ikke være tom");
            allInputIsValid = false;
        } else {
            try
            {
                Integer.parseInt(valueTextField.getText().trim());
            } catch (NumberFormatException e)
            {
                valueErrorLbl.setText("Værdi skal være et tal");
                allInputIsValid = false;
            }
        }
        if (descriptionTextArea.getText().isEmpty()){
            descriptionErrorLbl.setText("Beskrivelse må ikke være tom");
            allInputIsValid = false;
        }

        return allInputIsValid;
    }
}
