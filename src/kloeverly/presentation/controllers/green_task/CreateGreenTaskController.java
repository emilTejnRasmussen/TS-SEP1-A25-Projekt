package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class CreateGreenTaskController implements InitializableController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField valueField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label titleErrorLbl;

    @FXML
    private Label valueErrorLbl;

    @FXML
    private Label descriptionErrorLbl;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @FXML
    private void handleCancel() {
        ViewManager.showView(Views.ADD_GREEN_TASK);
    }

    @FXML
    private void handleCreate() {
        clearErrors();

        String title = titleField.getText().trim();
        String desc = descriptionArea.getText().trim();

        double value;

        boolean ok = true;

        if (title.isEmpty()) {
            ok = false;
            titleErrorLbl.setText("Titel mangler");
        }

        try {
            value = Double.parseDouble(valueField.getText().trim());
            if (value <= 0) {
                ok = false;
                valueErrorLbl.setText("Værdi skal være > 0");
            }
        } catch (Exception e) {
            ok = false;
            valueErrorLbl.setText("Ugyldig værdi");
            value = 0;
        }

        if (desc.isEmpty()) {
            ok = false;
            descriptionErrorLbl.setText("Beskrivelse mangler");
        }

        if (!ok) return;

        // Opret opgaven
        GreenTask task = new GreenTask(title, desc, (int) value);
        dataManager.addTask(task);

        // Tilbage til grøn opgave-listen
        ViewManager.showView(Views.ADD_GREEN_TASK);
    }

    private void clearErrors() {
        titleErrorLbl.setText("");
        valueErrorLbl.setText("");
        descriptionErrorLbl.setText("");
    }
}
