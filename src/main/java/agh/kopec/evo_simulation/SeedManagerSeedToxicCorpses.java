package agh.kopec.evo_simulation;

import java.util.*;

public class SeedManagerSeedToxicCorpses extends SeedManager {
    public SeedManagerSeedToxicCorpses(MapManager map, int plantsStartingAmount, int plantsDailyIncrease) {
        super(map, plantsDailyIncrease);
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

        SortedMap<Integer, Vector2d> fertileRank = new TreeMap<>();

        for (Map.Entry<Vector2d, Integer> entry : map.corpses.entrySet()) {
            fertileRank.put(entry.getValue(), entry.getKey());
        }

        Iterator<Map.Entry<Integer, Vector2d>> i = fertileRank.entrySet().iterator();
        int j =0;
        while (i.hasNext()) {
            Vector2d value =  i.next().getValue();

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
