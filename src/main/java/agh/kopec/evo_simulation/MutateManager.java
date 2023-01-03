package agh.kopec.evo_simulation;

import agh.kopec.evo_simulation.utility.MultiRandom;

import java.util.Random;

public abstract class MutateManager {

    int minMutate;
    int maxMutate;

    public void mutate(Genome old)
    {
        Random rand = new Random();
        int count = rand.nextInt(maxMutate - minMutate) + minMutate;
        MultiRandom multiRandom = new MultiRandom();
        for(Integer r : multiRandom.rand(count,old.numberOfGenes))
        {
            old.genome[r] = chooseNewGene(old.genome[r]);
        }
    }

    protected abstract short chooseNewGene(short oldGene);
    public MutateManager(int minMutate, int maxMutate)
    {
        this.minMutate = minMutate;
        this.maxMutate = maxMutate;
    }
}
