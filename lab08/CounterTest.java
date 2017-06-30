import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lifesaver on 30/06/2017.
 */
public class CounterTest {

    @org.junit.Test
    public void testConstructor() {
        Counter c = new Counter();
        assertTrue(c.value() == 0);
    }

    @org.junit.Test
    public void testIncrement() throws Exception {
        Counter c = new Counter();
        c.increment();
        assertTrue(c.value()  == 1);
        c.increment();
        assertTrue(c.value() == 2);
    }

    @org.junit.Test
    public void testReset() throws Exception {
        Counter c = new Counter();
        c.increment();
        c.reset();
        assertTrue(c.value() == 0);
    }

}