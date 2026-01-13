import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link UltraFastCharger} class.
 * <p>
 * Provides unit tests for core functionalities of UltraFastCharger:
 * <ul>
 *   <li>Calculation of recharge fees with a 10% surcharge.</li>
 *   <li>Compatibility checks to ensure only Premium vehicles can be charged.</li>
 *   <li>Equality checks using {@code equals(Object)}.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano<br>
 * Version: 12-11-2025
 * </p>
 */
public class UltraFastChargerTest
{
    /** The UltraFastCharger instance under test. */
    private UltraFastCharger charger;

    /** A premium electric vehicle used in tests. */
    private ElectricVehicle premEV;

    /** A standard electric vehicle used in tests. */
    private ElectricVehicle stdEV;

    /** The singleton EVCompany instance used in tests. */
    private EVCompany Vectalia;

    /**
     * Default constructor for test class UltraFastChargerTest
     */
    public UltraFastChargerTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
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
     * Tears down the test fixture after each test method.
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
     * Tests the {@code equals(Object)} method of {@link UltraFastCharger}.
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