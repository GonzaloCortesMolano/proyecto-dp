import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ChargingStation} class.
 * <p>
 * Provides unit tests for core functionalities of ChargingStation:
 * <ul>
 *   <li>Station construction and property getters.</li>
 *   <li>Adding chargers to a station.</li>
 *   <li>Retrieving a free charger using {@code getFreeCharger()}.</li>
 *   <li>Counting the total number of recharged vehicles across all chargers.</li>
 *   <li>Retrieving complete information in a formatted string.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés, Sergio Zambrano<br>
 * Version: 11-11-2025
 * </p>
 */class ChargingStationTest
{
    /** The first charging station used in the tests. */
    private ChargingStation station1;
    
    /** The second charging station used in the tests. */
    private ChargingStation station2;
    
    /** The first charger associated with a charging station for testing purposes. */
    private Charger charger1;
    
    /** The second charger associated with a charging station for testing purposes. */
    private Charger charger2;
    
    /** The third charger associated with a charging station for testing purposes. */
    private Charger charger3;
    
    /** The fourth charger associated with a charging station for testing purposes. */
    private Charger charger4;
    
    /** The first electric vehicle used in the tests. */
    private ElectricVehicle eVehicle1;
    
    /** The second electric vehicle used in the tests. */
    private ElectricVehicle eVehicle2;
    
    /** The singleton instance of the EV company used in the tests. */
    private EVCompany Vectalia;
    
    /**
     * Default constructor for test class ChargingStationTest
     */
    public ChargingStationTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
     * <p>
     * Initializes stations, chargers, vehicles, and company instance.
     * Also pre-recharges some vehicles to simulate occupied chargers.
     * </p>
     */
    @BeforeEach
    public void setUp()
    {
        //Locations for the ChargingStation
        Location loc1 = new Location(5, 7);
        Location loc2 = new Location(12, 9);
        Location locEV1 = new Location(4,7);
        Location locEV2 = new Location(10,9);
        
        //Stations
        station1 = new ChargingStation("Caceres", "CC1", loc1);
        station2 = new ChargingStation("Caceres", "CC2", loc2);
        
        //Chargers for the ChargingStation
        charger1 = new StandardCharger("CH1", 40, 0.25);
        charger2 = new StandardCharger("CH2", 50, 0.30);
        charger3 = new StandardCharger("CH3", 30, 0.20);
        charger4 = new StandardCharger("CH4", 60, 0.40);
        
        //Add the chargers to the station
        station1.addCharger(charger1);
        station1.addCharger(charger2);
        station1.addCharger(charger3);
        station1.addCharger(charger4);
        //Company
        EVCompany.resetInstance();
        Vectalia = EVCompany.getInstance();
        //Vehicles
        eVehicle1 = new StandardEV(Vectalia, locEV1, loc1, "Tesla", "CC12", 50);
        eVehicle1 = new StandardEV(Vectalia, locEV2,loc2, "Tesla", "CC15", 60);
        
        //Recharge the vehicles
        charger1.recharge(eVehicle1, 20); 
        charger2.recharge(eVehicle2, 15); 
        
    }

    /**
     * Tears down the test fixture.
     * <p>
     * Called after every test case method.
     * </p>
     */
    @AfterEach
    public void tearDown()
    {
        station1 = null;
        station2 = null;
        charger1 = null;
        charger2 = null;
        charger3 = null;
        charger4 = null;
        eVehicle1 = null;
        eVehicle2 = null;
        Vectalia = null;
    }
    
    /**
     * Tests the {@code getFreeCharger()} method.
     * <p>
     * Ensures that the method returns the first charger that is free
     * (i.e., has {@code free == true}) among all chargers in the station.
     * Also validates behavior when some chargers are occupied.
     * </p>
     */
    @Test
    public void testGetFreeCharger()
    {
       Charger chargerFree = station1.getFreeCharger();
       assertEquals(charger1, chargerFree);
    }
    
    /**
     * Tests the {@code getNumberEVRecharged()} method.
     * <p>
     * Verifies that the method correctly sums all electric vehicles
     * recharged across all chargers in the station.
     * <p>
     * Checks both a station with recharged vehicles and a station with none.
     * <>/p
     */
    @Test
    public void testGetNumerEVRecharged()
    {
        assertEquals(1, station1.getNumberEVRecharged());
        assertEquals(0, station2.getNumberEVRecharged());
    }
    
    /**
     * Tests the {@link ChargingStation} constructor and basic getters.
     * <p>
     * Verifies that a newly created station has the correct city, ID, location,
     * and starts with zero chargers.
     * </p>
     */
    @Test
    public void testChargingStation()
    {
        Location loc3 = new Location(10,9);
        ChargingStation stationTest = new ChargingStation("Caceres", "CC3", loc3);
        assertEquals("Caceres", stationTest.getCity());
        assertEquals("CC3", stationTest.getId());
        assertEquals(loc3, stationTest.getLocation());
        assertEquals(0, stationTest.getChargers().size());
    }
    
    /**
     * Tests {@code addCharger()}.
     * <p>
     * Ensures that chargers can be added to a station and the internal list updates correctly.
     * </p>
     */
    @Test
    public void testAddCharger()
    {
        assertEquals(0, station2.getChargers().size());
        Charger charger5 = new StandardCharger("CH5", 60, 0.40f);
        station2.addCharger(charger5);
        assertEquals(1, station2.getChargers().size());
        Charger charger6 = new StandardCharger("CH6", 60, 0.40f);
        station2.addCharger(charger5);
        assertEquals(2, station2.getChargers().size());
    }
    
    /**
     * Tests {@code getCheapestCharger()}.
     * <p>
     * Verifies that the station selects the charger with the lowest fee among those available
     * and compatible with the vehicle.
     * </p>
     */
    @Test
    public void testGetCheapestCharger()
    {

        Charger cheapest = station1.getCheapestCharger(eVehicle1);
        assertEquals(charger3, cheapest, "Debería devolver charger3 (0.20€)");
        

        charger3.setFree(false);

        cheapest = station1.getCheapestCharger(eVehicle1);
        assertEquals(charger1, cheapest, "Si charger3 está ocupado, el siguiente es charger1");
    }

    /**
     * Tests {@code getFastestCharger()}.
     * <p>
     * Verifies that the station selects the charger with the highest charging speed among those available
     * and compatible with the vehicle.
     * </p>
     */
    @Test
    public void testGetFastestCharger()
    {

        Charger fastest = station1.getFastestCharger(eVehicle1);
        assertEquals(charger4, fastest, "Debería devolver charger4 (60kwh)");
        

        charger4.setFree(false);

        fastest = station1.getFastestCharger(eVehicle1);
        assertEquals(charger2, fastest, "Si charger4 está ocupado, el siguiente es charger2");
    }
    
    /**
     * Tests charger compatibility during selection.
     * <p>
     * Verifies that a vehicle type (Priority) that is not compatible with Standard chargers
     * does not receive any charger, even if they are free.
     * </p>
     */
    @Test
    public void testGetChargerCompatibility()
    {
        PriorityEV priorityCar = new PriorityEV(Vectalia, new Location(0,0), new Location(5,5), "Prio", "P0000", 50);
        
        Charger result = station1.getCheapestCharger(priorityCar);
        
        assertNull(result, "Un PriorityEV no debe recibir un StandardCharger aunque esté libre");
    }
    
    /**
     * Tests {@code getCompleteInfo()}.
     * <p>
     * Ensures that the method returns a string representation
     * containing the station and all its chargers in the correct format.
     * </p>
     */
    @Test
    public void testGetCompleteInfo()
    {
        ChargingStation otra=new ChargingStation("Cáceres","CC01", new Location(10, 11));
        otra.addCharger(new StandardCharger("CC01_003", 80, 0.8));
        otra.addCharger(new StandardCharger("CC01_002", 60, 0.6));
        otra.addCharger(new StandardCharger("CC01_001", 40, 0.4));
        otra.addCharger(new StandardCharger("CC01_000", 20, 0.2));
        assertEquals("(ChargingStation: CC01, Cáceres, 0, 10-11)\n(StandardCharger: CC01_003, 80kwh, 0.8€, 0, 0.00€)\n(StandardCharger: CC01_002, 60kwh, 0.6€, 0, 0.00€)\n(StandardCharger: CC01_001, 40kwh, 0.4€, 0, 0.00€)\n(StandardCharger: CC01_000, 20kwh, 0.2€, 0, 0.00€)", otra.getCompleteInfo());
    }
}