package agh.kopec.evo_simulation.gui;

import agh.kopec.evo_simulation.ApplicationConfig;
import agh.kopec.evo_simulation.Configuration;
import agh.kopec.evo_simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimulationController {
    final public String simulationName;
    final Configuration configuration;
    final boolean saveToCSV;
    final SimulationEngine simulationEngine;
    private final Integer baseMoveDelay;

    final HBox animalStats;
    final VBox wordStats_values = new VBox();
    final GridPane map;

    final Stage simWindow;
    private PrintWriter pw;
    private FileWriter file;
    ScheduledThreadPoolExecutor simulationExecutor;

    public SimulationController(String simulationName, Configuration configuration, boolean saveToCSV) {
        this.simulationName = simulationName;
        this.configuration = configuration;
        this.saveToCSV = saveToCSV;

        VBox animalStats_description = new VBox(
                new Label("Born at day: "),
                new Label("Energy: "),
                new Label("Children: "),
                new Label("Plants eaten: "),
                new Label("Genome: "),
                new Label("Index of active gen: ")
        );
        VBox animalStats_values = new VBox(new Label("No animal selected"));
        animalStats = new HBox(animalStats_description,animalStats_values);


        try {
            simulationEngine = new SimulationEngine(configuration, animalStats);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error drawing map");
            alert.setContentText("Cannot find resources");
            alert.showAndWait();
            throw new RuntimeException();
        }

        this.baseMoveDelay = ApplicationConfig.BASE_ENGINE_INTERVAL;

        simWindow = new Stage();
        simWindow.setTitle(simulationName);

        map = simulationEngine.initGrid();
        simulationEngine.visualize(map);

        VBox wordStats_description = new VBox(
                new Label("Day: "),
                new Label("Life animals: "),
                new Label("Corpses: "),
                new Label("Plants: "),
                new Label("Free fields: "),
                new Label("Free fields for plants: "),
                new Label("Famous genotype: "),
                new Label("Average life animals energy: "),
                new Label("Average dead animals lifespan: ")
        );
        simulationEngine.simulationStats.redrawVBox(wordStats_values);
        HBox wordStats = new HBox(wordStats_description,wordStats_values);


        Tab word_stats = new Tab("word", wordStats);
        Tab animal_stats = new Tab("animal", animalStats);
        word_stats.setClosable(false);
        animal_stats.setClosable(false);

        TabPane stats = new TabPane(word_stats,animal_stats);

        Button Pause = new Button("II");
        Button Speed1 = new Button(">");
        Button Speed2 = new Button(">>");
        Button Speed4 = new Button(">>>");
        Button SpeedRT = new Button("RealTime");

        simulationExecutor = new ScheduledThreadPoolExecutor(2);

        Pause.setOnAction(e ->  setSpeedAndPlay(0));
        Speed1.setOnAction(e -> setSpeedAndPlay(baseMoveDelay));
        Speed2.setOnAction(e -> setSpeedAndPlay(baseMoveDelay / 4));
        Speed4.setOnAction(e -> setSpeedAndPlay(baseMoveDelay / 16));
        SpeedRT.setOnAction(e -> setSpeedAndPlay(ApplicationConfig.REAL_TIME_ENGINE_INTERVAL));



        HBox options = new HBox(Pause, Speed1, Speed2, Speed4, SpeedRT);

        VBox info = new VBox(options, stats);
        HBox map_info = new HBox(map, info);

        Scene scene = new Scene(map_info);
        simWindow.setScene(scene);
        simWindow.show();
        simWindow.setOnCloseRequest((e)->
        {

            simulationExecutor.shutdownNow();
            simWindow.close();
            App.removeSimulation(this);
            if(saveToCSV)
            {
                pw.close();
                try {
                    file.close();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Cannot close csv");
                    alert.show();
                    throw new RuntimeException(ex);
                }
            }
        }
        );

        if(saveToCSV)
        {
            try {
                file = new FileWriter(simulationName+".csv");
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Cannot create csv");
                alert.show();
                throw new RuntimeException();
            }
            pw = new PrintWriter(file);
            pw.println("Day;Alive animals;Corpses;Plants;Free fields for plants;Free fields;Famous Genotype; Corpses;Average alive animals energy;Average death lifespan");
        }
    }

    private ScheduledThreadPoolExecutor setSpeedAndPlay(Integer speed)
    {
        simulationExecutor.shutdown();
        //System.out.println("InFunc - " + speed);
        try {
            if(simulationExecutor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                if (speed != 0) {
                    simulationExecutor = new ScheduledThreadPoolExecutor(2);

                    Runnable runnable = () -> {
                        Platform.runLater(() ->
                        {
                            simulationEngine.dayCycle();
                            this.simulationEngine.simulationStats.redrawVBox(wordStats_values);
                            this.simulationEngine.visualize(map);
                            if (simulationEngine.animalsManager.trackedAnimal != null)
                                simulationEngine.animalsManager.trackedAnimal.redrawHBox(animalStats);
                            //System.out.println("In cycle");
                            if(saveToCSV)
                                pw.println(simulationEngine.simulationStats.CSVLine());
                        });
                    };
                    simulationExecutor.scheduleWithFixedDelay(runnable, 0, speed, TimeUnit.MILLISECONDS);
                }
            }
            else
            {
                System.out.println("Failed to stop");
            }
        }
        catch (InterruptedException  e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unexpected error when trying to change speed");
            alert.show();
            throw new RuntimeException(e);
        }
        return simulationExecutor;
    }
}


