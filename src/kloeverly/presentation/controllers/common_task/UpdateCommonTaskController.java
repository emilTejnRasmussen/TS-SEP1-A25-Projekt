package kloeverly.presentation.controllers.common_task;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.domain.CommonTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;

public class UpdateCommonTaskController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Label mainTitleLbl;
    @FXML
    private Label idLbl;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField valueTextField;
    @FXML
    private TextArea descriptionTextField;

    private DataManager dataManager;
    private int id;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    public void handleSave()
    {
    }

    public void handleCancel()
    {
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
        descriptionTextField.setText(task.getDescription());
    }
}
