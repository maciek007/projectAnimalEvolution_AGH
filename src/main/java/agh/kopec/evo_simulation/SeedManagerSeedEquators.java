package agh.kopec.evo_simulation;

import java.util.HashSet;

public class SeedManagerSeedEquators extends SeedManager {

    public SeedManagerSeedEquators(MapManager map, int plantsStartingAmount, int plantsDailyIncrease) {
        super(map, plantsDailyIncrease);
        int fertile_num = map.dimensions.x * map.dimensions.y / 5;
        int equators = (int) Math.ceil(map.dimensions.y / 5f);
        if(equators%2 != map.dimensions.y%2) equators +=1;

        int j = 0;

        for(int i = (map.dimensions.y - equators) / 2; i<(map.dimensions.y + equators) / 2; i++) {
            for (int ii = 0; ii < map.dimensions.x; ii++) {
                if (j == fertile_num)
                    notFertileMap.add(new Vector2d(ii, i));
                else {
                    fertileMap.add(new Vector2d(ii, i));
                    j += 1;
                }

            }
        }

        for(int i = 0; i< (map.dimensions.y - equators) / 2; i++) {
            for (int ii = 0; ii < map.dimensions.x; ii++) {
                notFertileMap.add(new Vector2d(ii, i));
            }
        }
        for(int i =(map.dimensions.y + equators) / 2; i<map.dimensions.y; i++) {
            for (int ii = 0; ii < map.dimensions.x; ii++) {
                notFertileMap.add(new Vector2d(ii, i));
            }
        }

        free_fertileMap = new HashSet<>(fertileMap);
        free_notFertileMap = new HashSet<>(notFertileMap);

        seed(plantsStartingAmount);
    }

    @Override
    protected void updateFertile() {}
}