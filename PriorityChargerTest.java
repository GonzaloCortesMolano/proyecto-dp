import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PriorityCharger} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of PriorityCharger:
 * - Fee calculation (Standard logic).
 * - Equality checks.
 */
public class PriorityChargerTest
{
    private PriorityCharger charger;
    private ElectricVehicle prioEV;
    private ElectricVehicle stdEV;
    private EVCompany Vectalia;

    /**
     * Default constructor for test class PriorityChargerTest
     */
    public PriorityChargerTest()
    {
    }

    /**
     * Sets up the test fixture.
     */
    @BeforeEach
    public void setUp()
    {
        EVCompany.resetInstance();
        Vectalia = EVCompany.getInstance();
        
        // Fee 0.50
        charger = new PriorityCharger("CH_PRI", 60, 0.50);
        
        Location loc = new Location(0,0);
        prioEV = new PriorityEV(Vectalia, loc, loc, "Prio", "CC01", 100);
        stdEV = new StandardEV(Vectalia, loc, loc, "Std", "CC02", 100);
    }

    /**
     * Tears down the test fixture.
     */
    @AfterEach
    public void tearDown()
    {
        charger = null;
        prioEV = null;
        stdEV = null;
        Vectalia = null;
    }

    /**
     * Tests the specific {@code canCharge()} logic.
     * PriorityCharger accepts only Priority vehicles.
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(prioEV, 100);
        assertEquals(50.0, cost, 0.01);
    }
    
    /**
     * Tests the specific {@code canCharge()} logic.
     * PriorityCharger accepts only Priority vehicles.
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(prioEV));
        assertFalse(charger.canCharge(stdEV));
    }

    /**
     * Tests the {@code equals(Object)} method.
     */
    @Test
    public void testEquals(){
        Charger sameRef = charger;
        Charger differentRef = new PriorityCharger("CH_PRI", 60, 0.50);
        Charger difType = new StandardCharger("CH_PRI", 60, 0.50);
        Charger diffId = new PriorityCharger("CH_OTHER", 60, 0.50);
        
        //Caso el mismo objeto creo, pq apunta a la misma direccion de memoria
        assertEquals(true, charger.equals(sameRef));
        //Mismo tipo distinto id
        assertEquals(false, charger.equals(diffId));
        //Distintas direcciones de memoria
        assertEquals(true, charger.equals(differentRef));
        //Mismos datos, distintas direcciones de memoria, y distinto tipo
        assertEquals(false, charger.equals(difType));
        //Con null
        assertFalse(charger.equals(null));
    }
}