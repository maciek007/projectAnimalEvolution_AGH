package agh.kopec.evo_simulation;

public class AnimalStats {
    public int energy;
    public final int born;
    public int death;
    public int children;
    public int plantsEaten;

    public AnimalStats(Integer energy, Integer born) {
        this.energy = energy;
        this.born = born;
        this.children = 0;
        this.plantsEaten = 0;
        this.death = -1;
    }
}
