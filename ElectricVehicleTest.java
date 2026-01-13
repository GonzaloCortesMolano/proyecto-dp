import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ElectricVehicle} class.
 * <p>
 * Provides unit tests for core functionalities of ElectricVehicle and its subclasses:
 * <ul>
 *   <li>Vehicle construction and initialization across all tiers.</li>
 *   <li>Battery sufficiency checks using {@code enoughBattery()}.</li>
 *   <li>Route calculation logic including intermediate recharge stops.</li>
 *   <li>Recharging process and cost updates.</li>
 *   <li>Movement simulation using {@code act()}.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano<br>
 * Version: 12-11-2025
 * </p>
 */
public class ElectricVehicleTest
{
    /** Standard vehicle instance under test. */
    private ElectricVehicle standardV;

    /** VTC vehicle instance under test. */
    private ElectricVehicle vtcEV;

    /** Premium vehicle instance under test. */
    private ElectricVehicle premiumEV;

    /** Priority vehicle instance under test. */
    private ElectricVehicle priorityEV;

    /** Singleton EVCompany instance used across tests. */
    private EVCompany company;

    /** Initial location used in tests. */
    private Location initialLocation;

    /** Target location used in tests. */
    private Location targetLocation;

    /** Charging station used for testing recharge behavior. */
    private ChargingStation station;
    
    /**
     * Default constructor for test class ElectricVehicleTest.
     */
    public ElectricVehicleTest()
    {
    }

    /**
     * Sets up the test fixture before each test.
     * <p>
     * Initializes the company, vehicles, and a charging station with multiple chargers.
     * </p>
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
     * Tears down the test fixture after each test.
     * <p>
     * Resets all references to ensure clean state for each test method.
     * </p>
     */
    @AfterEach
    public void tearDown()
    {
        standardV = null; vtcEV = null; premiumEV = null; priorityEV = null;
        company = null;
        initialLocation = null; targetLocation = null; station = null;
    }
    
    /**
     * Tests the {@link ElectricVehicle} constructor and getters.
     * <p>
     * Ensures correct initialization of attributes for different vehicle types.
     * </p>
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
     * Tests {@code enoughBattery()} method.
     * <p>
     * Verifies that the vehicle correctly identifies if it has enough battery to reach its target.
     * Checks both a reachable and an unreachable destination.
     * </p>
     */
    @Test
    public void testEnoughBattery(){
        assertTrue(standardV.enoughBattery(standardV.distanceToTheTargetLocation()));
        
        standardV.setTargetLocation(new Location(1000, 1000));
        assertFalse(standardV.enoughBattery(standardV.distanceToTheTargetLocation()));
    }
    
    /**
     * Tests the {@code calculateRoute()} method.
     * <p>
     * Verifies that the vehicle correctly generates its travel route
     * as a string showing the sequence of locations to visit.
     * Checks both a direct route and one with an intermediate recharge stop.
     * </p>
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
     * Tests {@code recharge()} method.
     * <p>
     * Simulates a recharge at a station and verifies:
     * <ul>
     *   <li>Battery level is restored to full capacity.</li>
     *   <li>Number of charges and total cost are updated.</li>
     *   <li>The vehicle no longer has a pending recharging location.</li>
     * </ul>
     * </p>
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
     * Tests {@code act()} method.
     * <p>
     * Verifies that the vehicle performs a movement step correctly, updating its location
     * and reducing its battery level accordingly.
     * </p>
     */
    
    @Test
    public void testAct()
    {
        Location start = standardV.getLocation();
        int battery = standardV.getBatteryLevel();
        
        standardV.possibilities(0); //llamada a possibilities para no mostrar en consola
        
        assertNotEquals(start, standardV.getLocation());
        assertTrue(standardV.getBatteryLevel() < battery); 
    }
}