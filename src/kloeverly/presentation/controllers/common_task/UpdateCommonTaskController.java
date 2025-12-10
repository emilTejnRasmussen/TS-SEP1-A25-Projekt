package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.domain.CommonTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class UpdateCommonTaskController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Label titleErrorLbl;
    @FXML
    private Label valueErrorLbl;
    @FXML
    private Label descriptionErrorLbl;
    @FXML
    private Label mainTitleLbl;
    @FXML
    private Label idLbl;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField valueTextField;
    @FXML
    private TextArea descriptionTextArea;

    private DataManager dataManager;
    private int id;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    public void handleSave()
    {
        if (!validInput()) return;
        String name = titleTextField.getText().trim();
        String description = descriptionTextArea.getText().trim();
        int value = Integer.parseInt(valueTextField.getText().trim());

        Task task = new CommonTask(name, description, value);
        task.setId(this.id);
        this.dataManager.updateTask(task);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.COMMON_TASK, this.id + "");

    }

    private boolean validInput()
    {
        titleErrorLbl.setText("");
        valueErrorLbl.setText("");
        descriptionErrorLbl.setText("");

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

    public void handleCancel()
    {
        ViewManager.showView(Views.COMMON_TASK, this.id + "");
    }

    @Override
    public void setArgument(String argument)
    {
        this.id = Integer.parseInt(argument);
        CommonTask task = (CommonTask) dataManager.getTaskById(this.id);

        mainTitleLbl.setText(task.getName());
        idLbl.setText(task.getId() + "");
        titleTextField.setText(task.getName());
        valueTextField.setText(task.getValue() + "");
        descriptionTextArea.setText(task.getDescription());
    }
}
