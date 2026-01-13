import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link SolarCharger} class.
 * <p>
 * Provides unit tests for core functionalities of SolarCharger:
 * <ul>
 *   <li>Calculation of recharge fees with a 10% discount.</li>
 *   <li>Compatibility checks to ensure only VTC vehicles can be charged.</li>
 *   <li>Equality checks using {@code equals(Object)}.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano<br>
 * Version: 12-11-2025
 * </p>
 */
public class SolarChargerTest
{
    /** The SolarCharger instance under test. */
    private SolarCharger charger;

    /** A VTC electric vehicle used in tests. */
    private ElectricVehicle vtcEV;

    /** A standard electric vehicle used in tests. */
    private ElectricVehicle stdEV;

    /** The singleton EVCompany instance used in tests. */
    private EVCompany Vectalia;

    /**
     * Default constructor for test class SolarChargerTest
     */
    public SolarChargerTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
     * <p>
     * Initializes the EVCompany instance, creates the SolarCharger under test,
     * and instantiates one VTC and one Standard electric vehicle.
     * </p>
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
     * Tears down the test fixture after each test method.
     * <p>
     * Clears all object references to ensure isolation between tests.
     * </p>
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
     * Tests the {@code calculateFee()} logic via a recharge operation.
     * <p>
     * Verifies that the SolarCharger correctly applies a 10% discount to the base fee
     * when recharging a VTC vehicle.
     * </p>
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(vtcEV, 100);
        assertEquals(90.0, cost, 0.01);
    }
    
    /**
     * Tests the {@code canCharge()} method.
     * <p>
     * Ensures that the SolarCharger only accepts VTC vehicles and rejects others,
     * such as StandardEV.
     * </p>
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(vtcEV));
        assertFalse(charger.canCharge(stdEV));
    }

    /**
     * Tests the {@code equals(Object)} method.
     * <p>
     * Compares the SolarCharger instance with various other objects:
     * <ul>
     *   <li>Same reference: should return true</li>
     *   <li>Same type, different object: should return true</li>
     *   <li>Different type but same data: should return false</li>
     *   <li>Different ID but same type: should return false</li>
     *   <li>null: should return false</li>
     * </ul>
     * </p>
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