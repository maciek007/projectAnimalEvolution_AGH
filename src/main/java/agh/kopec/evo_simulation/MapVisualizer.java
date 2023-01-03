package agh.kopec.evo_simulation;

import javafx.geometry.Insets;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;


public class MapVisualizer{

    private final MapManager map;
    private final AnimalsManager animalsManager;
    private final Image plantImage;
    private final Image animalImage;

    private final HBox trackedAnimalTab;

    float scalar;

    public MapVisualizer(MapManager map, AnimalsManager animalsManager, HBox trackedAnimalTab ) throws FileNotFoundException {
        this.trackedAnimalTab = trackedAnimalTab;
        this.map = map;
        this.animalsManager = animalsManager;
        this.plantImage = new Image(new FileInputStream("src/main/resources/grass.png"));
        this.animalImage = new Image(new FileInputStream("src/main/resources/animal.png"));
        this.scalar = min(800 / map.dimensions.x, 800 / map.dimensions.y);
    }

    public GridPane initGrid()
    {
        GridPane grid = new GridPane();
        for (int i = 0; i < map.dimensions.x; i++)
            grid.getColumnConstraints().add(new ColumnConstraints(scalar));
        for (int i = 0; i < map.dimensions.y; i++)
            grid.getRowConstraints().add(new RowConstraints(scalar));
        grid.setGridLinesVisible(true);

        grid.setPadding(new Insets(10,10,10,10));
        return grid;
    }
    public void drawGrid(GridPane grid)
    {
        int freeFields = 0;

        grid.getChildren().clear();

        Iterator<Animal> iterator = animalsManager.animals.stream().sorted().iterator();
        Animal animal = null;
        if(iterator.hasNext()) animal = iterator.next();

        for (int i = 0; i < map.dimensions.x; i++) {
            for (int j = 0; j < map.dimensions.y; j++) {

                Vector2d analyse = new Vector2d(i, j);

                List<ImageView> onField=new ArrayList<>();

                if(animal!=null)
                    while(animal.position.equals(analyse)) {

                        ImageView animalView = new ImageView(animalImage);
                        animalView.setRotate(animal.getRotate());
                        ColorAdjust eff = null;

                        if(animal.animalStats.energy < animalsManager.breedEnergy) {
                            eff = new ColorAdjust();
                            eff.setBrightness(-1+(float)animal.animalStats.energy/animalsManager.breedEnergy);
                            animalView.setEffect(eff);
                        }
                        if(animal == animalsManager.trackedAnimal)
                        {
                            InnerShadow ef2 = new InnerShadow(5, Color.GOLD);
                            ef2.setChoke(2);
//                            ef2.setHeight(10);
//                            ef2.setHeight(10);
                            Effect effect = ef2;
                            if(eff!=null)
                                effect = new Blend(BlendMode.OVERLAY, effect,eff);

                            animalView.setEffect(effect);
                        }


                        animalView.addEventHandler(MouseEvent.MOUSE_CLICKED, new AnimalClicked(animal, animalsManager, trackedAnimalTab));

                        onField.add(animalView);

                        if (iterator.hasNext()) {
                            animal = iterator.next();
                        } else break;
                    }

                TilePane tile = new TilePane();
                if (map.seedManager.fertileMap.contains(analyse)) {
                    tile.setStyle("-fx-background-color: #20C820;");
                }
                else
                    tile.setStyle("-fx-background-color: #648F64;");

                if(map.seedManager.plantMap[i][j]) {
                    ImageView plantView = new ImageView(plantImage);
                    onField.add(plantView);
                }

                int s = (int) Math.ceil(sqrt(onField.size()));
                tile.setPrefColumns(s);
                tile.setPrefRows(s);
                //tile.setPrefTileHeight(scalar/s);
                //tile.setPrefTileWidth(scalar/s);
                tile.setPrefSize(scalar,scalar);

                onField.forEach(img -> {img.setFitWidth(scalar/s);img.setFitHeight(scalar/s);});

                tile.getChildren().addAll(onField);


               if(onField.size()==0)
                   freeFields+=1;
                grid.add(tile,  i,map.dimensions.y - j - 1);
                grid.setGridLinesVisible(false);
                grid.setGridLinesVisible(true);
            }

        }
        map.simulationStats.freeFields = freeFields;
    }
}


