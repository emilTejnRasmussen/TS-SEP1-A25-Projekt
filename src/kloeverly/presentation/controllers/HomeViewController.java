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
    @FXML
    private PieChart pieChart;
    @FXML
    private Button runExternalScreenBtn;
    @FXML
    private Label residentAmountLbl;
    @FXML
    private Label greenTaskAmountLbl;
    @FXML
    private Label commonTaskAmountLbl;
    @FXML
    private Label exchangeTaskAmountLbl;
    @FXML
    private Label climateScoreLbl;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        loadStats();
        if (ViewManager.getExternalStage() != null){
            runExternalScreenBtn.setDisable(true);
        }
    }

    public void handleResetResidentPoints()
    {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft nulstilling");
        confirm.setHeaderText("Nulstil individuelle point");
        confirm.setContentText("Er du sikker på, at du vil nulstille alle beboers individuelle point?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            dataManager.resetPointsForAllResidents();
            ViewManager.updateExternalView();
        }
    }

    public void handleShowExternalScreen()
    {
        if (ViewManager.getExternalStage() != null) return;
        ViewManager.showExternalScreen(runExternalScreenBtn);
        runExternalScreenBtn.setDisable(true);
    }

    private void loadStats()
    {
        climateScoreLbl.setText(dataManager.getClimateScore().getTotalGreenPoints() + "");
        residentAmountLbl.setText(dataManager.getAllResidents().size() + "");
        greenTaskAmountLbl.setText(dataManager.getAllGreenTasks().size() + "");
        commonTaskAmountLbl.setText(dataManager.getAllCommonTasks().size() + "");
        exchangeTaskAmountLbl.setText(dataManager.getAllExchangeTasks().size() + "");

        createPieChart();
    }

    private void createPieChart()
    {
        pieChart.setAnimated(false);
        pieChart.getData().clear();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int green = dataManager.getAllGreenTasks().size();
        int exchange = dataManager.getAllExchangeTasks().size();
        int common = dataManager.getAllCommonTasks().size();

        if (green > 0)  pieChartData.add(new PieChart.Data("Grønne", green));
        if (exchange > 0) pieChartData.add(new PieChart.Data("Bytte", exchange));
        if (common > 0) pieChartData.add(new PieChart.Data("Fælles", common));


        pieChart.setData(pieChartData);
    }

    public void handleResetClimateScore()
    {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft nulstilling");
        confirm.setHeaderText("Nulstil klimakapital");
        confirm.setContentText("Er du sikker på, at du vil nulstille alle fælles point i klimakapitalen?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            dataManager.resetClimateScore();
            climateScoreLbl.setText(dataManager.getClimateScore().getTotalGreenPoints() + "");
        }
    }

    public void handleAddBaseData()
    {
        Resident r1 = new Resident("Grønne Bob", 1.3);        // Kontaktperson
        Resident r2 = new Resident("Solskin Sofie", 1.0);
        Resident r3 = new Resident("Cykel-Kasper", 1.2);
        Resident r4 = new Resident("Genbrugs-Gitte", 1.1);
        Resident r5 = new Resident("Have-Henrik", 1.0);
        Resident r6 = new Resident("Strøm-Signe", 1.2);
        Resident r7 = new Resident("Plante-Peter", 0.9);
        Resident r8 = new Resident("Øko-Oliver", 1.1);
        Resident r9 = new Resident("Affalds-Anna", 1.0);
        Resident r10 = new Resident("Miljø-Mads", 1.3);

        Task g1 = new GreenTask(
                "Bob's affaldsmission",
                "Sortér affald korrekt i hele bygningen",
                35
        );

        Task g2 = new GreenTask(
                "Cykel-ugen",
                "Drop bilen og cykl i en hel uge",
                50
        );

        Task g3 = new GreenTask(
                "Strøm-jagten",
                "Find og sluk unødvendigt strømforbrug",
                25
        );

        Task g4 = new GreenTask(
                "Genbrugsgarderoben",
                "Aflever tøj til genbrug eller bytte",
                30
        );

        Task g5 = new GreenTask(
                "Grøn gård",
                "Plant og plej nye planter i gården",
                45
        );

        Task c1 = new CommonTask(
                "Køkkenkaos-kontrol",
                "Rengør fælleskøkkenet grundigt",
                40
        );

        Task c2 = new CommonTask(
                "Opvaske-helten",
                "Tøm og ryd op i opvaskemaskinen",
                20
        );

        Task c3 = new CommonTask(
                "Trappe-tjansen",
                "Vask trapper og gelænder",
                30
        );

        Task c4 = new CommonTask(
                "Affaldsrum-redning",
                "Ryd op og vask affaldsrummet",
                25
        );

        Task c5 = new CommonTask(
                "Indkøbs-inspektør",
                "Køb fælles rengøringsmidler",
                20
        );

        Task c6 = new CommonTask(
                "Kig-klart vinduer",
                "Puds vinduer i fællesarealer",
                35
        );

        Task c7 = new CommonTask(
                "Gårdens vogter",
                "Fej og ryd gården for skrald",
                25
        );

        Task e1 = new ExchangeTask(
                "Friske gulerødder",
                "Jeg sælger 10 friske, økologiske gulerødder fra fælleshaven",
                15,          // point pr. stk
                10,          // antal
                r5           // Have-Henrik
        );

        Task e2 = new ExchangeTask(
                "Yoga-session",
                "Rolig yoga-session på 60 minutter for begyndere",
                150,         // point pr. session
                1,           // antal sessioner
                r2           // Solskin Sofie
        );

        Task e3 = new ExchangeTask(
                "Cykelreparation",
                "Jeg hjælper med justering af bremser og gear",
                50,          // point pr. reparation
                3,           // antal
                r3           // Cykel-Kasper
        );

        Task e4 = new ExchangeTask(
                "Planteplanter",
                "Overskudsplanter klar til udplantning",
                25,          // point pr. plante
                6,           // antal
                r1           // Grønne Bob
        );

        List<Resident> residents = List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
        residents.forEach(r -> dataManager.addResident(r));

        List<Task> tasks = List.of(
                // Grønne opgaver
                g1,
                g2,
                g3,
                g4,
                g5,

                // Fællesopgaver
                c1,
                c2,
                c3,
                c4,
                c5,
                c6,
                c7,

                // Bytteopgaver
                e1,
                e2,
                e3,
                e4
        );

        tasks.forEach(t -> dataManager.addTask(t));

        loadStats();
        ViewManager.updateExternalView();
    }
}
