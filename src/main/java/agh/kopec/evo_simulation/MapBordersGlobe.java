package agh.kopec.evo_simulation;

public class MapBordersGlobe extends MapBorders{
    public MapBordersGlobe(Vector2d dimensions) {
        super(dimensions);
    }

    @Override
    public void tryMoveTo(Animal animal, Vector2d newPosition) {

        if(newPosition.x >= dimensions.x)
            newPosition.x -= dimensions.x;
        if(newPosition.x < 0)
            newPosition.x = dimensions.x - newPosition.x;

        if(newPosition.y >= dimensions.y) {
            newPosition.y = dimensions.y - 1;
            animal.orientation = animal.orientation.opposite();
        }
        if(newPosition.y < 0){
            newPosition.y = 0;
            animal.orientation = animal.orientation.opposite();
        }

        animal.position = newPosition;
    }
}
