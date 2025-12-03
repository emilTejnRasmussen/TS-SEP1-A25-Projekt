package kloeverly.presentation.controllers.common_task;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;

public class AddCommonTaskTestController implements InitializableController
{
    @FXML
    private Label titelErrorLbl;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField valueTextField;
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
        System.out.println("Handle add");

    }

    public void handleCancel()
    {
        System.out.println("Handle cancel");
    }
}
