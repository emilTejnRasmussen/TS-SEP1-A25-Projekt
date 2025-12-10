package kloeverly.presentation.controllers.exchange_task;

import javafx.scene.control.Label;
import kloeverly.domain.ExchangeTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewDetailedExchangeTaskController
    implements AcceptsStringArgument, InitializableController
{

  public Label mainTitleLbl;
  public Label idLbl;
  public Label titleLbl;
  public Label valueLbl;
  public Label providerLbl;
  public Label amountLbl;
  public Label descriptionLbl;

  private DataManager dataManager;
  private int id;

  @Override public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
  }

  @Override public void setArgument(String argument)
  {
    this.id = Integer.parseInt(argument);
    ExchangeTask task = (ExchangeTask) dataManager.getTaskById(id);

    mainTitleLbl.setText(task.getName());
    idLbl.setText(String.valueOf(task.getId()));
    titleLbl.setText(task.getName());
    valueLbl.setText(String.valueOf(task.getValue()));
    providerLbl.setText(task.getProviderName());
    amountLbl.setText(String.valueOf(task.getAmount()));
    descriptionLbl.setText(task.getDescription());
  }

  public void handleUpdate()
  {
    ViewManager.showView(Views.UPDATE_EXCHANGE_TASK, String.valueOf(this.id));
  }

  public void handleGoBack()
  {
    ViewManager.showView(Views.EXCHANGE_TASKS);
  }
}
