package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import kloeverly.domain.CommonTask;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class RegisterCommonTaskController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Label amountErrorLbl;
    @FXML
    private Label amountLbl;
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
    private CommonTask selectedTask;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        loadResidents();
    }

    public void handleRegister()
    {
        Resident byResident = residentComboBox.getSelectionModel().getSelectedItem();

        if (!allInputIsValid(byResident)) return;

        selectedTask.setAmount(selectedTask.getAmount() - 1);
        dataManager.updateTask(selectedTask);
        dataManager.completeTask(this.id, byResident);

        String flashMessage = selectedTask.formatTaskCompleted(byResident);

        if (selectedTask.getAmount() < 1){
            dataManager.deleteTask(selectedTask);
            flashMessage += "\n\n Ingen pladser tilbage, sletter '" + selectedTask.getName() + "'.";
        }

        ViewManager.updateExternalView();
        ViewManager.showView(Views.COMMON_TASKS, null, flashMessage);
    }

    private boolean allInputIsValid(Resident byResident)
    {
        boolean allValid = true;
        if (byResident == null)
        {
            registerErrorLbl.setText("Vælg venligst en beboer.");
            allValid = false;
        }

        if (selectedTask.getAmount() < 1) {
            amountErrorLbl.setText("No more spots available");
            allValid = false;
        }

        return allValid;
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
        this.selectedTask = (CommonTask) dataManager.getTaskById(id);

        nameLbl.setText(selectedTask.getName());
        valueLbl.setText(selectedTask.getValue() + "");
        amountLbl.setText(selectedTask.getAmount() + "");
        descriptionLbl.setText(selectedTask.getDescription());
    }
}
