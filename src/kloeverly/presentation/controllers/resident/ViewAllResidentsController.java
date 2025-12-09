package kloeverly.presentation.controllers.resident;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

/**
 * Controller for ViewAllResidents.fxml
 */
public class ViewAllResidentsController implements InitializableController {

    // --- Data-adgang (injiceres af ControllerConfigurator) ---
    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;

        // Hent rigtige beboere fra data-laget
        residents.setAll(this.dataManager.getAllResidents());
        // FilteredList + TableView er allerede sat op i initialize(), så de opdateres automatisk
    }

    // --- FXML-felter (samme navne som fx:id i FXML) ---

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Resident> residentTable;

    @FXML
    private TableColumn<Resident, Integer> idColumn;

    @FXML
    private TableColumn<Resident, String> nameColumn;

    @FXML
    private TableColumn<Resident, Double> pointFactorColumn;

    @FXML
    private TableColumn<Resident, Integer> pointsColumn;

    // --- Data til tabellen ---

    private final ObservableList<Resident> residents = FXCollections.observableArrayList();
    private final FilteredList<Resident> filteredResidents =
            new FilteredList<>(residents, r -> true);

    /**
     * JavaFX initialize – kaldes automatisk efter FXML er loadet.
     */
    @FXML
    private void initialize() {
        // Bind kolonner til properties i Resident (getId, getName, getPointFactor, getPoints)
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointFactorColumn.setCellValueFactory(new PropertyValueFactory<>("pointFactor"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        residentTable.setItems(filteredResidents);
    }

    // --- Knapper nederst ---

    @FXML
    private void handleAddResident() {
        ViewManager.showView(Views.ADD_RESIDENT);
    }

    @FXML
    private void handleViewResident() {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("Vælg en beboer før du kan se detaljer.");
            return;
        }

        // Senere kan I sende ID videre:
        // ViewManager.showView(Views.VIEW_SINGLE_RESIDENT, String.valueOf(selected.getId()));
        ViewManager.showView(Views.VIEW_SINGLE_RESIDENT);
    }

    @FXML
    private void handleUpdateResident() {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("Vælg en beboer før du kan redigere.");
            return;
        }

        // ViewManager.showView(Views.UPDATE_RESIDENT, String.valueOf(selected.getId()));
        ViewManager.showView(Views.UPDATE_RESIDENT);
    }

    @FXML
    private void handleDeleteResident() {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("Vælg en beboer før du kan slette.");
            return;
        }

        // Slet i data-lag
        dataManager.deleteResident(selected);

        // Fjern fra listen/tabel
        residents.remove(selected);
    }

    // --- Søgning ---

    /**
     * Kaldes fra TextField: onKeyTyped="#handleSearch"
     */
    @FXML
    private void handleSearch(KeyEvent event) {
        String term = searchField.getText().trim().toLowerCase();

        filteredResidents.setPredicate(resident -> {
            if (term.isEmpty()) return true;

            boolean matchesName = resident.getName() != null &&
                    resident.getName().toLowerCase().contains(term);

            boolean matchesId = String.valueOf(resident.getId()).contains(term);

            return matchesName || matchesId;
        });
    }

    /**
     * Kaldes fra Nulstil-knappen: onAction="#handleClearSearch"
     */
    @FXML
    private void handleClearSearch() {
        searchField.clear();
        filteredResidents.setPredicate(r -> true);
    }
}
