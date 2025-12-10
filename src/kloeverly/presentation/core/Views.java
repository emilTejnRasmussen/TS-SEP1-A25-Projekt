package kloeverly.presentation.core;

public enum Views
{
    MAIN("MainView"),
    HOME("HomeView"),
    EXTERNAL("ExternalScreenView"),

  //Common Tasks
  ADD_COMMON_TASK("common_task/AddCommonTask"), UPDATE_COMMON_TASK(
    "common_task/UpdateCommonTask"), REGISTER_COMMON_TASK(
    "common_task/RegisterCommonTask"), COMMON_TASK(
    "common_task/ViewSingleCommonTask"), COMMON_TASKS(
    "common_task/ViewAllCommonTasks"),

  // Exchange Tasks
  EXCHANGE_TASKS("exchange_task/ViewAllExchangeTasks"),
  ADD_EXCHANGE_TASK("exchange_task/AddExchangeTask"),
  VIEW_DETAILED_EXCHANGE_TASK("exchange_task/ViewDetailedExchangeTask"),

  // Residents
  ADD_RESIDENT("");

  private final String view;

  Views(String view)
  {
    this.view = view;
  }

  public String getView()
  {
    return "/fxml/" + this.view + ".fxml";
  }
}
