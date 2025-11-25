package kloeverly.presentation.core;

public enum Views
{
    MAIN("MainView");

    private final String view;

    Views(String view){
        this.view = view;
    }

    public String getView(){
        return "/fxml/" + this.view + ".fxml";
    }
}
