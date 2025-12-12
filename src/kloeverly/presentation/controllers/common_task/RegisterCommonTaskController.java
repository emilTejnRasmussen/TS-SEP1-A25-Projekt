package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import kloeverly.domain.Resident;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.utility.UtilityMethods;

public class RegisterCommonTaskController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Spinner<Integer> amountSpinner;
    @FXML
    private Label amountErrorLbl;
    @FXML
    private Label registerErrorLbl;
    @FXML
    private ComboBox<Resident> residentComboBox;
    @FXML
    private Label nameLbl;
    @FXML
    private Label valueLbl;
    @FXML
    private Label descriptionLbl;

    private DataManager dataManager;
    private int id;
    private Task selectedTask;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        loadResidents();
        UtilityMethods.createAmountSpinner(amountSpinner);
    }

    public void handleRegister()
    {
        Resident byResident = residentComboBox.getSelectionModel().getSelectedItem();
        if (byResident == null)
        {
            registerErrorLbl.setText("Vælg venligst en beboer.");
            return;
        }

        for (int i = 0; i < amountSpinner.getValue(); i++)
        {
            dataManager.completeTask(this.id, byResident);
        }

        ViewManager.updateExternalView();
        ViewManager.showView(Views.COMMON_TASKS, null, selectedTask.formatTaskCompleted(byResident));
    }

    public void handleCancel()
    {
        ViewManager.showView(Views.COMMON_TASKS);
    }

    private void loadResidents()
    {
        this.residentComboBox.getItems().addAll(dataManager.getAllResidents());
    }

    @Override
    public void setArgument(String argument)
    {
        this.id = Integer.parseInt(argument);
        this.selectedTask = dataManager.getTaskById(id);

        this.nameLbl.setText(this.selectedTask.getName());
        this.valueLbl.setText(this.selectedTask.getValue() + "");
        this.descriptionLbl.setText(this.selectedTask.getDescription());
    }
}
