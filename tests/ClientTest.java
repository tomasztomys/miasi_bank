package tests;

import miasi_bank.Client;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Gwoździk on 24.03.2017.
 */
public class ClientTest {
    private Client client;
    private String name;
    private String surname;
    private String pesel;

    @Before
    public void setUp() throws Exception {
        name = "Tomasz";
        surname = "Gwoździk";
        pesel = "0010001001";
        client = new Client(name, surname, pesel);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(client.getId());
    }

    @Test
    public void getName() throws Exception {
        assertNotNull(client.getId());
        assertEquals(name, client.getName());
    }

    @Test
    public void getSurname() throws Exception {
        assertNotNull(client.getId());
        assertEquals(surname, client.getSurname());
    }

    @Test
    public void getPesel() throws Exception {
        assertNotNull(client.getId());
        assertEquals(pesel, client.getPesel());
    }

}