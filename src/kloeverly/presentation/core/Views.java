package kloeverly.presentation.core;

public enum Views
{
    MAIN("MainView"),
    HOME("HomeView"),

    //Common Tasks
    ADD_COMMON_TASK("common_task/AddCommonTask"),
    UPDATE_COMMON_TASK("common_task/UpdateCommonTask"),
    COMMON_TASK("common_task/ViewSingleCommonTask"),
    COMMON_TASKS("common_task/ViewAllCommonTasks"),
    RESIDENTS("resident/ViewAllResidents"),
    ADD_RESIDENT("resident/AddResident"),
    UPDATE_RESIDENT("resident/UpdateResident"),


    TEST_VIEW_COMMON_TASK("common_task/test"),
    ADD_GREEN_TASK("green_task/AddGreenTask"),
    CREATE_GREEN_TASK("green_task/CreateGreenTask");

    private final String view;

    Views(String view){
        this.view = view;
    }

    public String getView(){
        return "/fxml/" + this.view + ".fxml";
    }
}
