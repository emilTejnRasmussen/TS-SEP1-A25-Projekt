package kloeverly.presentation.controllers.exchange_task;

import javafx.scene.control.*;
import kloeverly.domain.ExchangeTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class UpdateExchangeTaskController extends AddExchangeTaskController
    implements AcceptsStringArgument, InitializableController
{

  public Label mainTitleLbl;
  public Label idValue;

  private Integer taskId;
  private ExchangeTask task;
  private boolean populated = false;

  @Override public void setArgument(String argument)
  {
    try
    {
      this.taskId = Integer.parseInt(argument.trim());
    }
    catch (NumberFormatException e)
    {
      this.taskId = null;
    }

    tryPopulate();
  }

  @Override public void init(DataManager dataManager)
  {
    super.init(dataManager);
    tryPopulate();

  }

  private void tryPopulate()
  {
    if (populated)
      return;
    if (dataManager == null || taskId == null)
      return;

    task = (ExchangeTask) dataManager.getTaskById(taskId);
    if (task == null)
    {
      ViewManager.showView(Views.EXCHANGE_TASKS);
      return;
    }

    mainTitleLbl.setText(task.getName());
    idValue.setText(String.valueOf(task.getId()));

    titleTextField.setText(task.getName());
    valueTextField.setText(String.valueOf(task.getValue()));
    providerCombo.setValue(task.getProvider());
    amountSpinner.getValueFactory().setValue(task.getAmount());
    descriptionTextArea.setText(task.getDescription());

    populated = true;
  }

  @Override public void handleAdd()
  {
    ExchangeTask updatedTask = setTask();
    if (updatedTask == null)
      return;
    task.setName(updatedTask.getName());
    task.setValue(updatedTask.getValue());
    task.setDescription(updatedTask.getDescription());
    task.setAmount(updatedTask.getAmount());
    task.setProvider(updatedTask.getProvider());
    dataManager.updateTask(task);
    ViewManager.updateExternalView();
    ViewManager.showView(Views.EXCHANGE_TASKS, null, task.formatTaskUpdated());
  }

  @Override
  public void handleCancel()
  {
    ViewManager.showView(Views.VIEW_DETAILED_EXCHANGE_TASK, String.valueOf(task.getId()));
  }

}
