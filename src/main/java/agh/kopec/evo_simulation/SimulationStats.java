package agh.kopec.evo_simulation;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationStats {

    public Integer day;
    public Integer  life_animals;
    public Integer  corpses;
    public Integer  plants_n;
    public Integer  freeFields;
    public final Integer  fields;
    public Integer  freeFieldsForPlants;
    public Genome famousGenotype;
    public Float avg_life_energy;
    public Float avg_death_lifespan;

    int totalLiveEnergy=0;
    int totalDeathLifespan=0;

    public SimulationStats(Vector2d dim) {
        this.day = 0;
        this.life_animals = 0;
        this.corpses = 0;
        this.plants_n = 0;
        this.fields = dim.x * dim.y;
        this.freeFields = fields;
        this.freeFieldsForPlants = fields - plants_n;
        this.famousGenotype = new Genome((short) 1);
        this.avg_life_energy = 0f;
        this.avg_death_lifespan = 0f;


    }


    public String CSVLine()
    {
        Stream l = Stream.of(day, life_animals, corpses, plants_n, freeFieldsForPlants, freeFields, famousGenotype, corpses, avg_life_energy, avg_death_lifespan);
        return (String) l.map(Object::toString).collect(Collectors.joining(";"));

    }

    public void addDeath(Animal a)
    {
        totalDeathLifespan += day-a.animalStats.born;
    }

    public void endDay()
    {
        freeFieldsForPlants = fields - plants_n;
        avg_death_lifespan = (float) (totalDeathLifespan / corpses);
        avg_life_energy = (float) (totalLiveEnergy / life_animals);
    }

}
