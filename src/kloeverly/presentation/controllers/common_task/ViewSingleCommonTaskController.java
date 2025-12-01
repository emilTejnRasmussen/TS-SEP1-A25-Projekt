package kloeverly.presentation.controllers.common_task;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kloeverly.domain.CommonTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewSingleCommonTaskController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Label mainTitleLbl;
    @FXML
    private Label idLbl;
    @FXML
    private Label titleLbl;
    @FXML
    private Label valueLbl;
    @FXML
    private Label descriptionLbl;

    private DataManager dataManager;
    private int id;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    public void handleUpdate()
    {
        System.out.println("Not Implemented");
    }

    public void handleGoBack()
    {
        ViewManager.showView(Views.COMMON_TASKS);
    }

    @Override
    public void setArgument(String argument)
    {
        this.id = Integer.parseInt(argument);
        CommonTask task = (CommonTask) dataManager.getTaskById(this.id);

        mainTitleLbl.setText(task.getName());
        idLbl.setText(task.getId() + "");
        titleLbl.setText(task.getName());
        valueLbl.setText(task.getValue() + "");
        descriptionLbl.setText(task.getDescription());
    }
}
