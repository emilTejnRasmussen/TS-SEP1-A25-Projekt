package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddGreenTaskController implements InitializableController
{
    @FXML
    private TableView<GreenTask> greenTaskTable;
    @FXML
    private TableColumn<GreenTask, Integer> idColumn;
    @FXML
    private TableColumn<GreenTask, String> nameColumn;
    @FXML
    private TableColumn<GreenTask, String> descriptionColumn;
    @FXML
    private TableColumn<GreenTask, Integer> valueColumn;

    @FXML
    private Label poolValueLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Label statusLabel;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        System.out.println("AddGreenTask loaded!");
        this.dataManager = dataManager;

        // Sæt kolonnerne op
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Fyld tabellen med alle grønne opgaver
        greenTaskTable.getItems().setAll(dataManager.getAllGreenTasks());

        // Opdatér fællespuljen
        updatePoolLabel();

        statusLabel.setText("");
    }

    private void updatePoolLabel()
    {
        int points = dataManager.getClimateScore().getTotalGreenPoints();
        poolValueLabel.setText(points + " point");
    }

    @FXML
    private void handleConfirm()
    {
        statusLabel.setText("");

        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            statusLabel.setText("Vælg en grøn opgave i tabellen.");
            return;
        }

        // Læg opgavens værdi til fællespuljen
        dataManager.addPointsToClimateScore(selected.getValue());


        updatePoolLabel();

        statusLabel.setText("Opgaven '" + selected.getName() + "' er registreret.");


    }

    @FXML
    private void handleCancel()
    {
        // Tilbage til hovedsiden
        ViewManager.showView(Views.MAIN);
    }


}
