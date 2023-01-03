package agh.kopec.evo_simulation;

import java.util.Random;

public class MutateSlightChange extends MutateManager {
    Random rand;
    public MutateSlightChange(int minMutate, int maxMutate) {
        super(minMutate, maxMutate);
        rand = new Random();
    }

    @Override
    protected short chooseNewGene(short oldGene){
        boolean b = rand.nextBoolean();
        return (short)(Math.floorMod(oldGene + (b?1:-1),8));
    }
}