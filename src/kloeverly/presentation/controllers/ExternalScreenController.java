package kloeverly.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.CommonTask;
import kloeverly.domain.ExchangeTask;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;

import java.util.List;

public class ExternalScreenController implements InitializableController
{
    @FXML
    private TableView<Resident> residentTable;
    @FXML
    private TableColumn<Resident, String> residentNameCol;
    @FXML
    private TableColumn<Resident, Integer> residentPointCol;
    @FXML
    private TableView<ExchangeTask> exchangeTable;
    @FXML
    private TableColumn<ExchangeTask, String> exchangeNameCol;
    @FXML
    private TableColumn<ExchangeTask, Integer> exchangeValueCol;
    @FXML
    private TableView<CommonTask> commonTable;
    @FXML
    private TableColumn<CommonTask, String> commonNameCol;
    @FXML
    private TableColumn<CommonTask, Integer> commonValueCol;
    @FXML
    private TableView<GreenTask> greenTable;
    @FXML
    private TableColumn<GreenTask, String> greenNameCol;
    @FXML
    private TableColumn<GreenTask, Integer> greenValueCol;

    private DataManager dataManager;
    private List<TableView<?>> tables;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        this.tables = List.of(residentTable, exchangeTable, commonTable, greenTable);
        loadData();
    }

    public void refresh() {
        loadData();
    }

    private void loadData()
    {
        tables.forEach(t -> t.getItems().clear());

        residentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        residentPointCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        exchangeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        exchangeValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        commonNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        commonValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        greenNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        greenValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));


        residentTable.getItems().addAll(dataManager.getAllResidents());
        exchangeTable.getItems().addAll(dataManager.getAllExchangeTasks());
        commonTable.getItems().addAll(dataManager.getAllCommonTasks());
        greenTable.getItems().addAll(dataManager.getAllGreenTasks());
    }
}
