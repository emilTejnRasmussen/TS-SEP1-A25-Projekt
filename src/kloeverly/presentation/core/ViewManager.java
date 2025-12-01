package kloeverly.presentation.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewManager
{
    private static BorderPane mainLayout;

    public static void init(Stage primaryStage, Views initialView) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(initialView.getView()));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kløverly");
        primaryStage.show();
    }

    public static void showView(Views view)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(view.getView()));
            Parent root = loader.load();

            Object controller = loader.getController();
            ControllerConfigurator.configure(controller);

            mainLayout.setCenter(root);

        } catch (IOException e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "Cannot find view '" + view.getView() + "'.");
            error.show();
        }
    }

    public static void showView(Views view, String argument){
        try
        {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(view.getView()));
            Parent root = loader.load();

            AcceptsStringArgument controller = loader.getController();
            ControllerConfigurator.configure(controller);
            controller.setArgument(argument);

            mainLayout.setCenter(root);

        } catch (IOException e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "Cannot find view '" + view.getView() + "'.");
            error.show();
        }
    }
}
