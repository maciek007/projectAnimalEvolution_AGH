package agh.kopec.evo_simulation.utility;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MultiRandom {

    Random rand = new Random();

    public Queue<Integer> rand(int n, int range)
    {
        return (Queue<Integer>) randMultiUnique(n,range,false);
    }

    public boolean [] rand_table(int n, int range)
    {
        return (boolean[]) (randMultiUnique(n,range,true));
    }

    public Object randMultiUnique(int n, int range, boolean list)
    {
        boolean [] choosen = new boolean[n];
        Queue<Integer>q = new LinkedList<>();

        for(int i=0;i<n;i++)
        {
            int r = rand.nextInt(range);
            int j=0;
            while(choosen[(r+j) % range])
                j+=1;
            choosen[(r+j) % range] = true;
            q.offer((r+j) % range);
        }

        if(list)
            return choosen;
        else
            return q;
    }
}
