package agh.kopec.evo_simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ConfigManager {
    public final String ConfPath;
    private Stream<Path> filesPaths;

    public ConfigManager(String CONFIG_PATH){
        ConfPath = CONFIG_PATH;
    }

    public List<String> getFilesList() throws IOException
    {
        try(Stream<Path> stream = Files.list(Paths.get(ConfPath)))
        {
            filesPaths = stream
                    .filter(file -> file.getFileName().endsWith(".config"));
            return filesPaths.map(Path::toString).toList();
        }
    }

}
