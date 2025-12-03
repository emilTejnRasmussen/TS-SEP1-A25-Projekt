package kloeverly;

import javafx.application.Application;
import javafx.stage.Stage;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class RunKloeverly extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewManager.init(primaryStage, Views.MAIN);
        ViewManager.showView(Views.HOME);
    }
}
