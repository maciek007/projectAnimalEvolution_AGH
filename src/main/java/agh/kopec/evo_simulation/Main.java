package agh.kopec.evo_simulation;

import agh.kopec.evo_simulation.gui.App;
import javafx.application.Application;

public class Main {
    public static void main(String[] args)
    {
        ApplicationConfig.parseArgs(args);
        Application.launch(App.class,args);
    }
}
