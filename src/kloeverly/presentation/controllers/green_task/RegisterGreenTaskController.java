package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Resident;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class RegisterGreenTaskController implements InitializableController, AcceptsStringArgument
{
    public Label registerErrorLbl;
    public ComboBox<Resident> residentComboBox;

    public Label nameLbl;
    public Label valueLbl;
    public Label descriptionLbl;

    private DataManager dataManager;
    private int id;
    private GreenTask selectedTask;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        registerErrorLbl.setText("");
        residentComboBox.getItems().setAll(dataManager.getAllResidents());
    }

    @Override
    public void setArgument(String argument)
    {
        registerErrorLbl.setText("");

        try
        {
            id = Integer.parseInt(argument);
        }
        catch (NumberFormatException e)
        {
            selectedTask = null;
            registerErrorLbl.setText("Kunne ikke finde grøn opgave.");
            return;
        }

        Task task = dataManager.getTaskById(id);
        if (!(task instanceof GreenTask))
        {
            selectedTask = null;
            registerErrorLbl.setText("Kunne ikke finde grøn opgave.");
            return;
        }

        selectedTask = (GreenTask) task;

        nameLbl.setText(selectedTask.getName());
        valueLbl.setText(String.valueOf(selectedTask.getValue()));
        descriptionLbl.setText(selectedTask.getDescription());
    }

    public void handleRegister()
    {
        registerErrorLbl.setText("");

        Resident byResident = residentComboBox.getSelectionModel().getSelectedItem();
        if (byResident == null)
        {
            registerErrorLbl.setText("Vælg venligst en beboer.");
            return;
        }

        if (selectedTask == null)
        {
            registerErrorLbl.setText("Kunne ikke finde grøn opgave.");
            return;
        }

        dataManager.completeTask(id, byResident);

        dataManager.addPointsToClimateScore(selectedTask.getValue());

        ViewManager.updateExternalView();
        ViewManager.showView(Views.GREEN_TASKS);
    }

    public void handleCancel()
    {
        ViewManager.showView(Views.GREEN_TASKS);
    }
}
