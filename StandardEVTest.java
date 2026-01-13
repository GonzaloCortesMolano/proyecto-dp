import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link StandardEV} class.
 * <p>
 * Provides unit tests for core functionalities of StandardEV:
 * <ul>
 *   <li>Charger selection strategy based on shortest total distance (Current -> Station -> Target).</li>
 *   <li>Equality checks using {@code equals(Object)}.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano<br>
 * Version: 12-11-2025
 * </p>
 */
public class StandardEVTest
{
    /** The StandardEV instance under test. */
    private StandardEV vehicle;

    /** The singleton EVCompany instance used in tests. */
    private EVCompany company;

    /** The starting location of the vehicle. */
    private Location startLoc;

    /** The target location of the vehicle. */
    private Location targetLoc;

    /**
     * Default constructor for test class StandardEVTest.
     */
    public StandardEVTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
     * <p>
     * Initializes the StandardEV instance with a starting location, a target location,
     * and a reference to the EVCompany singleton.
     * </p>
     */
    @BeforeEach
    public void setUp()
    {
        EVCompany.resetInstance();
        company = EVCompany.getInstance();
        startLoc = new Location(0, 0);
        targetLoc = new Location(20, 0);
        vehicle = new StandardEV(company, startLoc, targetLoc, "Standard", "STD01", 100);
    }

    /**
     * Tears down the test fixture after each test method.
     * <p>
     * Clears references to ensure test isolation.
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
     * Tests the charger selection strategy for StandardEV.
     * <p>
     * StandardEV should select the charging station that minimizes the total distance
     * from the current location to the station and then to the target.
     * This ensures the vehicle makes efficient routing decisions.
     * </p>
     */
    @Test
    public void testChargerSelectionStrategy()
    {
        // Station A: Located at (10,0). Total trip: 10 + 10 = 20 distance.
        Location locA = new Location(10, 0);
        ChargingStation stA = new ChargingStation("City", "STA", locA);
        stA.addCharger(new StandardCharger("CH_A", 50, 0.5));
        
        // Station B: Located at (0, 20). Total trip: 20 + 28 approx = ~48 distance.
        Location locB = new Location(0, 20);
        ChargingStation stB = new ChargingStation("City", "STB", locB);
        stB.addCharger(new StandardCharger("CH_B", 50, 0.5));
        
        company.addChargingStation(stA);
        company.addChargingStation(stB);
        
        // Force calculation
        vehicle.setBatteryLevel(60);
        vehicle.calculateRoute();
        
        // Should select Station A (Closer total distance)
        assertEquals(locA, vehicle.getRechargingLocation());
    }

    /**
     * Tests the {@code equals(Object)} method.
     * <p>
     * Ensures that two StandardEV instances with the same ID are considered equal,
     * and that vehicles with different IDs or null references are not equal.
     * </p>
     */
    @Test
    public void testEquals()
    {
        StandardEV same = new StandardEV(company, startLoc, targetLoc, "Standard", "STD01", 100);
        StandardEV diff = new StandardEV(company, startLoc, targetLoc, "Standard", "STD99", 100);
        
        assertEquals(vehicle, same);
        assertNotEquals(vehicle, diff);
        assertNotEquals(vehicle, null);
    }
}