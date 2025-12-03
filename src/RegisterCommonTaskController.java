import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import kloeverly.domain.CommonTask;
import kloeverly.domain.Resident;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class RegisterCommonTaskController implements InitializableController, AcceptsStringArgument
{
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
    }

    public void handleRegister()
    {
        Resident byResident = residentComboBox.getSelectionModel().getSelectedItem();
        if (byResident == null) return;

        this.selectedTask.completed(byResident);
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
        this.selectedTask = dataManager.getTaskById(this.id);

        this.nameLbl.setText(this.selectedTask.getName());
        this.valueLbl.setText(this.selectedTask.getValue() + "");
        this.descriptionLbl.setText(this.selectedTask.getDescription());
    }
}
