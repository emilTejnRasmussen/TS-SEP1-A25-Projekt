package kloeverly.presentation.controllers.exchange_task;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsFlashMessage;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import java.util.List;
import java.util.Optional;

public class ViewAllExchangeTasksController
    implements InitializableController, AcceptsFlashMessage
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
  private String pendingFlash;

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    showTable(dataManager.getAllExchangeTasks());

    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    providerCol.setCellValueFactory(new PropertyValueFactory<>("providerName"));
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

    if (pendingFlash != null && !pendingFlash.isBlank())
    {
      Alert alert = new Alert(Alert.AlertType.NONE, pendingFlash,
          ButtonType.OK);
      alert.setHeaderText(null);
      alert.setTitle("Bekræftelse");
      alert.show();
      pendingFlash = null;
    }
  }

  @Override public void setFlashMessage(String message)
  {
    this.pendingFlash = message;
  }

  public void handleSearch()
  {
    String searchInput = searchTextField.getText().toLowerCase().trim();

    if (searchInput.isEmpty())
    {
      showTable(getSource());
      return;
    }

    List<ExchangeTask> searchResults = getSource().stream().filter(
            task -> task.getName().toLowerCase().contains(searchInput)
                || task.getProvider().getName().toLowerCase().contains(searchInput)
                || String.valueOf(task.getProvider().getId()).contains(searchInput)
                || task.getDescription().toLowerCase().contains(searchInput))
        .toList();
    showTable(searchResults);
  }

  public void handleClearSearchBar()
  {
    searchTextField.clear();
    showTable(getSource());
  }

  public void handleAddExchangeTask()
  {
    ViewManager.showView(Views.ADD_EXCHANGE_TASK);
  }

  public void handleViewDetails()
  {
    Task task = allExchangeTaskTable.getSelectionModel().getSelectedItem();
    if (task == null)
      return;

    ViewManager.showView(Views.VIEW_DETAILED_EXCHANGE_TASK,
        String.valueOf(task.getId()));
  }

  public void handleTaskCompleted()
  {
    /*
    ExchangeTask selectedTask = allExchangeTaskTable.getSelectionModel()
        .getSelectedItem();
    if (selectedTask == null)
    {
      return;
    }

    Dialog<Resident> dialog = new Dialog<>();
    dialog.setTitle("Registrer byttehandel " + selectedTask.getName());
    dialog.setContentText(
        "Byttehandlen \"" + selectedTask.getName() + "\" koster "
            + selectedTask.getValue() + ".");
    dialog.setContentText("Vælg modtager af handlen: ");
    ButtonType completeBtn = new ButtonType("Gem",
        ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelBtn = new ButtonType("Annuller",
        ButtonBar.ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().setAll(completeBtn, cancelBtn);

    ComboBox<Resident> receiverComboBox = new ComboBox<>();
    receiverComboBox.getItems().setAll(dataManager.getAllResidents());
    receiverComboBox.setEditable(false);
    receiverComboBox.setVisibleRowCount(10);
    receiverComboBox.setPromptText("Vælg beboer");
    receiverComboBox.setConverter(new StringConverter<Resident>()
    {
      @Override public String toString(Resident resident)
      {
        return (resident == null) ?
            "" :
            (resident.getId() + ": " + resident.getName() + ", "
                + resident.getPoints());
      }

      @Override public Resident fromString(String string)
      {
        return receiverComboBox.getValue();
      }
    }
*/
    ViewManager.updateExternalView();
  }

  public void handleDeleteTask()
  {
    ExchangeTask selectedTask = allExchangeTaskTable.getSelectionModel()
        .getSelectedItem();

    if (selectedTask == null)
    {
      return;
    }

    ButtonType deleteBtn = new ButtonType("Slet opgave",
        ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelBtn = new ButtonType("Annuller",
        ButtonBar.ButtonData.CANCEL_CLOSE);

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Bekræft sletning");
    confirm.setHeaderText("Slet bytteopgave");
    confirm.setContentText(
        "Er du sikker på, at du vil slette: '" + selectedTask.getName() + "'?");
    confirm.getButtonTypes().setAll(deleteBtn, cancelBtn);

    Optional<ButtonType> result = confirm.showAndWait();
    Platform.runLater(
        // Fokuser annuller-knappen lige efter JavaFX har loadet popup'en
        () -> confirm.getDialogPane().lookupButton(cancelBtn).requestFocus());
    if (result.isPresent() && result.get() == deleteBtn)
    {
      dataManager.deleteTask(selectedTask);
      ViewManager.updateExternalView();
      ViewManager.showView(Views.EXCHANGE_TASKS, null,
          selectedTask.formatTaskDeleted());
    }
  }

  // HJÆLPE-METODER

  private void showTable(List<ExchangeTask> tasks)
  {
    allExchangeTaskTable.getItems().setAll(tasks == null ? List.of() : tasks);
  }

  private List<ExchangeTask> getSource()
  {
    return dataManager.getAllExchangeTasks();
  }
}
