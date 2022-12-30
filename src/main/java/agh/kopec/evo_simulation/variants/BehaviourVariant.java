package agh.kopec.evo_simulation.variants;

import agh.kopec.evo_simulation.BehaviourManagerAlwaysNext;
import agh.kopec.evo_simulation.BehaviourManagerRandomNext;
import agh.kopec.evo_simulation.IBehaviourManager;

public enum BehaviourVariant {
    PredestinedBehaviour,
    RandomOrderBehaviour;

    public static BehaviourVariant fromString(String v) throws IllegalArgumentException{
        return switch (v.trim()) {
            case "PredestinedBehaviour" -> PredestinedBehaviour;
            case "RandomOrderBehaviour" -> RandomOrderBehaviour;
            default -> throw new IllegalArgumentException("Unrecognized AnimalsBehaviourVariant\n" +
                                                          "Expected: PredestinedBehaviour | RandomOrderBehaviour\n" +
                                                          "Read: " + v);
        };
    }

    public IBehaviourManager getManager()
    {
        return switch (this)
                {
                    case PredestinedBehaviour ->new BehaviourManagerAlwaysNext();
                    case RandomOrderBehaviour ->new BehaviourManagerRandomNext();
                };
    }
}
