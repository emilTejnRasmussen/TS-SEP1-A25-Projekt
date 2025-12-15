package kloeverly.presentation.controllers.resident;

import javafx.application.Platform;
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

import java.util.Optional;

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

    @FXML private TableView<Resident> allResidentsTable;
    @FXML private TableColumn<Resident, Number> idCol;
    @FXML private TableColumn<Resident, String> nameCol;
    @FXML private TableColumn<Resident, Number> pointFactorCol;
    @FXML private TableColumn<Resident, Number> pointsCol;

    @FXML private Button addBtn;
    @FXML private Button detailsBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;

    private DataManager dataManager;
    private final ObservableList<Resident> residents = FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointFactorCol.setCellValueFactory(new PropertyValueFactory<>("pointFactor"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        loadResidents();
        UtilityMethods.buttonListener(residentTable, detailsBtn, updateBtn, deleteBtn);
    }

        // Tilføj er altid aktiv
        setButtonState(addBtn, true);

        // De andre kræver selection
        setSelectionButtonsEnabled(false);
        allResidentsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) ->
                        setSelectionButtonsEnabled(newVal != null));
    }

    public void handleAdd()
    {
        ViewManager.showView(Views.ADD_RESIDENT);
    }

    public void handleViewDetails()
    {

        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.VIEW_SINGLE_RESIDENT, String.valueOf(selected.getId()));
    }

    public void handleUpdate()
    {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.UPDATE_RESIDENT, String.valueOf(selected.getId()));
    }

    public void handleDelete()
    {
        Resident selected = residentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft sletning");
        confirm.setHeaderText("Slet beboer");
        confirm.setContentText("Er du sikker på, at du vil slette: \"" + selected.getName() + "\"?");
        confirm.getButtonTypes().setAll(deleteType, cancelType);

        Optional<ButtonType> result = confirm.showAndWait();
        Platform.runLater(() ->
                confirm.getDialogPane().lookupButton(cancelType).requestFocus());

        if (result.isPresent() && result.get() == deleteType)
        {
            dataManager.deleteResident(selected);
            loadResidents();
            setSelectionButtonsEnabled(false);

            showConfirmation("Beboeren \"" + selected.getName() + "\" er slettet.");
        }
    }

    public void handleSearch()
    {
        String query = searchTxtField.getText();
        if (query == null) query = "";
        query = query.trim().toLowerCase();

        if (query.isEmpty())
        {
            allResidentsTable.setItems(allResidents);
            return;
        }

        ObservableList<Resident> filtered = FXCollections.observableArrayList();
        for (Resident r : allResidents)
        {
            if (r.getName() != null && r.getName().toLowerCase().contains(query))
            {
                filtered.add(r);
            }
        }
        allResidentsTable.setItems(filtered);
    }

    public void handleClearSearchBar()
    {
        searchTxtField.clear();
        allResidentsTable.setItems(allResidents);
    }

    private void loadResidents()
    {
        allResidents.setAll(dataManager.getAllResidents());
        allResidentsTable.setItems(allResidents);
    }

    private void setSelectionButtonsEnabled(boolean enabled)
    {
        setButtonState(detailsBtn, enabled);
        setButtonState(updateBtn, enabled);
        setButtonState(deleteBtn, enabled);
    }

    private void setButtonState(Button btn, boolean enabled)
    {
        if (btn == null) return;
        btn.setDisable(!enabled);
        btn.setOpacity(enabled ? 1.0 : 0.5);
    }

    private void showConfirmation(String message)
    {
        if (message == null || message.isBlank()) return;

        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.setTitle("Bekræftelse");
        alert.setHeaderText(null);
        alert.show();
    }
}
