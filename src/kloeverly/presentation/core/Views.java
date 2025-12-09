package kloeverly.presentation.core;

public enum Views
{
    MAIN("MainView"),
    HOME("HomeView"),

    // Common tasks
    ADD_COMMON_TASK("common_task/AddCommonTask"),
    UPDATE_COMMON_TASK("common_task/UpdateCommonTask"),
    COMMON_TASK("common_task/ViewSingleCommonTask"),
    COMMON_TASKS("common_task/ViewAllCommonTasks"),

    // Residents
    RESIDENTS("resident/ViewAllResidents"),
    ADD_RESIDENT("resident/AddResident"),
    UPDATE_RESIDENT("resident/UpdateResident"),
    VIEW_RESIDENT("resident/ViewResidents"),

    // Green tasks
    ADD_GREEN_TASK("green_task/AddGreenTask"),
    CREATE_GREEN_TASK("green_task/CreateGreenTask"),
    TEST_VIEW_COMMON_TASK("common_task/test");

    private final String view;

    Views(String view) {
        this.view = view;
    }


    public String getView() {
        return view;
    }
}

