import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lifesaver on 30/06/2017.
 */
public class MeasurementTest {
    @Test
    public void testMeasurementCon(){
        Measurement m1=new Measurement();
        assertEquals(0,m1.feet);
        assertEquals(0,m1.inches);
    }

    @Test
    public void testMeasurementCon2(){
        Measurement m1=new Measurement(2);
        assertEquals(2,m1.feet);
        assertEquals(0,m1.inches);
    }

    @Test
    public void testMeasurementCon3(){
        Measurement m1=new Measurement(2,6);
        assertEquals(2,m1.feet);
        assertEquals(6,m1.inches);
    }

    @org.junit.Test
    public void testgetFeet() throws Exception {
        Measurement m1=new Measurement(2,6);
        assertEquals(2,m1.getFeet());
    }

    @Test
    public void testgetInches() throws Exception {
        Measurement m1=new Measurement(2,6);
        assertEquals(6,m1.getInches());
    }

    @Test
    public void testplus() throws Exception {
        Measurement m1=new Measurement(2,6);
        Measurement m2=new Measurement(1,3);
        m1.plus(m2);
        assertEquals(3,m1.feet);
        assertEquals(9,m1.inches);
    }

    @Test
    public void testminus() throws Exception {
        Measurement m1=new Measurement(2,6);
        Measurement m2=new Measurement(1,3);
        m1.minus(m2);
        assertEquals(1,m1.feet);
        assertEquals(3,m1.inches);
    }

    @Test
    public void testmultiple() throws Exception {
        Measurement m1=new Measurement(2,6);
        Measurement m3=m1.multiple(2);
        assertEquals(5,m3.feet);
        assertEquals(0,m3.inches);
    }

    @Test
    public void testtoString() throws Exception {
        Measurement m1=new Measurement(2,6);
        assertEquals("2'"+"6\"",m1.toString());
    }

}