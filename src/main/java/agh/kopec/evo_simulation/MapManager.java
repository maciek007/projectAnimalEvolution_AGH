package agh.kopec.evo_simulation;

import java.util.HashMap;
import java.util.Map;

public class MapManager {

    final SeedManager seedManager;
    private final MapBorders mapBorders;
    final Vector2d dimensions;
    final SimulationStats simulationStats;
    protected final Map <Vector2d, Integer> corpses;

    public MapManager(Configuration conf, SimulationStats simulationStats) {
        this.simulationStats = simulationStats;
        this.seedManager = conf.seedingVariant.getManager(conf.plantsStartingAmount, conf.plantsDailyIncrease, this);
        this.mapBorders = conf.mapVariant.getManager(conf.mapDimensions);
        this.dimensions = conf.mapDimensions;

        corpses = new HashMap<>(conf.mapDimensions.x * conf.mapDimensions.y);
        for(int i=0;i< dimensions.x;i++)
            for(int j=0;j< dimensions.y; j++)
                corpses.put(new Vector2d(i,j),0);
    }

    public void tryMoveTo(Animal a, Vector2d newPosition)
    {
        mapBorders.tryMoveTo(a,newPosition);
    }

    public void seed()
    {
        seedManager.seed();
    }

    public void addCorpse(Animal animal) {
        int deaths = corpses.remove(animal.position);
        corpses.put(animal.position,deaths+1);

    }

    public boolean findFood(Animal animal) {
        return seedManager.findAndEatPlant(animal.position);
    }

}
