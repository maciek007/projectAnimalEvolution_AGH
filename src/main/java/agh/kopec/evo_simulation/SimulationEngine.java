package agh.kopec.evo_simulation;


import agh.kopec.evo_simulation.gui.App;

import java.util.Set;

public class SimulationEngine implements Runnable{

    private final MapManager map;
    private final AnimalsManager animalsManager;

    private final int baseMoveDelay;
    final boolean saveToCSV;
    int speed;
    private final App app = null;
    private final int moveDelay;

    public final SimulationStats simulationStats;



    public SimulationEngine(Configuration config, boolean saveToCSV) {

        this.baseMoveDelay = ApplicationConfig.BASE_ENGINE_INTERVAL;
        this.moveDelay = baseMoveDelay;
        this.saveToCSV = saveToCSV;
        this.simulationStats = new SimulationStats(config.mapDimensions);

        this.map = new MapManager(config, simulationStats);
        this.animalsManager = new AnimalsManager(config,map, simulationStats);
        this.speed = 0;
    }

    private void dayCycle()
    {
        animalsManager.removeDeath();
        animalsManager.animalsMovement();
        animalsManager.animalsEatingAndBreeding();
        map.seed();
        animalsManager.consumeEnergy();
        simulationStats.endDay();
        simulationStats.day+=1;

        Set<Vector2d> pos = animalsManager.getPositions();
        pos.addAll(map.seedManager.free_notFertileMap);
        pos.addAll(map.seedManager.free_fertileMap);
        simulationStats.freeFields = simulationStats.fields - pos.size();

        animalsManager.findMostCommonGenotype();
    }

    @Override
    public void run(){
        try {
            Thread.sleep(moveDelay);
        }
        catch (InterruptedException e)
        {
            System.out.print("Wątek symulacji nieoczekiwanie został zamknięty");
        }
    }
}
