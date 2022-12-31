package agh.kopec.evo_simulation;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;

import static java.lang.Math.min;


public class MapVisualizer{

    private MapManager map;
    private GridPane grid;
    public MapVisualizer(MapManager map) {
        this.map = map;
    }

    public GridPane drawGrid(Vector2d lowerLeft, Vector2d upperRight) throws FileNotFoundException
    {
        if(grid == null) {
            int width = upperRight.substract(lowerLeft).x + 2;
            int height = upperRight.substract(lowerLeft).y + 2;

            grid = new GridPane();

            float scalar = min(800 / width, 800 / height);

            for (int j = lowerLeft.y; j <= upperRight.y; j++) {
                Label l = new Label(Integer.toString(j));
                l.setFont(new Font(scalar * 2 / 3));
                GridPane.setHalignment(l, HPos.CENTER);
                grid.add(l, 0, upperRight.y - j + 1);
            }
            for (int i = lowerLeft.x; i <= upperRight.x; i++) {
                Label l = new Label(Integer.toString(i));
                l.setFont(new Font(scalar * 2 / 3));
                GridPane.setHalignment(l, HPos.CENTER);
                grid.add(l, i - lowerLeft.x + 1, 0);
            }

            {
                Label l = new Label("y/x");
                l.setFont(new Font(scalar * 2 / 3));
                GridPane.setHalignment(l, HPos.CENTER);
                grid.add(l, 0, 0);
            }

            grid.getColumnConstraints().add(new ColumnConstraints(scalar));
            grid.getRowConstraints().add(new RowConstraints(scalar));

            for (int j = lowerLeft.y; j <= upperRight.y; j++) {
                grid.getRowConstraints().add(new RowConstraints(scalar));
                for (int i = lowerLeft.x; i <= upperRight.x; i++) {
                    //grid.getColumnConstraints().add(new ColumnConstraints(scalar));
                    Vector2d analyse = new Vector2d(i, j);

                    javafx.scene.Node child = new Label();
//                    if (map.objectAt(analyse) != null) {
//                        GuiElementBox box = new GuiElementBox(map.objectAt(analyse), scalar);
//                        child = box.getVbox();
//                    }
                    GridPane.setHalignment(child, HPos.CENTER);
                    grid.add(child, i - lowerLeft.x + 1, upperRight.y - j + 1);


                }
            }

            for (int i = lowerLeft.x; i <= upperRight.x; i++)
                grid.getColumnConstraints().add(new ColumnConstraints(scalar));
            grid.setGridLinesVisible(true);
        }
        return grid;
    }
}


