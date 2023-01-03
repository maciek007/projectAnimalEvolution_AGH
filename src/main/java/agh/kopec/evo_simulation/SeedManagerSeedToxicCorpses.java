package agh.kopec.evo_simulation;

import java.util.*;

public class SeedManagerSeedToxicCorpses extends SeedManager {
    public SeedManagerSeedToxicCorpses(MapManager map, int plantsStartingAmount, int plantsDailyIncrease) {
        super(map, plantsDailyIncrease);

        for(int i = 0; i<map.dimensions.x; i++)
            for(int j = 0; j<map.dimensions.y; j++)
                free_notFertileMap.add(new Vector2d(i,j));

        updateFertile();
        seed(plantsStartingAmount);
    }

    @Override
    protected void updateFertile() {
        int n = map.dimensions.x * map.dimensions.y;
        Set<Vector2d> free_Map = new HashSet<>();

        free_Map.addAll(free_fertileMap);
        free_Map.addAll(free_notFertileMap);
        fertileMap.clear();
        notFertileMap.clear();
        free_fertileMap.clear();
        free_notFertileMap.clear();

        int a = Math.round(n / 5f);

        List <Map.Entry<Vector2d, Integer>> fertileRank = new ArrayList<>(map.corpses.entrySet());

        fertileRank.sort(Map.Entry.comparingByValue());

        Iterator<Map.Entry<Vector2d, Integer>> i = fertileRank.iterator();
        int j =0;
        while (i.hasNext()) {
            Vector2d value =  i.next().getKey();

                if (j < a) {
                    if(free_Map.contains(value))
                        free_fertileMap.add(value);
                    fertileMap.add(value);
                }
                else {
                    if(free_Map.contains(value))
                        free_notFertileMap.add(value);
                    notFertileMap.add(value);
                }
            j++;
        }
    }
}
