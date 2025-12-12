package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.utility.UtilityMethods;

public class RegisterGreenTaskController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Label nameLbl;
    @FXML
    private Spinner<Integer> amountSpinner;
    @FXML
    private Label amountErrorLbl;
    @FXML
    private Label valueLbl;
    @FXML
    private Label descriptionLbl;

    private DataManager dataManager;
    private Task task;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        UtilityMethods.createAmountSpinner(amountSpinner);
    }

    public void handleRegister()
    {
        for (int i = 0; i < amountSpinner.getValue(); i++)
        {
            dataManager.addPointsToClimateScore(task.getValue());
        }

        ViewManager.updateExternalView();
        ViewManager.showView(Views.GREEN_TASKS);
    }

    public void handleCancel()
    {
        ViewManager.showView(Views.GREEN_TASKS);
    }

    @Override
    public void setArgument(String argument)
    {
        int id = Integer.parseInt(argument);
        this.task = dataManager.getTaskById(id);

        nameLbl.setText("'" + task.getName() + "'");
        valueLbl.setText(task.getValue() + "");
        descriptionLbl.setText(task.getDescription());
    }
}
