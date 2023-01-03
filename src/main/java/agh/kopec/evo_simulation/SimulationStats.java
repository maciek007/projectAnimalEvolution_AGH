package agh.kopec.evo_simulation;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationStats {

    public Integer day;
    public Integer live_animals;
    public Integer  corpses;
    public Integer  plants_n;
    public Integer  freeFields;
    public final Integer  fields;
    public Integer freeFieldsForPlants;
    public Genome famousGenotype;
    public Float avg_alive_energy;
    public Float avg_dead_lifespan;

    int totalLiveEnergy=0;
    int totalDeathLifespan=0;

    public SimulationStats(Vector2d dim) {
        this.day = 0;
        this.live_animals = 0;
        this.corpses = 0;
        this.plants_n = 0;
        this.fields = dim.x * dim.y;
        this.freeFields = fields;
        this.freeFieldsForPlants = fields - plants_n;
        this.famousGenotype = new Genome((short) 1);
        this.avg_alive_energy = 0f;
        this.avg_dead_lifespan = 0f;


    }


    public String CSVLine()
    {
        Stream l = Stream.of(day, live_animals, corpses, plants_n, freeFieldsForPlants, freeFields, famousGenotype, corpses, avg_alive_energy, avg_dead_lifespan);
        return (String) l.map(Object::toString).collect(Collectors.joining(";"));

    }

    public void addDeath(Animal a)
    {
        totalDeathLifespan += day-a.animalStats.born;
        corpses+=1;
        live_animals-=1;
    }

    public void endDay()
    {
        freeFieldsForPlants = fields - plants_n;

        if(corpses != 0)
            avg_dead_lifespan = ((float)totalDeathLifespan / corpses);

        if(live_animals != 0)
            avg_alive_energy = ((float)totalLiveEnergy / live_animals);

    }


    public void redrawVBox(VBox wordStats_values) {
        wordStats_values.getChildren().clear();
        wordStats_values.getChildren().addAll(
                new Label(day.toString()),
                new Label(live_animals.toString()),
                new Label(corpses.toString()),
                new Label(plants_n.toString()),
                new Label(freeFields + " / " + fields),
                new Label(freeFieldsForPlants + " / " + fields),
                new Label(famousGenotype==null?"null":famousGenotype.toString()),
                new Label(String.format("%.3f", avg_alive_energy)),
                new Label(String.format("%.3f", avg_dead_lifespan))
        );

    }
}
