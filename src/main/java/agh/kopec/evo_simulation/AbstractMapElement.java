package agh.kopec.evo_simulation;

public class AbstractMapElement implements Comparable{
    protected Vector2d position;

    @Override
    public int compareTo(Object o) {
        if(o instanceof AbstractMapElement that)
        {
            if(position.x != that.position.x) return position.x - that.position.x;
            else return position.y - that.position.y;
        }
        else return 1;
    }
}
