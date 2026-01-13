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
     * <p>
     * Initializes the EVCompany instance, creates the UltraFastCharger under test,
     * and instantiates one Premium and one Standard electric vehicle.
     * </p>
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
     * <p>
     * Clears all object references to ensure isolation between tests.
     * </p>
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
     * Tests the {@code calculateFee()} logic via a recharge operation.
     * <p>
     * Validates that UltraFastCharger correctly applies a 10% surcharge
     * to the base charging fee when recharging a Premium vehicle.
     * </p>
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(premEV, 100);
        assertEquals(110.0, cost, 0.01);
    }
    
    /**
     * Tests the {@code canCharge()} method.
     * <p>
     * Ensures that the UltraFastCharger only accepts Premium vehicles
     * and rejects other vehicle types, such as StandardEV.
     * </p>
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(premEV));
        assertFalse(charger.canCharge(stdEV));
    }

    /**
     * Tests the {@code equals(Object)} method of {@link UltraFastCharger}.
     * <p>
     * Compares the UltraFastCharger instance with various other objects to cover
     * different scenarios:
     * <ul>
     *   <li>Same object reference: should return true</li>
     *   <li>Same type, different object: should return true</li>
     *   <li>Different type but same ID/data: should return false</li>
     *   <li>Different ID but same type: should return false</li>
     *   <li>null: should return false</li>
     * </ul>
     * </p>
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