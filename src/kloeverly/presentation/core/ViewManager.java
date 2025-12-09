package kloeverly.presentation.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kloeverly.presentation.controllers.ExternalScreenController;

import java.io.IOException;

public class ViewManager
{
    private static BorderPane mainLayout;

    private static Stage externalStage;
    private static ExternalScreenController externalController;

    public static void init(Stage primaryStage, Views initialView) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(initialView.getView()));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kløverly");
        primaryStage.getIcons().addAll(
                new Image(ViewManager.class.getResourceAsStream("/icons/clover1.png")),
                new Image(ViewManager.class.getResourceAsStream("/icons/clover2.png"))
        );
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

    public static void showView(Views view, String argument)
    {
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

    public static void showExternalScreen(Button externalBtn)
    {
        if (externalStage != null && externalStage.isShowing())
        {
            externalStage.toFront();
            return;
        }

        try
        {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(Views.EXTERNAL.getView()));
            Scene scene = new Scene(loader.load(), 900, 900);

            externalController = loader.getController();
            ControllerConfigurator.configure(externalController);

            externalStage = new Stage();
            externalStage.setTitle("Kløverly");
            externalStage.setScene(scene);

            externalStage.setOnCloseRequest(e -> {
                externalBtn.setDisable(false);
                externalStage = null;
            });

            externalStage.show();
        } catch (IOException e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "Cannot open external screen.");
            error.show();
        }
    }

    public static Stage getExternalStage()
    {
        return externalStage;
    }

    public static void updateExternalView() {
        externalController.refresh();
    }
}
