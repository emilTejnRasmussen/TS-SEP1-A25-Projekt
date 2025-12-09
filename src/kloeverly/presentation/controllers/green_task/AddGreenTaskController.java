package kloeverly.presentation.controllers.green_task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddGreenTaskController implements InitializableController {

    @FXML
    private TableView<GreenTask> greenTaskTable;

    @FXML
    private TableColumn<GreenTask, Integer> idColumn;

    @FXML
    private TableColumn<GreenTask, String> titleColumn;

    @FXML
    private TableColumn<GreenTask, String> descriptionColumn;

    @FXML
    private TableColumn<GreenTask, Integer> valueColumn;

    @FXML
    private Label poolValueLabel;   // fx:id skal være poolValueLabel

    @FXML
    private Label statusLabel;      // fx:id statusLabel (bruges bare hvis du vil vise fejltekst)

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;

        // Binder kolonnerne til GreenTask / Task properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));          // Task.getName()
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Fyld tabellen
        ObservableList<GreenTask> tasks =
                FXCollections.observableArrayList(dataManager.getAllGreenTasks());
        greenTaskTable.setItems(tasks);

        // Vis fællespuljens point
        int poolPoints = dataManager.getClimateScore().getPoints();
        poolValueLabel.setText(poolPoints + " point");
    }

    @FXML
    private void handleConfirm() {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fejl", "Du skal vælge en grøn opgave.");
            return;
        }

        dataManager.addPointsToClimateScore(selected.getValue());
        ViewManager.showView(Views.HOME);
    }

    @FXML
    private void handleBack() {
        ViewManager.showView(Views.HOME);
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}

