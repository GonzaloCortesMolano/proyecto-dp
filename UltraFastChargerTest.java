import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link UltraFastCharger} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of UltraFastCharger:
 * - Fee calculation (10% surcharge).
 * - Equality checks.
 */
public class UltraFastChargerTest
{
    private UltraFastCharger charger;
    private ElectricVehicle premEV;
    private ElectricVehicle stdEV;
    private EVCompany Vectalia;

    /**
     * Default constructor for test class UltraFastChargerTest
     */
    public UltraFastChargerTest()
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
        charger = new UltraFastCharger("CH_FST", 100, 1.0);
        
        Location loc = new Location(0,0);
        premEV = new PremiumEV(Vectalia, loc, loc, "Prem", "CC01", 100);
        stdEV = new StandardEV(Vectalia, loc, loc, "Std", "CC02", 100);
    }

    /**
     * Tears down the test fixture.
     */
    @AfterEach
    public void tearDown()
    {
        charger = null;
        premEV = null;
        stdEV = null;
        Vectalia = null;
    }

    /**
     * Tests the {@code calculateFee()} logic implicitly via recharge.
     * UltraFastCharger applies a 10% surcharge.
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(premEV, 100);
        assertEquals(110.0, cost, 0.01);
    }
    
    /**
     * Tests the specific {@code canCharge()} logic.
     * UltraFastCharger accepts only Premium vehicles.
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(premEV));
        assertFalse(charger.canCharge(stdEV));
    }

    /**
     * Tests the {@code equals(Object)} method.
     */
    @Test
    public void testEquals(){
        Charger sameRef = charger;
        Charger differentRef = new UltraFastCharger("CH_FST", 100, 1.0);
        Charger difType = new SolarCharger("CH_FST", 100, 1.0);
        Charger diffId = new UltraFastCharger("CH_OTHER", 100, 1.0);
        
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