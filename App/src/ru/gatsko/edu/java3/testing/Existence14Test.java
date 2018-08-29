package ru.gatsko.edu.java3.testing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith (Parameterized.class)
public class Existence14Test {
    @Parameterized .Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList( new Object[][]{
                { new Integer[] {0,1,2,3,4,5,6} , true},
                { new Integer[] {-100,4,4,5,6,4,3}, false},
                { new Integer[] {-100,4,4,5,6,4,3,4}, false},
                { new Integer[] {1,2,3,5}, false},
        });
    }
    private Integer[] array;
    private boolean result;
    public Existence14Test ( Integer[] array, boolean result) {
        this.array = array;
        this.result = result;
    }
    App handler;
    @Before
    public void init () {
        handler = new App();
    }
    @Test
    public void massTest () {
        try {
            Assert.assertEquals(result, handler.check14(array));
        } catch (RuntimeException e) {}
    }
}
