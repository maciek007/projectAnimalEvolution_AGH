package agh.kopec.evo_simulation;

import java.util.Random;

public class BehaviourManagerRandomNext implements IBehaviourManager{

    @Override
    public Integer chooseNewGene(Integer activeGene, Integer numberOfGenes) {
        Random random = new Random();
        float f = random.nextFloat();

        if(numberOfGenes==1)
            return 0;
        else if (f<0.8f)
            return (activeGene+1) % numberOfGenes;
        else {

            Integer next = random.nextInt(numberOfGenes-1);
            if(next.equals(activeGene))
                next+=1;
            return next;
        }
    }
}
