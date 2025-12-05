package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import java.util.List;
import java.util.Optional;

public class ViewAllResidentsController implements InitializableController
{
    @FXML
    private TableView<Resident> residentsTable;
    @FXML
    private TableColumn<Resident, Integer> idCol;
    @FXML
    private TableColumn<Resident, String> nameCol;
    @FXML
    private TableColumn<Resident, Double> factorCol;
    @FXML
    private TableColumn<Resident, Integer> pointsCol;
    @FXML
    private TextField searchTxtField;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        showTable(dataManager.getAllResidents());
    }

    // --- Søgning ---

    @FXML
    public void handleSearch()
    {
        String search = searchTxtField.getText().toLowerCase();

        List<Resident> filtered = dataManager.getAllResidents().stream()
                .filter(r -> r.getName().toLowerCase().contains(search))
                .toList();

        showTable(filtered);
    }

    @FXML
    public void handleClearSearchBar()
    {
        searchTxtField.setText("");
        showTable(dataManager.getAllResidents());
    }

    // --- Knapper ---

    @FXML
    public void handleAdd()
    {
        ViewManager.showView(Views.ADD_RESIDENT);
    }

    @FXML
    public void handleUpdate()
    {
        Resident selected = residentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.UPDATE_RESIDENT, selected.getId() + "");
    }

    @FXML
    public void handleDelete()
    {
        Resident selected = residentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft sletning");
        confirm.setHeaderText("Slet beboer");
        confirm.setContentText(
                "Er du sikker på, at du vil slette: '" + selected.getName() + "'?"
        );

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            dataManager.deleteResident(selected);
            showTable(dataManager.getAllResidents());
        }
    }

    // --- Hjælpemetode ---

    private void showTable(List<Resident> residents)
    {
        residentsTable.getItems().clear();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        factorCol.setCellValueFactory(new PropertyValueFactory<>("pointFactor"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        residentsTable.getItems().addAll(residents);
    }
}
