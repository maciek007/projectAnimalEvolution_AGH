package agh.kopec.evo_simulation;

public class ApplicationConfig {

    static public String CONFIG_PATH="";
    static public Integer SIM_TABLE_REFRESH_INTERVAL=1000;
    static public Integer BASE_ENGINE_INTERVAL=500;
    static public Integer REAL_TIME_ENGINE_INTERVAL=10;

    static public void parseArgs(String[] args)
    {
        for(String s : args) {
            String[] splitted =s.split("=");
            if(splitted.length!=2) continue;
            String name = splitted[0];
            String value = splitted[1];
            switch (name)
            {
                case "CONFIG_PATH" -> CONFIG_PATH = value;
                case "SIM_TABLE_REFRESH_INTERVAL" -> SIM_TABLE_REFRESH_INTERVAL = Integer.parseInt(value);
                case "BASE_ENGINE_INTERVAL" -> BASE_ENGINE_INTERVAL = Integer.parseInt(value);
                case "REAL_TIME_ENGINE_INTERVAL" -> REAL_TIME_ENGINE_INTERVAL = Integer.parseInt(value);
            }
        }
    }
}
