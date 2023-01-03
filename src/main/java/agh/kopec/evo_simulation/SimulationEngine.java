package agh.kopec.evo_simulation;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;

public class SimulationEngine{

    final MapManager map;
    public final AnimalsManager animalsManager;

    public final SimulationStats simulationStats;
    private final MapVisualizer mapVisualizer;



    public SimulationEngine(Configuration config, HBox animalTracker) throws FileNotFoundException {
        this.simulationStats = new SimulationStats(config.mapDimensions);
        this.map = new MapManager(config, simulationStats);
        this.animalsManager = new AnimalsManager(config,map, simulationStats);

        this.mapVisualizer = new MapVisualizer(map,animalsManager, animalTracker);

        simulationStats.endDay();
        animalsManager.findMostCommonGenotype();
        //calcPos();
    }

    public void dayCycle()
    {
        animalsManager.removeDeath();
        animalsManager.animalsMovement();
        animalsManager.animalsEatingAndBreeding();
        map.seed();
        animalsManager.consumeEnergy();

        simulationStats.endDay();
        animalsManager.findMostCommonGenotype();
        //calcPos();
        simulationStats.day += 1;
    }

    public void visualize(GridPane gridPane){
        mapVisualizer.drawGrid(gridPane);
    }

    public GridPane initGrid() {
       return mapVisualizer.initGrid();
    }
}
