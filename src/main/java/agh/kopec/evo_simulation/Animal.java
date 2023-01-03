package agh.kopec.evo_simulation;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Random;

public class Animal extends AbstractMapElement{
    MoveDirection orientation;

    public AnimalStats animalStats;

    public Genome genome;
    short activeGen;
    float energyBurn;

    @Override
    public int compareTo(Object o) {
        if (o instanceof Animal that) {
            if (this.position.x != that.position.x)
                return this.position.x - that.position.x;
            if (this.position.y != that.position.y)
                return this.position.y - that.position.y;
            if (this.animalStats.energy != that.animalStats.energy)
                return this.animalStats.energy - that.animalStats.energy;
            if (this.animalStats.born != that.animalStats.born)
                return that.animalStats.born - this.animalStats.born;
            if (this.animalStats.children != that.animalStats.children)
                return this.animalStats.children - that.animalStats.children;
            else return 0;
        }
        return 0;
    }


    public Animal(Vector2d initialPosition, int energy, Genome genome, int born, float energyBurn) {
        this.position = new Vector2d(initialPosition);
        this.animalStats = new AnimalStats(energy, born);
        this.genome = genome;
        this.energyBurn = energyBurn;

        Random rand = new Random();
        activeGen = (short)(rand.nextInt(genome.numberOfGenes));
        this.orientation = MoveDirection.values()[rand.nextInt(8)];
    }
    
    public void move(IBehaviourManager behaviourManager, MapManager map)
    {
        activeGen = behaviourManager.chooseNewGene(activeGen,genome.numberOfGenes);
        orientation = orientation.rotate(genome.genome[activeGen]);
        map.tryMoveTo(this,position.add(orientation.toUnitVector()));
    }

    public void updateEnergy()
    {
        animalStats.energy -=1;
    }
    public int burnEnergy()
    {
        int energyBurned = Math.round(animalStats.energy * energyBurn);
        animalStats.energy -= energyBurned;
        return energyBurned;
    }

    public void eat(int plantEnergy) {
        animalStats.energy+=plantEnergy;
        animalStats.plantsEaten+=1;
    }

    public Integer getRotate()
    {
        return orientation.toRotate();
    }

    public void redrawHBox(HBox origin)
    {
        String energyDeath = animalStats.death==-1?"Energy: " :"Died on the day: ";

        VBox animalStats_description = new VBox(
                new Label("Born at day: "),
                new Label(energyDeath),
                new Label("Children: "),
                new Label("Plants eaten: "),
                new Label("Genome: "),
                new Label("Index of active gen: ")
        );

        VBox animalStats_values = new VBox(
                new Label(Integer.toString(animalStats.born)),
                new Label(Integer.toString(animalStats.death==-1?animalStats.energy:animalStats.death)),
                new Label(Integer.toString(animalStats.children)),
                new Label(Integer.toString(animalStats.plantsEaten)),
                new Label(genome.toString()),
                new Label(Integer.toString(activeGen))
        );

        origin.getChildren().clear();
        origin.getChildren().addAll(animalStats_description,animalStats_values);
    }
}
