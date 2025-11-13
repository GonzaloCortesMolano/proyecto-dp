import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test implementation of the {@link ElectricVehicle} class.
 * Provides unit tests for methods related to electric vehicle creation,
 * route calculation, battery management, and recharging behavior.
 * 
 * Each test verifies that the vehicle behaves correctly in different scenarios,
 * including initialization, battery sufficiency, route calculation, and the recharge process.
 * 
 * @author: Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez
 * @version 12-11-2025
 */
public class ElectricVehicleTest
{
    private ElectricVehicle v1;
    private EVCompany c;
    private Location l;
    private Location target;
    private Charger ch;
    
    /**
     * Default constructor for test class ElectricVehicleTest
     */
    public ElectricVehicleTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        c = new EVCompany("nueva");
        l = new Location(5, 8);
        v1 = new ElectricVehicle(c, l, new Location(20, 20), "name", "plate", 200);
        target = new Location(20, 20);
        ChargingStation station = new ChargingStation("Cáceres", "1", target);
        c.addChargingStation(station);
        ch=new Charger("id", 60, 0.1);
        station.addCharger(ch);
    }

    /**
     * Tears down the test fixture.
     * 
     * Called after every test case method.
     * Resets references to ensure clean state for each test.
     */
    @AfterEach
    public void tearDown()
    {
    }
    /**
     * Tests the {@link ElectricVehicle} constructor and its getter methods.
     * 
     * Verifies that all attributes are correctly initialized when creating a new
     * electric vehicle, including company, locations, identifiers, and battery state.
     * Also ensures that numeric values (battery, counters, etc.) start at expected defaults.
     */
    @Test
    public void testCreation(){
        v1=new ElectricVehicle(new EVCompany("nueva"), new Location(5, 8), new Location(20, 20), "name", "plate", 200);
        EVCompany otra=new EVCompany("nueva");
        otra.addChargingStation(new ChargingStation("Cáceres", "1", target));
        
        assertEquals(v1.getCompany(), otra);
        assertEquals(v1.getLocation(), new Location(5, 8));
        assertFalse(v1.hasRechargingLocation());
        assertEquals(v1.getTargetLocation(), target);
        assertEquals(v1.getName(), "name");
        assertEquals(v1.getPlate(), "plate");
        assertEquals(v1.getBatteryCapacity(), 200);
        assertEquals(v1.getIdleCount(), 0);
        assertEquals(v1.getBatteryLevel(), 200);
        assertEquals(v1.getIdleCount(), 0);
        assertEquals(v1.getKwsCharged(), 0);
        assertEquals(v1.getChargesCount(), 0);
        assertEquals(v1.getChargestCost(), 0);
    }
    /**
     * Tests the {@code enoughBattery()} method.
     * 
     * Ensures that the vehicle can correctly determine whether it has enough battery
     * to reach its target location. It checks both a reachable and an unreachable destination.
     */
    @Test
    public void testEnoughBattery(){
        assertTrue(v1.enoughBattery(v1.distanceToTheTargetLocation()));
        v1.setTargetLocation(new Location(120, 120));
        assertFalse(v1.enoughBattery(v1.distanceToTheTargetLocation()));
    }
    /**
     * Tests the {@code calculateRoute()} method.
     * 
     * Verifies that the vehicle correctly generates its travel route
     * as a string showing the sequence of locations to visit.
     * Checks both a direct route and one with an intermediate recharge stop.
     */
    @Test
    public void testCalculateRoute(){
        v1.calculateRoute();
        assertEquals("5-8 -> 20-20", v1.getStringRoute());
        v1.setTargetLocation(new Location(120, 120));
        v1.calculateRoute();
        assertEquals("5-8 -> 20-20 -> 120-120", v1.getStringRoute());
    }
    /**
     * Tests the {@code recharge()} method.
     * 
     * Simulates the vehicle recharging process at a station.
     * Verifies that the battery level is restored to full capacity,
     * the recharge count and total cost are updated,
     * and the vehicle no longer has a pending recharging location.
     */
    @Test
    public void testRecharge(){
        v1.setRechargingLocation(target);
        v1.setBatteryLevel(0);
        v1.recharge(0);
        assertEquals(200, v1.getBatteryLevel());
        assertEquals(1, v1.getChargesCount());
        assertEquals(20, v1.getChargestCost());
        assertFalse(v1.hasRechargingLocation());
    }
}