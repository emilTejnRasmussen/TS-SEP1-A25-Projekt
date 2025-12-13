package kloeverly.presentation.controllers.exchange_task;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.util.StringConverter;
import kloeverly.domain.ExchangeTask;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.utility.UtilityMethods;

public class RegisterExchangeTaskController
    implements InitializableController, AcceptsStringArgument
{
  public Label amountLbl;
  public Spinner<Integer> amountSpinner;
  public Label amountErrorLbl;
  public Label receiverErrorLbl;
  public ComboBox<Resident> receiverComboBox;
  public Label nameLbl;
  public Label valueLbl;
  public Label descriptionLbl;
  public Button registerBtn;
  public Label idLbl;

  private DataManager dataManager;
  private Integer taskId;
  private ExchangeTask selectedTask;
  private boolean populated = false;
  private boolean receiverBoolean = false;
  private boolean amountBoolean = true;

  @Override public void setArgument(String argument)
  {
    try
    {
      this.taskId = Integer.parseInt(argument);
    }
    catch (NumberFormatException e)
    {
      this.taskId = null;
    }
    tryPopulate();
  }

  @Override public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    tryPopulate();
  }

  private void tryPopulate()
  {
    if (populated)
      return;
    if (dataManager == null || taskId == null)
      return;

    this.selectedTask = (ExchangeTask) dataManager.getTaskById(taskId);
    if (selectedTask == null)
    {
      ViewManager.showView(Views.EXCHANGE_TASKS);
      return;
    }

    this.idLbl.setText(String.valueOf(selectedTask.getId()));
    this.nameLbl.setText(selectedTask.getName());
    this.valueLbl.setText(String.valueOf(selectedTask.getValue()));
    this.amountLbl.setText(String.valueOf(selectedTask.getAmount()));
    this.descriptionLbl.setText(selectedTask.getDescription());
    this.receiverComboBox.getItems().addAll(dataManager.getAllResidents());
    UtilityMethods.createAmountSpinner(amountSpinner);

    receiverComboBox.setConverter((new StringConverter<Resident>()
    {
      @Override public String toString(Resident resident)
      {
        if (resident == null)
          return "";
        return (resident.getId() + ": " + resident.getName() + ", point: "
            + resident.getPoints());
      }

      @Override public Resident fromString(String resident)
      {
        return null;
      }
    }));

    registerBtn.setDisable(true);
    receiverErrorLbl.setVisible(false);
    amountErrorLbl.setVisible(false);
    amountErrorLbl.setText(
        "Du kan højest sætte antal til " + selectedTask.getAmount());

    receiverComboBox.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldIdx, newIdx) -> {
          listenerLogic();
        });

    amountSpinner.valueProperty()
        .addListener((observable, oldValue, newValue) -> {
          listenerLogic();
        });

    populated = true;
  }

  private void listenerLogic()
  {
    Integer amount = amountSpinner.getValue();
    int totalPointsForTask = amount * selectedTask.getValue();
    Resident selectedResident = receiverComboBox.getSelectionModel()
        .getSelectedItem();

    if (amount > selectedTask.getAmount())
    {
      amountErrorLbl.setVisible(true);
      amountBoolean = false;
    }
    else
    {
      amountErrorLbl.setVisible(false);
      amountBoolean = true;
    }

    if (selectedResident == null)
    {
      receiverErrorLbl.setText("Vælg en beboer");
      receiverErrorLbl.setVisible(true);
      receiverBoolean = false;
    }
    else
    {
      if (selectedResident.getPoints() < totalPointsForTask)
      {
        receiverErrorLbl.setText("Modtageren har ikke nok point.");
        receiverErrorLbl.setVisible(true);
        receiverBoolean = false;
      }
      else
      {
        receiverErrorLbl.setVisible(false);
        receiverBoolean = true;
      }
    }
    registerBtn.setDisable(!(receiverBoolean && amountBoolean));
  }

  public void handleRegister()
  {
    registerBtn.setDisable(true);

    Resident receiver = receiverComboBox.getSelectionModel().getSelectedItem();
    Integer amount = amountSpinner.getValue();
    int totalPointsForTask = amount * selectedTask.getValue();
    ExchangeTask updatedTask = null;
    StringBuilder flash = new StringBuilder();

    if (receiver == null)
    {
      receiverErrorLbl.setText("Vælg en beboer");
      receiverErrorLbl.setVisible(true);
      registerBtn.setDisable(false);
      return;
    }

    if (selectedTask.getAmount() < amount)
    {
      amountErrorLbl.setVisible(true);
      registerBtn.setDisable(false);
      return;
    }

    if (receiver.getPoints() >= totalPointsForTask)
    {
      for (int i = 0; i < amount; i++)
      {
        dataManager.completeTask(selectedTask.getId(), receiver);

        updatedTask = (ExchangeTask) dataManager.getTaskById(
            selectedTask.getId());

        if (updatedTask.getAmount() == 0)
        {
          break;
        }
      }

      if (updatedTask.getAmount() >= 1)
      {
        flash.append(updatedTask.formatTaskCompleted(receiver));
      }

      if (updatedTask.getAmount() == 0)
      {
        dataManager.deleteTask(updatedTask);
        flash.append(updatedTask.formatTaskCompleted(receiver) + " " + updatedTask.formatTaskDeleted());
      }

      ViewManager.updateExternalView();
      ViewManager.showView(Views.EXCHANGE_TASKS, null, flash.toString());
    }
    else
    {
      receiverErrorLbl.setText("Modtageren har ikke nok point.");
      receiverErrorLbl.setVisible(true);
    }
  }

  public void handleCancel()
  {
    ViewManager.showView(Views.EXCHANGE_TASKS);
  }

}
