package agh.kopec.evo_simulation;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ConfigManager {
    private List<Path> filesPaths;

    public List<String> getFilesList() throws IOException
    {
        try(Stream<Path> stream = Files.list(Paths.get("src/main/resources/configurations")))
        {
            filesPaths = stream.filter(file -> file.toString().endsWith(".config")).toList();
            return filesPaths.stream().map(p->p.getFileName().toString()).toList();
        }
    }

    public Configuration getConfiguration(int index){
        Path file = filesPaths.get(index);
        try {
            return new Configuration(file);
        }
        catch (IOException ioException)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot read file " + file.getFileName());
            alert.showAndWait();
        }
        catch (IllegalArgumentException illegalArgumentException)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegal value");
            alert.setHeaderText("Error when parsing file " + file.getFileName());
            alert.setContentText(illegalArgumentException.getMessage());
            alert.showAndWait();
        }
        return null;
    }

}
