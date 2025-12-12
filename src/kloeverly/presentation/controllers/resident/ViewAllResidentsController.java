package kloeverly.presentation.controllers.resident;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewAllResidentsController implements InitializableController
{
    private DataManager dataManager;

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

    @FXML
    private Label errorLabel;

    private final ObservableList<Resident> residents = FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        errorLabel.setText("");

        configureColumns();
        loadResidents();
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
        errorLabel.setText("");
        ViewManager.showView(Views.ADD_RESIDENT);
    }

    @FXML
    private void handleViewDetails()
    {
        errorLabel.setText("");

        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            errorLabel.setText("Vælg en beboer i listen.");
            return;
        }

        ViewManager.showView(Views.VIEW_SINGLE_RESIDENT, String.valueOf(selected.getId()));
    }

    @FXML
    private void handleUpdate()
    {
        errorLabel.setText("");

        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            errorLabel.setText("Vælg en beboer i listen.");
            return;
        }

        ViewManager.showView(Views.UPDATE_RESIDENT, String.valueOf(selected.getId()));
    }

    @FXML
    private void handleDelete()
    {
        errorLabel.setText("");

        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            errorLabel.setText("Vælg en beboer i listen.");
            return;
        }

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
        errorLabel.setText("");
        searchTxtField.clear();
        residentTable.setItems(residents);
    }
}
