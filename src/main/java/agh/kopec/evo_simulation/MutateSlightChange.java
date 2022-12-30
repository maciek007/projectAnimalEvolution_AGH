package agh.kopec.evo_simulation;

import java.util.Random;

public class MutateSlightChange extends MutateManager {
    Random rand;
    public MutateSlightChange(Integer minMutate, Integer maxMutate) {
        super(minMutate, maxMutate);
        rand = new Random();
    }

    @Override
    protected short chooseNewGene(short oldGene, short n){
        boolean b = rand.nextBoolean();
        return (short)((oldGene + (b?1:-1))%n);
    }
}