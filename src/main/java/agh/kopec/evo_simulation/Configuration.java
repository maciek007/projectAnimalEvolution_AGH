package agh.kopec.evo_simulation;

import agh.kopec.evo_simulation.variants.BehaviourVariant;
import agh.kopec.evo_simulation.variants.MapVariant;
import agh.kopec.evo_simulation.variants.MutateVariant;
import agh.kopec.evo_simulation.variants.SeedingVariant;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Configuration {

    public MapVariant mapVariant = MapVariant.Globe;
    public Vector2d mapDimensions = new Vector2d(100,100);

    public int plantsStartingAmount = 500;
    public int plantsProvidingEnergy = 100;
    public int plantsDailyIncrease = 100;
    public SeedingVariant seedingVariant = SeedingVariant.FertileEquators;

    public int animalsStartingAmount = 500;
    public int animalsStartingEnergy = 100;
    public int animalsBreedEnergy = 50;
    public float animalsBreedPartialCost = 0.2f;
    public BehaviourVariant animalsBehaviourVariant = BehaviourVariant.PredestinedBehaviour;

    public short genesAmount = 10;
    public MutateVariant genesMutateVariant = MutateVariant.SlightChange;
    public short genesMinMutate = 0;
    public short genesMaxMutate = 4;

    public Configuration(Path file) throws IOException, IllegalArgumentException {

        Scanner sc = new Scanner(file);
        Map<String, String> arg_val = new HashMap<>();

        while(sc.hasNextLine())
        {
            String line = sc.nextLine().split("#")[0];
            if(line.trim().equals("")) continue;
            String[] argument = line.split(":");
            if(argument.length > 2) throw new IllegalArgumentException("Error parsing file - invalid line format, expected:" +
                                                                        "ArgumentName : Value");

            arg_val.put(argument[0].trim(),argument[1].trim());
        }

        try {
            arg_val.forEach((k, v) -> {
                switch (k) {
                    case "mapVariant" -> mapVariant = MapVariant.fromString(v);
                    case "mapDimensions" -> mapDimensions = new Vector2d(Vector2d.fromString(v));

                    case "plantsStartingAmount" -> plantsStartingAmount = Integer.parseInt(v);
                    case "plantsProvidingEnergy" -> plantsProvidingEnergy = Integer.parseInt(v);
                    case "plantsDailyIncrease" -> plantsDailyIncrease = Integer.parseInt(v);
                    case "seedingVariant" -> seedingVariant = SeedingVariant.fromString(v);

                    case "animalsStartingAmount" -> animalsStartingAmount = Integer.parseInt(v);
                    case "animalsStartingEnergy" -> animalsStartingEnergy = Integer.parseInt(v);
                    case "animalsBreedEnergy" -> animalsBreedEnergy = Integer.parseInt(v);
                    case "animalsBreedPartialCost" -> animalsBreedPartialCost = Float.parseFloat(v);
                    case "animalsBehaviourVariant" -> animalsBehaviourVariant = BehaviourVariant.fromString(v);

                    case "genesAmount" -> genesAmount = (short) Integer.parseInt(v);
                    case "genesMutateVariant" -> genesMutateVariant = MutateVariant.fromString(v);
                    case "genesMinMutate" -> genesMinMutate = (short) Integer.parseInt(v);
                    case "genesMaxMutate" -> genesMaxMutate = (short) Integer.parseInt(v);
                    default -> throw new IllegalArgumentException("Error parsing file - invalid argumentName");
                }
            });
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Error parsing file - invalid number format");
        }

        if(plantsStartingAmount > mapDimensions.y * mapDimensions.x) plantsStartingAmount = mapDimensions.x * mapDimensions.y;
        if(animalsBreedPartialCost > 1.0f)
            throw new IllegalArgumentException("animalsBreedPartialCost should be between 0 and 1, read: "+animalsBreedPartialCost);
        if(genesMinMutate>genesMaxMutate)
            throw new IllegalArgumentException("genesMinMutate should be lower than genesMaxMutate");
        if(genesAmount<genesMaxMutate)
            throw new IllegalArgumentException("genesMaxMutate should be lower than genesAmount");
        if(plantsStartingAmount<0)
            throw new IllegalArgumentException("plantsStartingAmount cant be lower than 0");
        if(animalsStartingAmount<0)
            throw new IllegalArgumentException("animalsStartingAmount cant be lower than 0");
        if(genesAmount<0)
            throw new IllegalArgumentException("genesAmount cant be lower than 0");

    }

    //public Configuration(){}
}
