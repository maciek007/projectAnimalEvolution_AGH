package agh.kopec.evo_simulation;

import java.util.Random;

public class MapBordersHellPortal extends MapBorders{
    Random rand = new Random();

    public MapBordersHellPortal(Vector2d dimensions) {
        super(dimensions);
    }

    @Override
    public void tryMoveTo(Animal animal, Vector2d newPosition) {

        if(newPosition.x >= dimensions.x ||
            newPosition.x < 0 ||
            newPosition.y >= dimensions.y ||
            newPosition.y < 0) {

            newPosition = new Vector2d(rand.nextInt(dimensions.x), rand.nextInt(dimensions.y));
            animal.burnEnergy();
        }

        animal.position = newPosition;
    }
}
