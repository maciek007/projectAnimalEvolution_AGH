package agh.kopec.evo_simulation;

import java.util.Random;

public class MutateFullRandom extends MutateManager {
    Random rand;

    public MutateFullRandom(int minMutate, int maxMutate) {
        super(minMutate, maxMutate);
        rand = new Random();
    }

    @Override
    protected short chooseNewGene(short oldGene) {
        short newGene = (short) rand.nextInt(8);
        if (oldGene == newGene)
            newGene = (short) ((newGene + 1) % 8);
        return newGene;
    }
}