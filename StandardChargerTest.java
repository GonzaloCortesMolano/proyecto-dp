import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link StandardCharger} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of StandardCharger:
 * - Fee calculation (Standard logic).
 * - Equality checks.
 */
public class StandardChargerTest
{
    private StandardCharger charger;
    private ElectricVehicle stdEV;
    private ElectricVehicle premEV;
    private EVCompany Vectalia;
    
    
    /**
     * Default constructor for test class StandardChargerTest
     */
    public StandardChargerTest()
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
        charger = new StandardCharger("CH_STD", 40, 0.50);
        
        Location loc = new Location(0,0);
        stdEV = new StandardEV(Vectalia, loc, loc, "Std", "CC01", 100);
        premEV = new PremiumEV(Vectalia, loc, loc, "Prem", "CC02", 100);
    }
    
    /**
     * Tears down the test fixture.
     */
    @AfterEach
    public void tearDown()
    {
        charger = null;
        stdEV = null;
        premEV = null;
        Vectalia = null;
    }

    /**
     * Tests the {@code calculateFee()} logic implicitly via recharge.
     * StandardCharger uses the base fee without modifications.
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(stdEV, 100);
        assertEquals(50.0, cost, 0.01);
    }
    
    /**
     * Tests the specific {@code canCharge()} logic.
     * StandardCharger accepts Standard and VTC vehicles.
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(stdEV));
        assertFalse(charger.canCharge(premEV));
    }

    /**
     * Tests the {@code equals(Object)} method.
     * Checks equality based on ID and type references.
     */
    @Test
    public void testEquals(){
        Charger sameRef = charger;
        Charger differentRef = new StandardCharger("CH_STD", 40, 0.50);
        Charger difType = new PriorityCharger("CH_STD", 40, 0.50);
        Charger diffId = new StandardCharger("CH_OTHER", 40, 0.50);
        
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