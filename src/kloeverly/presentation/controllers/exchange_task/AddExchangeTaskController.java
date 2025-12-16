package kloeverly.presentation.controllers.exchange_task;

import javafx.scene.control.*;
import javafx.util.StringConverter;
import kloeverly.domain.ExchangeTask;
import kloeverly.domain.Resident;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import java.util.List;

public class AddExchangeTaskController implements InitializableController
{
  public Label titleErrorLbl;
  public TextField titleTextField;
  public Label valueErrorLbl;
  public TextField valueTextField;
  public Label providerErrorLbl;
  public ComboBox<Resident> providerCombo;
  public Label amountErrorLbl;
  public Spinner<Integer> amountSpinner;
  public Label descriptionErrorLbl;
  public TextArea descriptionTextArea;
  public Button addBtn;
  public Button cancelBtn;
  public ComboBox<Resident> providerCombo1;

  protected DataManager dataManager;

  public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;

    titleErrorLbl.setVisible(false);
    titleErrorLbl.setText("Titel skal udfyldes");
    valueErrorLbl.setVisible(false);
    providerErrorLbl.setVisible(false);
    amountErrorLbl.setVisible(false);
    descriptionErrorLbl.setVisible(false);

    amountSpinner.setValueFactory(
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999999999, 1));
    amountSpinner.setEditable(true);

    numericGuard(valueTextField, valueErrorLbl);
    numericGuard(amountSpinner.getEditor(), amountErrorLbl);

    providerCombo.getItems().setAll(dataManager.getAllResidents());
    providerCombo.setEditable(false);
    providerCombo.setVisibleRowCount(10);
    providerCombo.setConverter(new StringConverter<Resident>()
    {
      @Override public String toString(Resident resident)
      {
        return (resident == null) ?
            "" :
            (resident.getId() + ": " + resident.getName());
      }

      @Override public Resident fromString(String string)
      {
        return providerCombo.getValue();
      }
    });
  }

  public void handleAdd()
  {
    ExchangeTask task = setTask();
    if (task == null)
      return;
    dataManager.addTask(task);
    ViewManager.updateExternalView();
    ViewManager.showView(Views.EXCHANGE_TASKS, null, task.formatTaskAdded());
  }

  public void handleCancel()
  {
    ViewManager.updateExternalView();
    ViewManager.showView(Views.EXCHANGE_TASKS);
  }

  // HJÆLPER-METODER

  protected void numericGuard(TextInputControl input, Label errorLabel)
  {
    input.textProperty().addListener((observable, oldText, newText) -> {
      boolean valid;

      if (newText == null || newText.isEmpty())
      {
        valid = true;
      }
      else
      {
        valid = newText.matches("\\d+");
      }

      if (valid)
      {
        errorLabel.setVisible(false);
        addBtn.setDisable(false);
        addBtn.setOpacity(1);
      }
      else
      {
        errorLabel.setText("Kun hele tal er tilladt");
        errorLabel.setVisible(true);
        addBtn.setDisable(true);
        addBtn.setOpacity(0.5);
      }
    });
  }

  protected ExchangeTask setTask()
  {
    String name;
    int value = 0;
    Resident provider;
    int amount = 1;
    String description;
    boolean hasErrors = false;

    titleErrorLbl.setVisible(false);
    valueErrorLbl.setVisible(false);
    providerErrorLbl.setVisible(false);
    amountErrorLbl.setVisible(false);
    descriptionErrorLbl.setVisible(false);

    // TITEL
    name =
        titleTextField.getText() != null ? titleTextField.getText().trim() : "";
    if (name.isEmpty())
    {
      titleErrorLbl.setText("Titel skal udfyldes");
      titleErrorLbl.setVisible(true);
      hasErrors = true;
    }

    // POINTVÆRDI
    String valueString =
        valueTextField.getText() != null ? valueTextField.getText().trim() : "";
    if (valueString.isEmpty())
    {
      valueErrorLbl.setText("Pointværdi skal udfyldes");
      valueErrorLbl.setVisible(true);
      hasErrors = true;
    }
    else
    {
      try
      {
        value = Integer.parseInt(valueString);
        if (value < 0)
        {
          valueErrorLbl.setText("Pointværdien skal være 0 eller større");
          valueErrorLbl.setVisible(true);
          hasErrors = true;
          value = 0;
        }
      }
      catch (NumberFormatException e)
      {
        valueErrorLbl.setText("Kun hele tal er tilladt");
        valueErrorLbl.setVisible(true);
        hasErrors = true;
        value = 0;
      }
    }

    // UDBYDER
    provider = providerCombo.getValue();

    if (provider == null)
    {
      providerErrorLbl.setText("Du skal vælge en beboer fra listen");
      providerErrorLbl.setVisible(true);
      hasErrors = true;
    }
    else
    {
      boolean exists = providerCombo.getItems().stream()
          .anyMatch(r -> r.getId() == provider.getId());
      if (!exists)
      {
        providerErrorLbl.setText("Modtageren findes ikke");
        providerErrorLbl.setVisible(true);
        hasErrors = true;
      }
    }

    // MÆNGDE
    try
    {
      amount = amountSpinner.getValue();
      if (amount < 1)
      {
        amountErrorLbl.setText("Mængden skal være mindst 1");
        amountErrorLbl.setVisible(true);
        hasErrors = true;
      }
    }
    catch (Exception exception)
    {
      amountErrorLbl.setText("Kun hele tal er tilladt");
      amountErrorLbl.setVisible(true);
      hasErrors = true;
    }

    // BESKRIVELSE
    description = descriptionTextArea.getText();

    // HJÆLPERE
    if (hasErrors)
    {
      focusFirstError();
      return null;
    }
    else
    {
      return new ExchangeTask(name, description, value, amount, provider);
    }
  }

  protected void focusFirstError()
  {
    if (titleErrorLbl.isVisible())
    {
      titleTextField.requestFocus();
      return;
    }

    if (valueErrorLbl.isVisible())
    {
      valueTextField.requestFocus();
      return;
    }

    if (providerErrorLbl.isVisible())
    {
      providerCombo.requestFocus();
      return;
    }

    if (amountErrorLbl.isVisible())
    {
      amountSpinner.requestFocus();
      return;
    }

    if (descriptionErrorLbl.isVisible())
    {
      descriptionTextArea.requestFocus();
      return;
    }
  }
}
