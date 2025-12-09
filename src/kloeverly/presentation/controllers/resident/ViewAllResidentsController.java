package kloeverly.presentation.controllers.resident;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import javafx.application.Platform;

public class ViewAllResidentsController implements InitializableController {

    @FXML
    private TextField searchTxtField;

    @FXML
    private TableView<Resident> residentTableView;

    @FXML
    private TableColumn<Resident, Integer> idColumn;

    @FXML
    private TableColumn<Resident, String> nameColumn;

    @FXML
    private TableColumn<Resident, Double> pointFactorColumn;

    @FXML
    private TableColumn<Resident, Integer> pointsColumn;

    private DataManager dataManager;

    // Ufiltreret liste over alle beboere
    private final ObservableList<Resident> allResidents =
            FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;

        // Hent beboere
        allResidents.setAll(dataManager.getAllResidents());

        // Map kolonner
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointFactorColumn.setCellValueFactory(new PropertyValueFactory<>("pointFactor"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        // Brug runLater så UI rent faktisk viser data
        Platform.runLater(() -> {
            residentTableView.setItems(allResidents);
            residentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        });
    }


    // Søg mens man skriver (koblet til onKeyTyped="#handleSearch")
    @FXML
    private void handleSearch() {
        String query = searchTxtField.getText().toLowerCase().trim();

        if (query.isEmpty()) {
            residentTableView.setItems(allResidents);
            return;
        }

        ObservableList<Resident> filtered = FXCollections.observableArrayList();

        for (Resident r : allResidents) {
            if (r.getName().toLowerCase().contains(query)) {
                filtered.add(r);
            }
        }

        residentTableView.setItems(filtered);
    }

    @FXML
    private void handleClearSearchBar() {
        searchTxtField.clear();
        residentTableView.setItems(allResidents);
    }

    // ------------ Knapperne nederst ----------------

    // "Tilføj" -> gå til Opret-beboer-vinduet (AddResident.fxml)
    @FXML
    private void handleAdd() {
        ViewManager.showView(Views.ADD_RESIDENT);
    }

    // "Detaljer" -> lige nu samme som Opdater (kan senere ændres til et separat detail-view)
    @FXML
    private void handleViewDetails() {
        Resident selected = getSelectedResident();
        if (selected == null) return;

        String idString = String.valueOf(selected.getId());
        ViewManager.showView(Views.VIEW_RESIDENT, idString);

    }

    // "Opdater" -> opdater valgt beboer
    @FXML
    private void handleUpdate() {
        Resident selected = getSelectedResident();
        if (selected == null) return;

        String idString = String.valueOf(selected.getId());
        ViewManager.showView(Views.UPDATE_RESIDENT, idString);
    }

    // "Slet" -> fjern beboer
    @FXML
    private void handleDelete() {
        Resident selected = getSelectedResident();
        if (selected == null) return;

        dataManager.deleteResident(selected);
        allResidents.remove(selected);
        residentTableView.setItems(allResidents);
    }

    private Resident getSelectedResident() {
        return residentTableView.getSelectionModel().getSelectedItem();
    }
}
