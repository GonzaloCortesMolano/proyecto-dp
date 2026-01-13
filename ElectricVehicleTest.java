import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test implementation of the {@link ElectricVehicle} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for the core functionalities of the {@code ElectricVehicle} class,
 * validating inheritance and common behavior across all Vehicle types.
 * Tests include:
 * - Vehicle construction and initial state
 * - Battery sufficiency checks
 * - Route calculation logic (Template Method)
 * - Recharging process and cost updates
 * - Movement simulation (act)
 */
public class ElectricVehicleTest
{
    private ElectricVehicle standardV;
    private ElectricVehicle vtcEV;
    private ElectricVehicle premiumEV;
    private ElectricVehicle priorityEV;
    
    private EVCompany company;
    private Location initialLocation;
    private Location targetLocation;
    private ChargingStation station;
    
    /**
     * Default constructor for test class ElectricVehicleTest.
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
        EVCompany.resetInstance();
        company = EVCompany.getInstance();
        
        initialLocation = new Location(5, 5);
        targetLocation = new Location(20, 20);
                
        station = new ChargingStation("Cáceres", "1", targetLocation);
        station.addCharger(new StandardCharger("CH_STD", 40, 0.20));
        station.addCharger(new SolarCharger("CH_SOL", 50, 0.25));
        station.addCharger(new UltraFastCharger("CH_FST", 100, 0.50));
        station.addCharger(new PriorityCharger("CH_PRI", 60, 0.30));
        
        company.addChargingStation(station);
        
        standardV = new StandardEV(company, initialLocation, targetLocation, "Standard", "STD01", 100);
        vtcEV = new VtcEV(company, initialLocation, targetLocation, "VTC", "VTC01", 100);
        premiumEV = new PremiumEV(company, initialLocation, targetLocation, "Premium", "PRM01", 100);
        priorityEV = new PriorityEV(company, initialLocation, targetLocation, "Priority", "PRI01",100);
        
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
        standardV = null; vtcEV = null; premiumEV = null; priorityEV = null;
        company = null;
        initialLocation = null; targetLocation = null; station = null;
    }
    
    /**
     * Tests the {@link ElectricVehicle} constructor and its getter methods.
     * Verifies that all attributes are correctly initialized for different vehicle types.
     */
    @Test
    public void testCreation()
    {
        // Test with StandardEV
        assertEquals("Standard", standardV.getName());
        assertEquals("STD01", standardV.getPlate());
        assertEquals(100, standardV.getBatteryCapacity());
        assertEquals(100, standardV.getBatteryLevel()); // Starts full
        assertEquals(VehicleTier.STANDARD, standardV.getType());
        assertEquals(initialLocation, standardV.getLocation());
        assertEquals(targetLocation, standardV.getTargetLocation());
        
        // Test with PremiumEV
        assertEquals("Premium", premiumEV.getName());
        assertEquals(VehicleTier.PREMIUM, premiumEV.getType());
        assertEquals(company, premiumEV.getCompany());
    }
    
    /**
     * Tests the {@code enoughBattery()} method.
     * 
     * Ensures that the vehicle can correctly determine whether it has enough battery
     * to reach its target location. It checks both a reachable and an unreachable destination.
     */
    @Test
    public void testEnoughBattery(){
        assertTrue(standardV.enoughBattery(standardV.distanceToTheTargetLocation()));
        
        standardV.setTargetLocation(new Location(1000, 1000));
        assertFalse(standardV.enoughBattery(standardV.distanceToTheTargetLocation()));}
    
    /**
     * Tests the {@code calculateRoute()} method.
     * 
     * Verifies that the vehicle correctly generates its travel route
     * as a string showing the sequence of locations to visit.
     * Checks both a direct route and one with an intermediate recharge stop.
     */
    @Test
    public void testCalculateRoute()
    {
        //Enough battery
        standardV.calculateRoute();
        String route = standardV.getStringRoute();
        assertTrue(route.contains(initialLocation.toString()));
        assertTrue(route.contains(targetLocation.toString()));
        
        //Not enough battery (recalcula la ruta hasta estación)
        standardV.setBatteryLevel(10); 
        standardV.calculateRoute();
        route = standardV.getStringRoute();
        
        assertTrue(route.contains(station.getLocation().toString()));
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
       // Move vehicle to station logically
        standardV.setRechargingLocation(station.getLocation());
        standardV.setLocation(station.getLocation());
        standardV.setBatteryLevel(0); // Empty battery
        
        standardV.recharge(0);
        
        // Assertions
        assertEquals(100, standardV.getBatteryLevel());
        assertEquals(1, standardV.getChargesCount());
        // Cost should be > 0 (StandardCharger 0.20 * 100 = 20.0)
        assertEquals(20.0, standardV.getChargesCost(), 0.01);
        assertFalse(standardV.hasRechargingLocation());
    }
    
    /**
     * Tests the {@code act()} method.
     * * Verifies that the vehicle executes a movement step correctly,
     * updating its location and reducing its battery level.
     */
    /*
    @Test
    public void testAct()
    {
        Location start = standardV.getLocation();
        int battery = standardV.getBatteryLevel();
        
        standardV.act(0);
        
        assertNotEquals(start, standardV.getLocation());
        assertTrue(standardV.getBatteryLevel() < battery); 
    }
    */
}