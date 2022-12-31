package agh.kopec.evo_simulation.variants;

import agh.kopec.evo_simulation.MapManager;
import agh.kopec.evo_simulation.SeedManager;
import agh.kopec.evo_simulation.SeedManagerSeedEquators;
import agh.kopec.evo_simulation.SeedManagerSeedToxicCorpses;

public enum SeedingVariant {
    FertileEquators,
    ToxicCorpses;

    public static SeedingVariant fromString(String v) throws IllegalArgumentException{
        return switch (v.trim()) {
            case "FertileEquators" -> FertileEquators;
            case "ToxicCorpses" -> ToxicCorpses;
            default -> throw new IllegalArgumentException("Unrecognized SeedingVariant\n" +
                                                          "Expected: FertileEquators | ToxicCorpses\n" +
                                                          "Read: " + v);
        };
    }

    public SeedManager getManager(int plantsStartingAmount, int plantsDailyIncrease, MapManager map)
    {
        return switch (this)
        {
            case FertileEquators -> new SeedManagerSeedEquators(map,plantsStartingAmount, plantsDailyIncrease);
            case ToxicCorpses -> new SeedManagerSeedToxicCorpses(map,plantsStartingAmount, plantsDailyIncrease);
        };
    }

}
