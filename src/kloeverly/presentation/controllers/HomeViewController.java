package kloeverly.presentation.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import kloeverly.domain.*;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;

import java.util.List;
import java.util.Optional;

public class HomeViewController implements InitializableController
{
  @FXML private PieChart pieChart;
  @FXML private Button runExternalScreenBtn;
  @FXML private Label residentAmountLbl;
  @FXML private Label greenTaskAmountLbl;
  @FXML private Label commonTaskAmountLbl;
  @FXML private Label exchangeTaskAmountLbl;
  @FXML private Label climateScoreLbl;

  private DataManager dataManager;

  @Override public void init(DataManager dataManager)
  {
    this.dataManager = dataManager;
    loadStats();
    if (ViewManager.getExternalStage() != null)
    {
      runExternalScreenBtn.setDisable(true);
    }
  }

  public void handleResetResidentPoints()
  {
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Bekræft nulstilling");
    confirm.setHeaderText("Nulstil individuelle point");
    confirm.setContentText(
        "Er du sikker på, at du vil nulstille alle beboers individuelle point?");

    Optional<ButtonType> result = confirm.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK)
    {
      dataManager.resetPointsForAllResidents();
      ViewManager.updateExternalView();
    }
  }

  public void handleShowExternalScreen()
  {
    if (ViewManager.getExternalStage() != null)
      return;
    ViewManager.showExternalScreen(runExternalScreenBtn);
    runExternalScreenBtn.setDisable(true);
  }

  private void loadStats()
  {
    climateScoreLbl.setText(
        dataManager.getClimateScore().getTotalGreenPoints() + "");
    residentAmountLbl.setText(dataManager.getAllResidents().size() + "");
    greenTaskAmountLbl.setText(dataManager.getAllGreenTasks().size() + "");
    commonTaskAmountLbl.setText(dataManager.getAllCommonTasks().size() + "");
    exchangeTaskAmountLbl.setText(
        dataManager.getAllExchangeTasks().size() + "");

    createPieChart();
  }

  private void createPieChart()
  {
    pieChart.setAnimated(false);
    pieChart.getData().clear();
    pieChart.setLabelsVisible(false);

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    int green = dataManager.getAllGreenTasks().size();
    int exchange = dataManager.getAllExchangeTasks().size();
    int common = dataManager.getAllCommonTasks().size();

    if (green > 0)
      pieChartData.add(new PieChart.Data("Grønne", green));
    if (exchange > 0)
      pieChartData.add(new PieChart.Data("Bytte", exchange));
    if (common > 0)
      pieChartData.add(new PieChart.Data("Fælles", common));

    pieChart.setData(pieChartData);
  }

  public void handleResetClimateScore()
  {
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Bekræft nulstilling");
    confirm.setHeaderText("Nulstil klimakapital");
    confirm.setContentText(
        "Er du sikker på, at du vil nulstille alle fælles point i klimakapitalen?");

    Optional<ButtonType> result = confirm.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK)
    {
      dataManager.resetClimateScore();
      climateScoreLbl.setText(
          dataManager.getClimateScore().getTotalGreenPoints() + "");
    }
  }

  public void handleAddBaseData()
  {
    Resident r1 = new Resident("Grønne Bob", 1.3);        // Kontaktperson
    Resident r2 = new Resident("Sofie", 1.0);
    Resident r3 = new Resident("Kasper", 1.0);
    Resident r4 = new Resident("Gitte", 1.1);
    Resident r5 = new Resident("William", 1.5);
    Resident r6 = new Resident("Ella", 1.5);
    Resident r7 = new Resident("Peter", 0.9);
    Resident r8 = new Resident("Oliver", 1.0);
    Resident r9 = new Resident("Anna", 1.0);
    Resident r10 = new Resident("Mads", 1.3);
    Resident r11 = new Resident("Alfred", 1.3);
    Resident r12 = new Resident("John", 1.0);
    Resident r13 = new Resident("Carl", 1.5);
    Resident r14 = new Resident("Solvej", 1.1);
    Resident r15 = new Resident("Mads", 1.0);
    Resident r16 = new Resident("Agnes", 1.5);
    Resident r17 = new Resident("Valdemar", 1.5);
    Resident r18 = new Resident("Malthe", 1.3);
    Resident r19 = new Resident("Sofie", 1.0);
    Resident r20 = new Resident("Thomas", 1.0);

    Task g1 = new GreenTask("Sorter dit madaffald",
        "Sorter dit madaffald til fælleskomposten.\n Sortér alt dit grønne madaffald fra i en hel uge og smid det på fælleskomposten i stedet, så det bliver holdt lokalt og kan være med i vores biodynamiske jordbrug.",
        35);

    Task g2 = new GreenTask("Cykel-ugen",
        "Drop bilen og tag din cykel i en hel uge i stedet for!", 70);

    Task g3 = new GreenTask("En uge på strøm-jagt",
        "Find og sluk for unødvendigt strømforbrug - fx kan du slukke for standbylys, når du ikke bruger et apparat, huske at slukke lyset når du forlader et rum etc.",
        25);

    Task g4 = new GreenTask("Donér til genbrug eller Byttebiksen",
        "Aflever et stykke tøj eller en genstand til genbrug eller vores allesammens Byttebiks.",
        30);

    Task g5 = new GreenTask("Reparer dit tøj",
        "Lap huller og fix dit slidte tøj, så det kan klare sig lidt længere endnu.",
        45);

    Task g6 = new GreenTask("Ryd ud i din mailboks",
        "Slet alle unødvendige og gamle mails, der bare ligger og fylder et sted i skyen.",
        20);

    Task c1 = new CommonTask("Hovedrengøring i køkkenet",
        "Rengør fælleskøkkenet grundigt: \n- Tøm og vask køleskabene\n- Kør pyrolyse på ovnen og tør den af\n- Støv af på hylder\n- Vask gulvet grundigt\n- Kig lagervarer igennem for dato-varer og stil dem frem",
        40, 5   // op til 5 personer
    );

    Task c2 = new CommonTask("Madtjans: Mandag",
        "Madlavning og oprydning efter madlavning.\n\nHusk at der skal vaskes gulv hver aften!",
        20, 2   // 2 personer kan deles om det
    );

    Task c3 = new CommonTask("Rengøring af fællesarealer: Uge 13",
        "- Vask trapper og gelændre\n- Tør alle overflader af med en fugtig klud\n- Vask gulve\n- Vask toiletter\n- Ryd op og fjern rod\n- Tøm skraldespande",
        30, 4);

    Task c4 = new CommonTask("Lug ukrudt",
        "Lug ukrudt på fælles-terassen og i fællesbedene.\nTag et par timer og hjælp os alle ved at få luget ud i ukrudt på fællesterassen og bedene",
        25, 2);

    Task c5 = new CommonTask("Planlæg madplan og indkøb",
        "Lav madplan for den kommende uge til fællesspisningen og sørg for indkøb og sortering i kasser til ugedagene.",
        120, 2);

    Task c6 = new CommonTask("Vask vinduer på fælleshuset",
        "Puds vinduer i fællesarealer inde eller ude.", 35, 3);

    Task c7 = new CommonTask("Pas køkkenhaven",
        "Læg en times arbejde i pasningen af være fælles køkkenhave.", 10, 100);

    Task e1 = new ExchangeTask("Friske gulerødder",
        "Jeg sælger 2 poser friske, økologiske gulerødder fra min køkkenhave",
        15, 2, r5);

    Task e2 = new ExchangeTask("Yoga-session for begyndere på tirsdag",
        "Rolig yoga-session på 60 minutter for begyndere i fælleshuset", 150, 6,
        r2);

    Task e3 = new ExchangeTask("Cykelservice",
        "Jeg hjælper med justering af bremser og gear", 50, 3, r3);

    Task e4 = new ExchangeTask("Planter til udplantning",
        "Overskudsplanter klar til udplantning", 5, 6, r1);

    Task e5 = new ExchangeTask("Hundeluftning",
        "Vi vil gerne lufte din hund, hvis du ikke er hjemme. Det koster 10 rigtige penge. Vi sparer op til en pony.\n- Ella og Agnes",
        0, 20, r16);

    List<Resident> residents = List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10,
        r11, r12, r13, r14, r15, r16, r17, r18, r19, r20);
    residents.forEach(r -> dataManager.addResident(r));

    List<Task> tasks = List.of(
        // Grønne opgaver
        g1, g2, g3, g4, g5, g6,

        // Fællesopgaver
        c1, c2, c3, c4, c5, c6, c7,

        // Bytteopgaver
        e1, e2, e3, e4, e5);

    tasks.forEach(t -> dataManager.addTask(t));

    loadStats();
    ViewManager.updateExternalView();
  }
}
