package agh.kopec.evo_simulation;

import agh.kopec.evo_simulation.utility.MultiRandom;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class SeedManager {
    final MapManager map;
    final SimulationStats stats;
    final int plantsDailyIncrease;
    final boolean [][] plantMap;
    final Set <Vector2d> fertileMap;
    final Set <Vector2d> notFertileMap;

    Set <Vector2d> free_fertileMap = new HashSet<>();
    Set <Vector2d> free_notFertileMap = new HashSet<>();

    public void seed()
    {
        updateFertile();
        seed(plantsDailyIncrease);
    }

    protected abstract void updateFertile();

    public SeedManager(MapManager map, int plantsDailyIncrease) {
        this.map = map;
        this.plantsDailyIncrease = plantsDailyIncrease;
        this.stats = map.simulationStats;
        this.plantMap = new boolean[map.dimensions.x][map.dimensions.y];
        fertileMap = new HashSet<>();
        notFertileMap = new HashSet<>();
    }

    public boolean findAndEatPlant(Vector2d position) {
        if(plantMap[position.x][position.y])
        {
            plantMap[position.x][position.y] = false;
            Vector2d v1 = new Vector2d(position.x, position.y);
            if(fertileMap.contains(v1))
                free_fertileMap.add(v1);
            else
                free_notFertileMap.add(v1);
            stats.plants_n -= 1;

            return true;
        }
        return false;
    }

    protected void seed(int n) {
        int a = Math.round(n*4f/5f);
        int b = n-a;

        int fMsize = free_fertileMap.size();
        int fNMsize = free_notFertileMap.size();

        if(a>=fMsize)
        {
            a = fMsize;
            b = Math.min(n - a, fNMsize);
        }
        else if(b>= fNMsize)
        {
            b = fNMsize;
            a = Math.min(n - b, fMsize);
        }


        seedOn(free_fertileMap, a);
        seedOn(free_notFertileMap, b);
        stats.plants_n+=a+b;
    }
    public void seedOn(Set <Vector2d> free_fields, int n)
    {
        MultiRandom random = new MultiRandom();
        int f_size = free_fields.size();
        if(n == f_size) {
            free_fields.forEach(vector -> plantMap[vector.x][vector.y] = true);
            free_fields.clear();
        }
        else
        {
            boolean [] choosen = random.rand_table(n,f_size);
            LinkedList<Vector2d> vs = new LinkedList<>();

            int j = 0;
            for(Vector2d f : free_fields)
            {
                if(choosen[j])
                {
                    vs.add(f);
                    plantMap[f.x][f.y] = true;
                }
                j+=1;
            }

            //System.out.println(vs.size() + " " + free_fields.size());
            vs.forEach(free_fields::remove);
            //System.out.println(free_fields.size() + "\n");
        }
    }
}
