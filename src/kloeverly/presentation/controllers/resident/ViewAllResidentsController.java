package kloeverly.presentation.controllers.resident;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewAllResidentsController implements InitializableController {

    // --- Data-adgang (injiceres af ControllerConfigurator) ---
    private DataManager dataManager;

    // --- FXML-felter (match fx:id i ViewAllResidents.fxml) ---
    @FXML
    private TextField searchField;

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

    // Liste med alle beboere (grundliste)
    private final ObservableList<Resident> residents =
            FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
        configureColumns();
        loadResidents();
    }

    // Sæt op hvordan kolonnerne læser værdier fra Resident
    private void configureColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointFactorColumn.setCellValueFactory(new PropertyValueFactory<>("pointFactor"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    // Hent alle beboere fra DataManager
    private void loadResidents() {
        residents.setAll(dataManager.getAllResidents());
        residentTable.setItems(residents);
    }

    // --- Knap-handlers ---

    @FXML
    private void handleAddResident() {
        ViewManager.showView(Views.ADD_RESIDENT);
    }

    @FXML
    private void handleViewResident() {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            // her kunne man evt. vise en Alert, men det er ikke et krav
            return;
        }

        ViewManager.showView(
                Views.VIEW_SINGLE_RESIDENT,
                String.valueOf(selected.getId())
        );
    }

    @FXML
    private void handleUpdateResident() {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        ViewManager.showView(
                Views.UPDATE_RESIDENT,
                String.valueOf(selected.getId())
        );
    }

    @FXML
    private void handleDeleteResident() {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        dataManager.deleteResident(selected);
        loadResidents();  // opdatér tabellen efter sletning
    }

    // --- Søgning i beboer-listen ---

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        if (query == null) {
            query = "";
        }
        query = query.trim().toLowerCase();

        if (query.isEmpty()) {
            residentTable.setItems(residents);
            return;
        }

        ObservableList<Resident> filtered = FXCollections.observableArrayList();
        for (Resident r : residents) {
            if (r.getName().toLowerCase().contains(query)) {
                filtered.add(r);
            }
        }
        residentTable.setItems(filtered);
    }

    @FXML
    private void handleClearSearch() {
        searchField.clear();
        residentTable.setItems(residents);
    }
}
