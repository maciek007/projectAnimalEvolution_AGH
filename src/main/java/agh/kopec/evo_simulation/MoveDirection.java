package agh.kopec.evo_simulation;

public enum MoveDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    final static String guiNORTH = "src/main/resources/up.png";
    final static String guiEAST = "src/main/resources/right.png";
    final static String guiSOUTH = "src/main/resources/down.png";
    final static String guiWEST = "src/main/resources/left.png";


    public String toResPath()
    {
        return switch (this) {
            case NORTH -> guiNORTH;
            case NORTH_EAST -> null;
            case EAST -> guiEAST;
            case SOUTH_EAST -> null;
            case SOUTH -> guiSOUTH;
            case SOUTH_WEST -> null;
            case WEST -> guiWEST;
            case NORTH_WEST -> null;
        };
    }

    public Vector2d toUnitVector()
    {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(-1, 1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(1, -1);
        };
    }

    public MoveDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST -> WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST -> EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }

    public MoveDirection rotate(short i) {
        return values()[(ordinal()+i) % values().length];
    }
}
