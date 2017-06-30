import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lifesaver on 30/06/2017.
 */
public class AccountTest {

    @Test
    public void testInit() {
        Account ac = new Account(20);
        assertEquals(20, ac.getBalance());
    }

    @Test
    public void testInvalidArgs() throws Exception {
        Account ac = new Account(20);
        ac.withdraw(-20);
        assertEquals(20, ac.getBalance());
        ac.deposit(-20);
        assertEquals(20, ac.getBalance());
    }

    @Test
    public void testOverdraft() throws Exception {
        Account ac = new Account(20);
        ac.withdraw(50);
        assertEquals(20, ac.getBalance());
    }

    @org.junit.Test
    public void deposit() throws Exception {
        Account ac = new Account(20);
        ac.deposit(20);
        assertEquals(40,ac.getBalance());
    }

    @Test
    public void withdraw() throws Exception {
        Account ac = new Account(20);
        ac.withdraw(10);
        assertEquals(10,ac.getBalance());
    }

}