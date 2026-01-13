import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PriorityEV} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of PriorityEV, including:
 * - Special movement logic (Double speed).
 * - Company notification overriding (Should not register).
 * - Equality checks.
 */
public class PriorityEVTest
{
    private PriorityEV vehicle;
    private EVCompany company;
    private Location startLoc;
    private Location targetLoc;

    /**
     * Default constructor for test class PriorityEVTest.
     */
    public PriorityEVTest()
    {
    }

    /**
     * Sets up the test fixture.
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
     * Tears down the test fixture.
     */
    @AfterEach
    public void tearDown()
    {
        vehicle = null; company = null; startLoc = null; targetLoc = null;
    }

    /**
     * Tests the specific {@code act()} logic for PriorityEV.
     * * PriorityEV is capable of moving twice in a single simulation step.
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
     * Tests the {@code notifyCompany()} override.
     * * PriorityEV should NOT register its recharges in the company registry.
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