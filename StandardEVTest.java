import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link StandardEV} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of StandardEV, including:
 * - Charger selection strategy (based on shortest total distance).
 * - Equality checks.
 */
public class StandardEVTest
{
    private StandardEV vehicle;
    private EVCompany company;
    private Location startLoc;
    private Location targetLoc;

    /**
     * Default constructor for test class StandardEVTest.
     */
    public StandardEVTest()
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
        targetLoc = new Location(20, 0);
        vehicle = new StandardEV(company, startLoc, targetLoc, "Standard", "STD01", 100);
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
     * Tests the specific charger selection strategy for StandardEV.
     * * StandardEV should choose the charger that minimizes the total distance:
     * (Current -> Station -> Target).
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