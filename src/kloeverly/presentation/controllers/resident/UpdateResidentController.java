package kloeverly.presentation.controllers.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class UpdateResidentController implements InitializableController, AcceptsStringArgument
{
    @FXML
    private Label mainTitleLbl;
    @FXML
    private Label idLbl;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField factorTextField;
    @FXML
    private TextField pointsTextField;
    @FXML
    private Label nameErrorLbl;
    @FXML
    private Label factorErrorLbl;
    @FXML
    private Label pointsErrorLbl;

    private DataManager dataManager;
    private int id;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    @Override
    public void setArgument(String argument)
    {
        this.id = Integer.parseInt(argument);
        Resident resident = dataManager.getResidentById(this.id);

        mainTitleLbl.setText("Opdater beboer: " + resident.getName());
        idLbl.setText("ID: " + resident.getId());

        nameTextField.setText(resident.getName());
        factorTextField.setText(resident.getPointFactor() + "");
        pointsTextField.setText(resident.getPoints() + "");

    }

    @FXML
    public void handleSave()
    {
        if (!validInput()) return;

        String name = nameTextField.getText().trim();
        double factor = Double.parseDouble(factorTextField.getText().trim());
        int points = Integer.parseInt(pointsTextField.getText().trim());

        Resident updated = new Resident(name);
        updated.setId(this.id);
        updated.setPointFactor(factor);
        updated.setPoints(points);


        dataManager.updateResident(updated);

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
        pointsErrorLbl.setText("");

        boolean ok = true;

        if (nameTextField.getText().trim().isEmpty())
        {
            nameErrorLbl.setText("Navn må ikke være tomt");
            ok = false;
        }

        try
        {
            Double.parseDouble(factorTextField.getText().trim());
        }
        catch (NumberFormatException e)
        {
            factorErrorLbl.setText("Pointfaktor skal være et tal");
            ok = false;
        }

        try
        {
            Integer.parseInt(pointsTextField.getText().trim());
        }
        catch (NumberFormatException e)
        {
            pointsErrorLbl.setText("Point skal være et helt tal");
            ok = false;
        }

        return ok;
    }
}
