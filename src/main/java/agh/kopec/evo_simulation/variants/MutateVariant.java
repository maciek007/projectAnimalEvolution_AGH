package agh.kopec.evo_simulation.variants;

import agh.kopec.evo_simulation.MutateFullRandom;
import agh.kopec.evo_simulation.MutateManager;
import agh.kopec.evo_simulation.MutateSlightChange;

public enum MutateVariant {
    FullRandom,
    SlightChange;

    public static MutateVariant fromString(String v) throws IllegalArgumentException{
        return switch (v.trim()) {
            case "FullRandom" -> FullRandom;
            case "SlightChange" -> SlightChange;
            default -> throw new IllegalArgumentException("Unrecognized genesMutateVariant\n" +
                                                          "Expected: FullRandom | SlightChange\n" +
                                                          "Read: " + v);
        };
    }

    public MutateManager getManager(int minMutate, int maxMutate)
    {
        return switch (this)
        {
            case FullRandom ->new MutateFullRandom(minMutate, maxMutate);
            case SlightChange ->new MutateSlightChange(minMutate, maxMutate);
        };
    }
}
