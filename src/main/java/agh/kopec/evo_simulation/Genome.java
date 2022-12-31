package agh.kopec.evo_simulation;

import java.util.Arrays;
import java.util.Random;

public class Genome {

    short [] genome;

    short numberOfGenes;


    public Genome(short n)
    {
        genome = new short[n];
        numberOfGenes = n;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Genome that)
        {
            return Arrays.equals(that.genome, this.genome);
        }
        else return false;
    }

    static Genome newRandom(short numberOfGenes)
    {
        Random rand = new Random();
        Genome g = new Genome(numberOfGenes);
        for(int i=0;i<numberOfGenes; i++)
            g.genome[i] = (short) rand.nextInt(numberOfGenes);
        return g;
    }

    @Override
    public String toString() {
        return Arrays.toString(genome);
    }
}
