package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

/**
 * Controller til at tilføje en ny grøn opgave.
 * Matcher AddGreenTask.fxml og følger AddViewTemplate-mønsteret.
 */
public class AddGreenTaskController implements InitializableController {

    // --- Data-adgang (injiceres af ControllerConfigurator) ---
    private DataManager dataManager;

    // --- FXML-felter ---

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField pointsField;

    @FXML
    private Label errorLabel;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // --- Knap-handlers ---

    @FXML
    private void handleAdd() {
        String name = nameField.getText() == null ? "" : nameField.getText().trim();
        String description = descriptionField.getText() == null ? "" : descriptionField.getText().trim();
        String pointsText = pointsField.getText() == null ? "" : pointsField.getText().trim();

        if (name.isEmpty() || description.isEmpty() || pointsText.isEmpty()) {
            errorLabel.setText("Navn, beskrivelse og point skal udfyldes.");
            return;
        }

        int points;
        try {
            points = Integer.parseInt(pointsText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Point skal være et helt tal, fx 10 eller 25.");
            return;
        }

        // Opret grøn opgave (tilpas constructor hvis jeres GreenTask er anderledes)
        GreenTask task = new GreenTask(name, description, points);

        dataManager.addTask(task);

        // Tilbage til oversigten
        ViewManager.showView(Views.GREEN_TASKS);
    }

    @FXML
    private void handleCancel() {
        ViewManager.showView(Views.GREEN_TASKS);
    }
}
