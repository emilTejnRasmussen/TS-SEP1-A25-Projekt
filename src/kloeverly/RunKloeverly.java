package kloeverly;

import javafx.application.Application;
import javafx.stage.Stage;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class RunKloeverly extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Start i MainView.fxml
        ViewManager.init(primaryStage, Views.MAIN);
    }

    public static void main(String[] args) {
        launch();
    }
}
