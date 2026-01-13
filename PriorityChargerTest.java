import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PriorityCharger} class.
 * <p>
 * Provides unit tests for specific behaviors of PriorityCharger:
 * <ul>
 *   <li>Fee calculation (Priority logic).</li>
 *   <li>Compatibility checks.</li>
 *   <li>Equality checks.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * Version: 12-11-2025
 * </p>
 */
public class PriorityChargerTest
{
    /** The PriorityCharger instance under test. */
    private PriorityCharger charger;
    
    /** A priority electric vehicle used in tests. */
    private ElectricVehicle prioEV;
    
    /** A standard electric vehicle used in tests. */
    private ElectricVehicle stdEV;
    
    /** The singleton EVCompany instance used in tests. */
    private EVCompany Vectalia;

    /**
     * Default constructor for the test class.
     * <p>
     * Initializes a new instance of {@code PriorityChargerTest}.
     * </p>
     */
    public PriorityChargerTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
     * <p>
     * Resets the EVCompany singleton, creates a PriorityCharger, and initializes
     * both priority and standard electric vehicles.
     * </p>
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
     * Tears down the test fixture after each test method.
     * <p>
     * Cleans up all instances created during setup.
     * </p>
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
     * Tests the {@code calculateFee()} behavior of {@link PriorityCharger}.
     * <p>
     * Verifies that the recharge cost for a priority electric vehicle
     * corresponds to the expected fee calculation.
     * </p>
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(prioEV, 100);
        assertEquals(50.0, cost, 0.01);
    }
    
    /**
     * Tests the {@code canCharge()} method of {@link PriorityCharger}.
     * <p>
     * Ensures that the charger correctly identifies compatible vehicles:
     * only priority EVs should be accepted; standard EVs should be rejected.
     * </p>
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(prioEV));
        assertFalse(charger.canCharge(stdEV));
    }

    /**
     * Tests the {@code equals(Object)} method of {@link PriorityCharger}.
     * <p>
     * Verifies equality checks in various scenarios:
     * <ul>
     *   <li>Same object reference</li>
     *   <li>Different objects with same type and data</li>
     *   <li>Objects of different types</li>
     *   <li>Objects with different IDs</li>
     *   <li>Null comparison</li>
     * </ul>
     * </p>
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