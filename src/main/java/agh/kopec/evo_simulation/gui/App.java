package agh.kopec.evo_simulation.gui;

import agh.kopec.evo_simulation.ConfigManager;
import agh.kopec.evo_simulation.Configuration;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    static private final List <SimulationController> runningSimulations = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        // visual section

        ComboBox configurations = new ComboBox();
        configurations.setPromptText("Choose Configuration");
        configurations.setAccessibleText("Choose Configuration");
        configurations.setMaxWidth(Double.MAX_VALUE);

        Button run = new Button("Run");
        run.setPrefSize(50,50);
        CheckBox saveToCSV = new CheckBox("Save statistic data to CSV?");

        TextField simulationName = new TextField();
        simulationName.setPromptText("SimulationName");


        VBox leftBox = new VBox(simulationName, configurations, saveToCSV);
        leftBox.setSpacing(10);

        HBox main = new HBox(leftBox, run);
        main.setAlignment(Pos.CENTER);
        main.setSpacing(20);
        main.setPadding(new Insets(10,10,10,10));


        //logic section

        ConfigManager configManager = new ConfigManager();
        configurations.setOnShowing(e->
        {
            try {
                configurations.getItems().clear();
                configurations.getItems().addAll(configManager.getFilesList());
            }
            catch(IOException exception)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error listing configurations");
                alert.setContentText("Cannot find \"configurations\" folder");
                alert.showAndWait();
            }
        });


        run.setOnAction(
                a -> {
                    int selectedIndex = configurations.getSelectionModel().getSelectedIndex();
                    if(selectedIndex == -1)
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText("Please choose configuration");
                        alert.showAndWait();
                        return;
                    }
                    Configuration conf = configManager.getConfiguration(selectedIndex);
                    if(conf == null) return;

                    String simNameString = simulationName.getText();
                    if(simNameString == "") simNameString = "SimulationName";

                    List<String>names = runningSimulations.stream().map(s->s.simulationName).toList();

                    if(names.contains(simNameString))
                    {
                        int j = 1;
                        while(names.contains((simNameString+"_"+ Integer.toString(j))))
                        {
                            j+=1;
                        }
                        simNameString = simNameString+"_"+ Integer.toString(j);
                    }

                    SimulationController sC = new SimulationController(simNameString, conf,saveToCSV.isSelected());
                    runningSimulations.add(sC);
                }
        );



        primaryStage.setTitle("Evolution Simulation Engine");
        Scene mainScene = new Scene(main);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        main.requestFocus();
    }
    static public void removeSimulation(SimulationController s)
    {
        runningSimulations.remove(s);
    }
}
