package kloeverly.presentation.core;

public enum Views
{
    MAIN("MainView"),
    HOME("HomeView"),
    EXTERNAL("ExternalScreenView"),

    //Common Tasks
    ADD_COMMON_TASK("common_task/AddCommonTask"),
    UPDATE_COMMON_TASK("common_task/UpdateCommonTask"),
    REGISTER_COMMON_TASK("common_task/RegisterCommonTask"),
    COMMON_TASK("common_task/ViewSingleCommonTask"),
    COMMON_TASKS("common_task/ViewAllCommonTasks"),

    // Green Tasks
    GREEN_TASKS("green_task/ViewAllGreenTasks"),
    ADD_GREEN_TASK("green_task/AddGreenTask"),
    VIEW_SINGLE_GREEN_TASK("green_task/ViewGreenTask"),
    UPDATE_GREEN_TASK("green_task/UpdateGreenTask"),
    REGISTER_GREEN_TASK("green_task/RegisterGreenTask"),

    // Exchange Tasks
    EXCHANGE_TASKS("exchange_task/ViewAllExchangeTasks"),
    ADD_EXCHANGE_TASK("exchange_task/AddExchangeTask"),
    VIEW_DETAILED_EXCHANGE_TASK("exchange_task/ViewDetailedExchangeTask"),
    UPDATE_EXCHANGE_TASK("exchange_task/UpdateExchangeTask"),
    REGISTER_EXCHANGE_TASK("exchange_task/RegisterExchangeTask"),

  // Residents
    RESIDENTS("resident/ViewAllResidents"),
    ADD_RESIDENT("resident/AddResident"),
    VIEW_SINGLE_RESIDENT("resident/ViewResident"),
    UPDATE_RESIDENT("resident/UpdateResident");


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
