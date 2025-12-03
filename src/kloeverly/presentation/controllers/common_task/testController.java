package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import kloeverly.domain.CommonTask;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;

import javax.xml.crypto.Data;

public class testController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Label mainTitleLbl;
    @FXML
    private Label descriptionLbl;
    @FXML
    private Label mainTitleLbl1;
    @FXML
    private ComboBox<Resident> residentComboBox;
    @FXML
    private Label statusLbl;
    @FXML
    private Label valueLbl;

    private DataManager dataManager;
    private int id;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    public void handleRegisterCompletion()
    {
    }

    public void handleUpdate()
    {
    }

    public void handleGoBack()
    {
    }

    @Override
    public void setArgument(String argument)
    {
        this.id = Integer.parseInt(argument);
        CommonTask task = (CommonTask) dataManager.getTaskById(this.id);

        mainTitleLbl.setText(task.getName());
        valueLbl.setText(task.getValue() + "");
        descriptionLbl.setText(task.getDescription());
    }
}
