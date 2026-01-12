import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link SolarCharger} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of SolarCharger:
 * - Fee calculation (10% discount).
 * - Equality checks.
 */
public class SolarChargerTest
{
    private SolarCharger charger;
    private ElectricVehicle vtcEV;
    private ElectricVehicle stdEV;
    private EVCompany Vectalia;

    /**
     * Default constructor for test class SolarChargerTest
     */
    public SolarChargerTest()
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
        
        // Fee 1.0
        charger = new SolarCharger("CH_SOL", 50, 1.0);
        
        Location loc = new Location(0,0);
        vtcEV = new VtcEV(Vectalia, loc, loc, "VTC", "CC01", 100);
        stdEV = new StandardEV(Vectalia, loc, loc, "Std", "CC02", 100);
    }

    /**
     * Tears down the test fixture.
     */
    @AfterEach
    public void tearDown()
    {
        charger = null;
        vtcEV = null;
        stdEV = null;
        Vectalia = null;
    }

    /**
     * Tests the {@code calculateFee()} logic implicitly via recharge.
     * SolarCharger applies a 10% discount.
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(vtcEV, 100);
        assertEquals(90.0, cost, 0.01);
    }
    
    /**
     * Tests the specific {@code canCharge()} logic.
     * SolarCharger accepts only VTC vehicles.
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(vtcEV));
        assertFalse(charger.canCharge(stdEV));
    }

    /**
     * Tests the {@code equals(Object)} method.
     */
    @Test
    public void testEquals(){
        Charger sameRef = charger;
        Charger differentRef = new SolarCharger("CH_SOL", 50, 1.0);
        Charger difType = new StandardCharger("CH_SOL", 50, 1.0);
        Charger diffId = new SolarCharger("CH_OTHER", 50, 1.0);
        
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