package agh.kopec.evo_simulation;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class AnimalClicked implements EventHandler<MouseEvent> {

    Animal animal;
    AnimalsManager animalsManager;
    final HBox trackedAnimalTab;
    public AnimalClicked(Animal animal, AnimalsManager animalsManager, HBox trackedAnimalTab)
    {
        this.animal = animal;
        this.animalsManager = animalsManager;
        this.trackedAnimalTab = trackedAnimalTab;
    }
    @Override
    public void handle(MouseEvent event) {
        animalsManager.trackedAnimal = animal;
        animalsManager.trackedAnimal.redrawHBox(trackedAnimalTab);
        event.consume();
    }
}
