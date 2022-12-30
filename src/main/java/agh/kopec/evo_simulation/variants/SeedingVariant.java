package agh.kopec.evo_simulation.variants;

import agh.kopec.evo_simulation.*;

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

    public SeedManager getManager()
    {
        return switch (this)
        {
            case FertileEquators -> new SeedManagerSeedEquators();
            case ToxicCorpses -> new SeedManagerSeedToxicCorpses();
        };
    }

}
