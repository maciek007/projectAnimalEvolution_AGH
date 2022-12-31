package agh.kopec.evo_simulation.variants;

import agh.kopec.evo_simulation.MapBorders;
import agh.kopec.evo_simulation.MapBordersGlobe;
import agh.kopec.evo_simulation.MapBordersHellPortal;
import agh.kopec.evo_simulation.Vector2d;

public enum MapVariant {
    Globe,
    HellPortal;
    public static MapVariant fromString(String v) throws IllegalArgumentException
    {
        return switch (v.trim()) {
            case "Globe" -> Globe;
            case "HellPortal" -> HellPortal;
            default -> throw new IllegalArgumentException("Unrecognized MapVariant\n" +
                                                          "Expected: Globe | HellPortal\n" +
                                                          "Read: " + v);
        };
    }
    public MapBorders getManager(Vector2d mapDimensions)
    {

        return switch (this)
        {
            case Globe ->new MapBordersGlobe(mapDimensions);
            case HellPortal ->new MapBordersHellPortal(mapDimensions);
        };
    }
}
