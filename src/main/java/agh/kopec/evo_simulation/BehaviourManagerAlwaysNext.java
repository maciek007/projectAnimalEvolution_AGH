package agh.kopec.evo_simulation;

public class BehaviourManagerAlwaysNext implements IBehaviourManager {
    @Override
    public short chooseNewGene(short activeGene, short numberOfGenes) {
        return (short) ((activeGene+1)%numberOfGenes);
    }
}
