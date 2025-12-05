package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddResidentController implements InitializableController
{
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField factorTextField;
    @FXML
    private Label nameErrorLbl;
    @FXML
    private Label factorErrorLbl;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    @FXML
    public void handleAdd() {
        // Behold dit input-check
        if (!validInput()) return;

        // 1) Læs navn
        String name = nameTextField.getText().trim();

        // 2) Læs faktor – tom => standard 1.0
        String factorText = factorTextField.getText().trim();
        double factor = 1.0; // standardværdi

        if (!factorText.isEmpty()) {
            try {
                factor = Double.parseDouble(factorText);
            } catch (NumberFormatException e) {
                // Hvis nogen skriver “hej” i stedet for et tal
                System.out.println("Pointfaktor skal være et tal.");
                return;
            }
        }

        // 3) Opret beboer med både navn og pointFactor
        Resident resident = new Resident(name, factor);

        // 4) Gem i dataManager
        dataManager.addResident(resident);

        // 5) Tilbage til beboer-oversigten
        ViewManager.showView(Views.RESIDENTS);
    }


    @FXML
    public void handleCancel()
    {
        ViewManager.showView(Views.RESIDENTS);
    }

    private boolean validInput()
    {
        nameErrorLbl.setText("");
        factorErrorLbl.setText("");

        boolean ok = true;

        if (nameTextField.getText().trim().isEmpty())
        {
            nameErrorLbl.setText("Navn må ikke være tomt");
            ok = false;
        }

        String factorText = factorTextField.getText().trim();
        if (!factorText.isEmpty())
        {
            try
            {
                Double.parseDouble(factorText);
            }
            catch (NumberFormatException e)
            {
                factorErrorLbl.setText("Pointfaktor skal være et tal (fx 1.0)");
                ok = false;
            }
        }

        return ok;
    }
}
