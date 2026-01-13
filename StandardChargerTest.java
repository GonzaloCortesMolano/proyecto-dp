import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link StandardCharger} class.
 * <p>
 * Provides unit tests for specific behaviors of StandardCharger:
 * <ul>
 *   <li>Fee calculation (Standard logic).</li>
 *   <li>Compatibility checks.</li>
 *   <li>Equality checks.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * Version: 12-11-2025
 * </p>
 */
public class StandardChargerTest
{
    /** The StandardCharger instance under test. */
    private StandardCharger charger;
    
    /** A standard electric vehicle used in tests. */
    private ElectricVehicle stdEV;
    
    /** A premium electric vehicle used in tests. */
    private ElectricVehicle premEV;
    
    /** The singleton EVCompany instance used in tests. */
    private EVCompany Vectalia;
    
    
    /**
     * Default constructor for the test class.
     * <p>
     * Initializes a new instance of {@code StandardChargerTest}.
     * </p>
     */
    public StandardChargerTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
     * <p>
     * Resets the EVCompany singleton, creates a StandardCharger, and initializes
     * both standard and premium electric vehicles.
     * </p>
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
     * Tears down the test fixture after each test method.
     * <p>
     * Cleans up all instances created during setup.
     * </p>
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
     * Tests the {@code calculateFee()} behavior of {@link StandardCharger}.
     * <p>
     * Verifies that the recharge cost for a standard electric vehicle
     * corresponds to the expected fee calculation.
     * </p>
     */
    @Test
    public void testCalculateFee()
    {
        double cost = charger.recharge(stdEV, 100);
        assertEquals(50.0, cost, 0.01);
    }
    
    /**
     * Tests the {@code canCharge()} method of {@link StandardCharger}.
     * <p>
     * Ensures that the charger correctly identifies compatible vehicles:
     * standard EVs should be accepted, premium EVs should be rejected.
     * </p>
     */
    @Test
    public void testCompatibility()
    {
        assertTrue(charger.canCharge(stdEV));
        assertFalse(charger.canCharge(premEV));
    }

    /**
     * Tests the {@code equals(Object)} method of {@link StandardCharger}.
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