package agh.kopec.evo_simulation;

import java.util.Set;

public class SeedManagerSeedEquators extends SeedManager {

    public SeedManagerSeedEquators(MapManager map, int plantsStartingAmount, int plantsDailyIncrease) {
        super(map, plantsDailyIncrease);
        int equators = (int) Math.ceil(map.dimensions.y / 5f);
        if(equators%2 == 1 && map.dimensions.y%2 == 0) equators +=1;
        else if(equators%2 == 0 && map.dimensions.y%2 == 1) equators+=1;

        int j = 0;

        for(int i = (map.dimensions.y - equators) / 2; i<(map.dimensions.y + equators) / 2; i++) {
            for (int ii = 0; ii < map.dimensions.x; ii++) {
                fertileMap.add(new Vector2d(ii, i));
                j += 1;
                if (j == plantsStartingAmount)
                    notFertileMap.add(new Vector2d(ii, i));
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

        free_fertileMap = Set.copyOf(fertileMap);
        free_notFertileMap = Set.copyOf(notFertileMap);

        seed(plantsStartingAmount);
    }

    @Override
    protected void updateFertile() {}
}