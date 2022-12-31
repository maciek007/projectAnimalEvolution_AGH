package agh.kopec.evo_simulation;

import java.util.Random;

public class Animal extends AbstractMapElement{
    MoveDirection orientation;

    public AnimalStats animalStats;

    public Genome genome;
    short activeGen;
    float energyBurn;

    @Override
    public int compareTo(Object o) {
        int r = super.compareTo(o);
        if (r == 0) {
            if (o instanceof Animal that) {
                if (this.animalStats.energy != that.animalStats.energy)
                    return this.animalStats.energy - that.animalStats.energy;
                if (this.animalStats.born != that.animalStats.born)
                    return that.animalStats.born - this.animalStats.born;
                if (this.animalStats.children != that.animalStats.children)
                    return this.animalStats.children - that.animalStats.children;
                else return 0;
            }
        }
        return -1;
    }


    public Animal(Vector2d initialPosition, int energy, Genome genome, int born, float energyBurn) {
        this.position = new Vector2d(initialPosition);
        this.animalStats = new AnimalStats(energy, born);
        this.genome = genome;
        this.energyBurn = energyBurn;

        Random rand = new Random();
        activeGen = (short)(rand.nextInt(genome.numberOfGenes));
    }
    
    public void move(IBehaviourManager behaviourManager, MapManager map)
    {
        activeGen = behaviourManager.chooseNewGene(activeGen,genome.numberOfGenes);
        orientation.rotate(genome.genome[activeGen]);
        map.tryMoveTo(this,position.add(orientation.toUnitVector()));
    }

    public void updateEnergy()
    {
        animalStats.energy -=1;
    }
    public int burnEnergy()
    {
        int energyBurned = Math.round(animalStats.energy * energyBurn);
        animalStats.energy -= energyBurned;
        return energyBurned;
    }

    public void eat(int plantEnergy) {
        animalStats.energy+=plantEnergy;
    }
}
