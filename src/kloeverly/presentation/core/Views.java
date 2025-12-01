package kloeverly.presentation.core;

public enum Views
{
    MAIN("MainView"),
    HOME("HomeView"),

    COMMON_TASKS("common_task/ViewAllCommonTasks");

    private final String view;

    Views(String view){
        this.view = view;
    }

    public String getView(){
        return "/fxml/" + this.view + ".fxml";
    }
}
