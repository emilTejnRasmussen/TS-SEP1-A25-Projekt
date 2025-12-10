package kloeverly.presentation.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
        primaryStage.getIcons().addAll(
            new Image(Objects.requireNonNull(
                ViewManager.class.getResourceAsStream("/icons/clover1.png"))),
            new Image(Objects.requireNonNull(
                ViewManager.class.getResourceAsStream("/icons/clover2.png")))
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

  public static void showView(Views view, String argument, String flashMessage){
    try
    {
      FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(view.getView()));
      Parent root = loader.load();

      Object controller = loader.getController();
      if (controller instanceof AcceptsStringArgument && argument != null)
      {
        ((AcceptsStringArgument) controller).setArgument(argument);
      }

      if (controller instanceof AcceptsFlashMessage && flashMessage != null)
      {
        ((AcceptsFlashMessage) controller).setFlashMessage(flashMessage);
      }

      ControllerConfigurator.configure(controller);
      mainLayout.setCenter(root);

    } catch (IOException e)
    {
      Alert error = new Alert(Alert.AlertType.ERROR, "Cannot find view '" + view.getView() + "'.");
      error.show();
    }
  }

  public static void showExternalScreen(Button runExternalScreenBtn){
    try{
      FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(Views.EXTERNAL.getView()));
      Scene scene = new Scene(loader.load(), 600, 400);
      Stage stage = new Stage();
      stage.setTitle("Kløverly");
      stage.setScene(scene);
      stage.setOnCloseRequest( e -> runExternalScreenBtn.setDisable(false));
      stage.show();
    } catch (IOException e)
    {
      Alert error = new Alert(Alert.AlertType.ERROR, "Cannot open external screen.");
      error.show();
    }
  }
}
