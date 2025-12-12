package kloeverly.presentation.controllers.resident;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.utility.UtilityMethods;

public class ViewAllResidentsController implements InitializableController
{
    @FXML
    private Button detailsBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;

    @FXML
    private TextField searchTxtField;

    @FXML
    private TableView<Resident> residentTable;

    @FXML
    private TableColumn<Resident, Number> idColumn;

    @FXML
    private TableColumn<Resident, String> nameColumn;

    @FXML
    private TableColumn<Resident, Number> pointFactorColumn;

    @FXML
    private TableColumn<Resident, Number> pointsColumn;

    private DataManager dataManager;
    private final ObservableList<Resident> residents = FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;

        configureColumns();
        loadResidents();
        UtilityMethods.buttonListener(residentTable, detailsBtn, updateBtn, deleteBtn);
    }

    private void configureColumns()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointFactorColumn.setCellValueFactory(new PropertyValueFactory<>("pointFactor"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    private void loadResidents()
    {
        residents.setAll(dataManager.getAllResidents());
        residentTable.setItems(residents);
    }

    // --- Template handlers ---

    @FXML
    private void handleAdd()
    {
        ViewManager.showView(Views.ADD_RESIDENT);
    }

    @FXML
    private void handleViewDetails()
    {

        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.VIEW_SINGLE_RESIDENT, String.valueOf(selected.getId()));
    }

    @FXML
    private void handleUpdate()
    {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.UPDATE_RESIDENT, String.valueOf(selected.getId()));
    }

    @FXML
    private void handleDelete()
    {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        dataManager.deleteResident(selected);

        ViewManager.updateExternalView();
        loadResidents();
    }

    @FXML
    private void handleSearch()
    {
        String query = searchTxtField.getText();
        if (query == null) query = "";
        query = query.trim().toLowerCase();

        if (query.isEmpty())
        {
            residentTable.setItems(residents);
            return;
        }

        ObservableList<Resident> filtered = FXCollections.observableArrayList();
        for (Resident r : residents)
        {
            if (r.getName() != null && r.getName().toLowerCase().contains(query))
            {
                filtered.add(r);
            }
        }

        residentTable.setItems(filtered);
    }

    @FXML
    private void handleClearSearchBar()
    {
        searchTxtField.clear();
        residentTable.setItems(residents);
    }
}
