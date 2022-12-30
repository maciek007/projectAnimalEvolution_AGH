package agh.kopec.evo_simulation;

public abstract class MapBorders {
    Vector2d dimensions;
    public MapBorders(Vector2d dimensions) {
        this.dimensions = dimensions;
    }

    public abstract void tryMoveTo(Animal animal, Vector2d newPosition);
}
