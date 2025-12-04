package kloeverly.presentation.controllers.exchange_task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import java.util.List;

public class ViewAllExchangeTasksController implements InitializableController
{
  @FXML public TextField searchTextField;
  @FXML public TableView<ExchangeTask> allExchangeTaskTable;
  @FXML public TableColumn<ExchangeTask, String> nameCol;
  @FXML public TableColumn<ExchangeTask, String> providerCol;
  @FXML public TableColumn<ExchangeTask, Integer> valueCol;
  @FXML public TableColumn<ExchangeTask, Integer> amountCol;
  @FXML public Button taskCompleted;
  @FXML public Button viewDetails;
  @FXML public Button deleteTask;

  private DataManager dataManager;

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    showTable(dataManager.getAllExchangeTasks());

    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    providerCol.setCellValueFactory(new PropertyValueFactory<>("provider"));
    valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

    taskCompleted.setDisable(true);
    taskCompleted.setOpacity(0.5);
    viewDetails.setDisable(true);
    viewDetails.setOpacity(0.5);
    deleteTask.setDisable(true);
    deleteTask.setOpacity(0.5);

    allExchangeTaskTable.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          boolean hasSelection = newValue != null;
          taskCompleted.setDisable(!hasSelection);
          taskCompleted.setOpacity(hasSelection ? 1 : 0.5);
          viewDetails.setDisable(!hasSelection);
          viewDetails.setOpacity(hasSelection ? 1 : 0.5);
          deleteTask.setDisable(!hasSelection);
          deleteTask.setOpacity(hasSelection ? 1 : 0.5);
        });
  }

  private void showTable(List<ExchangeTask> tasks)
  {
    allExchangeTaskTable.getItems().setAll(tasks == null ? List.of() : tasks);
  }

  public void handleSearch()
  {
    String searchInput = searchTextField.getText().toLowerCase().trim();

    if (searchInput.isEmpty())
    {
      showTable(dataManager.getAllExchangeTasks());
      return;
    }

    List<ExchangeTask> searchResults = dataManager.getAllExchangeTasks()
        .stream().filter(task -> {
          return task.getName().toLowerCase().contains(searchInput)
              || task.getProvider().getName().toLowerCase()
              .contains(searchInput) || task.getDescription().toLowerCase()
              .contains(searchInput);
        }).toList();
    showTable(searchResults);
  }

  public void handleClearSearchBar()
  {
    searchTextField.clear();
    showTable(dataManager.getAllExchangeTasks());
  }

  public void handleAddExchangeTask()
  {
    ViewManager.showView(Views.ADD_EXCHANGE_TASK);
  }

  public void handleViewDetails()
  {
    Task task = allExchangeTaskTable.getSelectionModel().getSelectedItem();
    if (task == null) return;

    ViewManager.showView(Views.VIEW_DETAILED_EXCHANGE_TASK, String.valueOf(task.getId()));
  }

  public void handleTaskCompleted()
  {

  }

  public void handleDeleteTask()
  {

  }

}
