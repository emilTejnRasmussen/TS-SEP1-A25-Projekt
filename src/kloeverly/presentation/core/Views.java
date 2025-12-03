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
    TEST_VIEW_COMMON_TASK("common_task/test"),

    // Residents
    ADD_RESIDENT("");

    private final String view;

    Views(String view){
        this.view = view;
    }

    public String getView(){
        return "/fxml/" + this.view + ".fxml";
    }
}
