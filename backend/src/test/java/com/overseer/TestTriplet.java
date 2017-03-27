package com.overseer;

import org.javatuples.Triplet;
import org.junit.Test;

import java.util.List;

/**
 * Created by Alkit on 3/27/2017.
 */
public class TestTriplet {

    @Test
    public void testing(){
        Object[] objects = null;
        System.out.println(objects);
        Triplet<String,Integer,List> g = Triplet.with("dawda0",21,null);
        System.out.println("wadawdawd");
    }
}
