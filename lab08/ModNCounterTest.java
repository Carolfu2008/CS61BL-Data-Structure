import static org.junit.Assert.*;

/**
 * Created by lifesaver on 30/06/2017.
 */
public class ModNCounterTest {

    @org.junit.Test
    public void testConstructor() {
        ModNCounter c = new ModNCounter();
        assertTrue(c.value() == 0);
    }

    @org.junit.Test
    public void testConsturtor2(){
        ModNCounter c1=new ModNCounter(5);
        assertTrue(c1.value()==0);
        assertTrue(c1.mod()==5);
    }

    @org.junit.Test
    public void testIncrement() throws Exception {
        ModNCounter c = new ModNCounter();
        c.increment();
        assertTrue(c.value()  == 1);
        c.increment();
        assertTrue(c.value() == 2);
    }

    @org.junit.Test
    public void testReset() throws Exception {
        ModNCounter c = new ModNCounter();
        c.increment();
        c.reset();
        assertTrue(c.value() == 0);
    }

}