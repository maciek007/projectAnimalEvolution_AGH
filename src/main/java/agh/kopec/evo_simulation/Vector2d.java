package agh.kopec.evo_simulation;

import java.util.Objects;
public class Vector2d
{
    public int x;
    public int y;

    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public Vector2d(Vector2d other)
    {
        this.x = other.x;
        this.y = other.y;
    }

    public static Vector2d fromString(String v) throws IllegalArgumentException{
        String[] dimensions = v.split(",");
        if (dimensions.length == 2) {
            return new Vector2d(Integer.parseInt(dimensions[0].trim()), Integer.parseInt(dimensions[1].trim()));
        }
        throw new IllegalArgumentException("Invalid dimension format, expected: Integer, Integer");
    }


    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x + other.x,this.y + other.y);
    }
    public Vector2d substract(Vector2d other)
    {
        return new Vector2d(this.x - other.x,this.y - other.y);
    }
    @Override
    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof Vector2d that))
            return false;
        return (that.x==this.x && that.y == this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
