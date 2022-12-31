package agh.kopec.evo_simulation;

import java.util.Random;

public class BehaviourManagerRandomNext implements IBehaviourManager{

    @Override
    public short chooseNewGene(short activeGene, short numberOfGenes) {
        Random random = new Random();
        float f = random.nextFloat();

        if(numberOfGenes==1)
            return 0;
        else if (f<0.8f)
            return (short)((activeGene+1) % numberOfGenes);
        else {

            int next = random.nextInt(numberOfGenes-1);
            if(next == activeGene)
                next+=1;
            return (short)(next);
        }
    }
}
