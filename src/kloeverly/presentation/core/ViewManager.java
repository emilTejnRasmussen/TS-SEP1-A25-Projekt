package kloeverly.presentation.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewManager
{
    private static BorderPane mainLayout;


    private static FXMLLoader createLoader(Views view) {
        String fxmlPath = "/fxml/" + view.getView() + ".fxml";
        System.out.println("Loader FXML: " + fxmlPath);

        URL url = ViewManager.class.getResource(fxmlPath);
        if (url == null) {
            throw new RuntimeException("FXML not found at: " + fxmlPath);
        }
        return new FXMLLoader(url);
    }

    public static void init(Stage primaryStage, Views initialView) throws IOException
    {
        FXMLLoader loader = createLoader(initialView);
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kløverly");
        primaryStage.show();
    }

    public static void showView(Views view)
    {
        try {
            FXMLLoader loader = createLoader(view);
            Parent root = loader.load();

            Object controller = loader.getController();
            ControllerConfigurator.configure(controller);

            mainLayout.setCenter(root);

        } catch (IOException | RuntimeException e) {
            System.err.println("Fejl ved indlæsning af view: " + e.getMessage());
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "Cannot find view '" + view.getView() + "'.\n\n" + e.getMessage());
            error.show();
        }
    }

    public static void showView(Views view, String argument)
    {
        try {
            FXMLLoader loader = createLoader(view);
            Parent root = loader.load();

            AcceptsStringArgument controller = loader.getController();
            ControllerConfigurator.configure(controller);
            controller.setArgument(argument);

            mainLayout.setCenter(root);

        } catch (IOException | RuntimeException e) {
            System.err.println("Fejl ved indlæsning af view: " + e.getMessage());
            Alert error = new Alert(Alert.AlertType.ERROR,
                    "Cannot find view '" + view.getView() + "'.\n\n" + e.getMessage());
            error.show();
        }
    }
}
