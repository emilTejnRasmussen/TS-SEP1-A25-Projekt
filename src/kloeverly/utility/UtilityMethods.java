package kloeverly.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class UtilityMethods
{
    public static void showFlashMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message,
                ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Bekræftelse");
        alert.show();
    }
}
