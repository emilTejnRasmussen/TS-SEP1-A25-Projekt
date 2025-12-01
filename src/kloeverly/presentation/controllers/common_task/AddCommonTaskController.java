package kloeverly.presentation.controllers.common_task;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.persistence.DataManager;

public class AddCommonTaskController
{
    @FXML
    private Label titelErrorLbl;
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

    public void handleAdd(ActionEvent event)
    {
    }

    public void handleCancel(ActionEvent event)
    {
    }
}
