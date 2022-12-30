package agh.kopec.evo_simulation;

import java.util.Random;

public abstract class MutateManager {

    int minMutate;
    int maxMutate;
    Random rand = new Random();

    public void mutate(Genome old)
    {
        boolean [] mutatedGenes = new boolean[old.numberOfGenes];
        Random rand = new Random();
        int count = rand.nextInt(maxMutate - minMutate) + minMutate;
        for(int i = 0; i<count; i++)
        {
            int r = newFree(mutatedGenes);
            old.genome[r] = chooseNewGene(old.genome[r],old.numberOfGenes);

        }
    }

    protected abstract short chooseNewGene(short oldGene, short n);
    public MutateManager(Integer minMutate, Integer maxMutate)
    {
        this.minMutate = minMutate;
        this.maxMutate = maxMutate;
    }

    private int newFree(boolean [] mutatedGenes)
    {
        int n = mutatedGenes.length;
        int r = rand.nextInt();
        while(mutatedGenes[r]){
            int j = 1;
            int r1 = (r+j)%n;
            int r2 = (r-j)%n;
            if(!mutatedGenes[r1])
                r=r1;
            else if(!mutatedGenes[r2])
                r=r2;
        }
        mutatedGenes[r] = true;
        return r;
    }
}
