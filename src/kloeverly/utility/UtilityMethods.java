package kloeverly.utility;

import javafx.scene.control.*;

public class UtilityMethods
{
    public static void showFlashMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.NONE, message,
                ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Bekræftelse");
        alert.show();
    }

    public static void buttonListener(TableView<?> tableView, Button detailsBtn, Button registerBtn, Button deleteBtn)
    {
        detailsBtn.setDisable(true);
        registerBtn.setDisable(true);
        deleteBtn.setDisable(true);

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    boolean hasSelection = newValue != null;
                    detailsBtn.setDisable(!hasSelection);
                    registerBtn.setDisable(!hasSelection);
                    deleteBtn.setDisable(!hasSelection);
                });
    }

    public static void createAmountSpinner(Spinner<Integer> spinner){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, 100, 1
        );
        spinner.setValueFactory(valueFactory);
    }
}
