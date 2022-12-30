package agh.kopec.evo_simulation;

public class BehaviourManagerAlwaysNext implements IBehaviourManager {
    @Override
    public Integer chooseNewGene(Integer activeGene, Integer numberOfGenes) {
        return (activeGene+1)%numberOfGenes;
    }
}
