package agh.kopec.evo_simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalsManager {

    private final MutateManager mutateManager;
    private final IBehaviourManager behaviourManager;
    private final MapManager mapManager;
    private final SimulationStats simulationStats;

    private final ArrayList <Animal> animals = new ArrayList<>();
    private final int breedEnergy;
    private final float partEnergyBurn;
    private final int plantEnergy;
    final short genesAmount;

    final Random rand = new Random();

    public AnimalsManager(Configuration config, MapManager mapManager, SimulationStats simulationStats)
    {
        this.plantEnergy = config.plantsProvidingEnergy;
        this.mutateManager = config.genesMutateVariant.getManager(config.genesMinMutate, config.genesMaxMutate);
        this.behaviourManager = config.animalsBehaviourVariant.getManager();
        this.mapManager = mapManager;
        this.genesAmount = config.genesAmount;
        this.breedEnergy = config.animalsBreedEnergy;
        this.partEnergyBurn = config.animalsBreedPartialCost;
        this.simulationStats = simulationStats;

        for(int i=0;i<config.animalsStartingAmount;i++)
        {
            put(new Animal(new Vector2d(rand.nextInt(config.mapDimensions.x), rand.nextInt(config.mapDimensions.y)),config.animalsStartingEnergy, Genome.newRandom(genesAmount),0, partEnergyBurn));
        }

    }

    public void put(Animal a1)
    {
        animals.add(a1);
        simulationStats.life_animals+=1;
        simulationStats.totalLiveEnergy+=a1.animalStats.energy;
    }

    private void breed(Animal a1, Animal a2)
    {
        a1.animalStats.children += 1;
        a2.animalStats.children += 1;

        int energy = a1.burnEnergy();
        energy += a2.burnEnergy();

        int num = Math.round((float)a1.animalStats.energy / (a1.animalStats.energy + a2.animalStats.energy) * genesAmount);
        Genome g = new Genome(genesAmount);

        short [] g1;
        short [] g2;

        if(rand.nextBoolean())
        {
            g1 = a1.genome.genome;
            g2 = a2.genome.genome;
        }
        else
        {
            g1 = a2.genome.genome;
            g2 = a1.genome.genome;
        }


        System.arraycopy(g1, 0, g.genome,0,num);
        System.arraycopy(g2, num, g.genome,num,g.genome.length - num);
        mutateManager.mutate(g);
        put(new Animal(a1.position, energy, g, simulationStats.day, partEnergyBurn));

    }

    public void animalsMovement() {
        animals.forEach(animal -> animal.move(behaviourManager,mapManager));
    }

    public void removeDeath() {
        for(Animal animal : animals)
        {
            if(animal.animalStats.energy <= 0)
            {
                mapManager.addCorpse(animal);
                simulationStats.addDeath(animal);
                animal.animalStats.death = simulationStats.day;
            }
        }
        animals.removeIf(animal -> animal.animalStats.energy <= 0);
    }

    public void animalsEatingAndBreeding() {
        animals.sort(null);

        Animal prev = null;
        for(Animal animal : animals)
        {
            if(prev!=null && prev.position.equals(animal.position)) {
                if(animal.animalStats.energy>=breedEnergy)
                    breed(animal, prev);
            }
            else {
                prev = animal;
                if (mapManager.findFood(animal))
                    animal.eat(plantEnergy);
            }
        }
    }

    public void consumeEnergy() {
        simulationStats.totalLiveEnergy -= animals.stream().filter(animal-> animal.animalStats.energy>0).count();
        animals.forEach(Animal::updateEnergy);
    }

    public Set<Vector2d> getPositions()
    {
        return animals.stream().map(animal->animal.position).collect(Collectors.toSet());
    }

    public void findMostCommonGenotype() {
        List<Genome> gs = new ArrayList<>(animals.stream().map(animal -> animal.genome).toList());
        gs.sort(null);

        Genome prev = null;

        int max=0;
        int i = 0;
        Genome g_max = null;

        for(Genome g : gs)
        {
            if(g.equals(prev))
            {
                i+=1;
            }
            else {
                if(i>max) {
                    max = i;
                    g_max = g;
                }
                prev = g;
                i=1;
            }
        }
        if(i>max) {
            g_max = prev;
        }
        simulationStats.famousGenotype = g_max;
    }
}
