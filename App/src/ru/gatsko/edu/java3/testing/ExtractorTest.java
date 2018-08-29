package ru.gatsko.edu.java3.testing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith (Parameterized.class)
public class ExtractorTest {
    @Parameterized .Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList( new Object[][]{
                { new int[] {0,1,2,3,4,5,6} , new int[] {5,6} },
                { new int[] {-100,4,4,5,6,4,3}, new int[] {3}},
                { new int[] {-100,4,4,5,6,4,3,4}, new int[] {}},
                { new int[] {1,2,3,5}, new int[] {}},
        });
    }
    private int[] array;
    private int[] result;
    public ExtractorTest ( int[] array, int[] result) {
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
            Assert.assertArrayEquals(result, handler.extract(array));
        } catch (RuntimeException e) {}
    }
}
