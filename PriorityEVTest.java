import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PriorityEV} class.
 * <p>
 * Provides unit tests for core functionalities of PriorityEV:
 * <ul>
 *   <li>Special movement logic (double movement per simulation step).</li>
 *   <li>Company notification overriding (should not register in company logs).</li>
 *   <li>Equality checks using {@code equals(Object)}.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano<br>
 * Version: 12-11-2025
 * </p>
 */
public class PriorityEVTest
{
    /** The PriorityEV instance under test. */
    private PriorityEV vehicle;

    /** The singleton EVCompany instance used in tests. */
    private EVCompany company;

    /** The starting location of the vehicle. */
    private Location startLoc;

    /** The target location of the vehicle. */
    private Location targetLoc;

    /**
     * Default constructor for test class PriorityEVTest.
     */
    public PriorityEVTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
     * <p>
     * Initializes a PriorityEV instance at a starting location with a target, and links it
     * to the EVCompany singleton.
     * </p>
     */
    @BeforeEach
    public void setUp()
    {
        EVCompany.resetInstance();
        company = EVCompany.getInstance();
        startLoc = new Location(0, 0);
        targetLoc = new Location(20, 20);
        vehicle = new PriorityEV(company, startLoc, targetLoc, "Priority", "PRI01", 200);
    }

    /**
     * Tears down the test fixture after each test method.
     * <p>
     * Clears references to ensure test isolation between cases.
     * </p>
     */
    @AfterEach
    public void tearDown()
    {
        vehicle = null; 
        company = null; 
        startLoc = null; 
        targetLoc = null;
    }

    /**
     * Tests the {@code act()} method for double movement.
     * <p>
     * Ensures that the PriorityEV moves two steps per simulation tick instead of one, 
     * reflecting its priority behavior in the simulation.
     * </p>
     */
    @Test
    public void testDoubleMove()
    {
        // Start 0,0. Target 20,20. 
        // Standard move would be to 1,1. 
        // Priority move should be to 2,2 (Two steps).
        vehicle.act(0);
        assertEquals(new Location(2, 2), vehicle.getLocation());
    }
    
    /**
     * Tests the {@code notifyCompany()} override for PriorityEV.
     * <p>
     * Verifies that recharges performed by PriorityEV do not register in the company's
     * recharge registry, as this type of vehicle is exempt from logging.
     * </p>
     */
    @Test
    public void testNoRegistration()
    {
        // Setup a station and charger
        ChargingStation st = new ChargingStation("City", "S", startLoc);
        Charger ch = new PriorityCharger("C", 50, 0.5);
        st.addCharger(ch);
        company.addChargingStation(st);
        
        // Perform recharge
        vehicle.setRechargingLocation(startLoc);
        vehicle.recharge(0);
        
        // Assert: Registry should NOT contain this charger key, or if it does, not this vehicle
        boolean registered = false;
        if(company.getChargesRegistry().containsKey(ch.getId())) {
             registered = company.getChargesRegistry().get(ch.getId()).contains(vehicle);
        }
        
        assertFalse(registered, "PriorityEV should not be registered in company logs");
    }

    /**
     * Tests the {@code equals(Object)} method.
     * <p>
     * Ensures that two PriorityEV instances with the same ID are considered equal,
     * and vehicles with different IDs are not equal.
     * </p>
     */
    @Test
    public void testEquals()
    {
        PriorityEV same = new PriorityEV(company, startLoc, targetLoc, "Priority", "PRI01", 200);
        PriorityEV diff = new PriorityEV(company, startLoc, targetLoc, "Priority", "PRI99", 200);
        
        assertEquals(vehicle, same);
        assertNotEquals(vehicle, diff);
    }
}