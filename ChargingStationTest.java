import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ChargingStation} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 11-11-2025
 * Provides unit tests for core functionalities such as managing chargers,
 * tracking recharged vehicles, and verifying station properties.
 * 
 * Tests include:
 * - Getting a free charger
 * - Counting the total number of recharged vehicles
 * - Station construction and property getters
 * - Adding chargers to a station
 */
public class ChargingStationTest
{
    private ChargingStation station1;
    private ChargingStation station2;
    private Charger charger1;
    private Charger charger2;
    private Charger charger3;
    private Charger charger4;
    private ElectricVehicle eVehicle1;
    private ElectricVehicle eVehicle2;
    private EVCompany Vectalia;
    /**
     * Default constructor for test class ChargingStationTest
     */
    public ChargingStationTest()
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
     *
     * Called after every test case method.
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
     * Ensures that the method returns the first charger that is free
     * (i.e., has {@code free == true}) among all chargers in the station.
     * Also validates behavior when some chargers are occupied.
     */
    @Test
    public void testGetFreeCharger()
    {
       Charger chargerFree = station1.getFreeCharger();
       assertEquals(charger1, chargerFree);
    }
    
    /**
     * Tests the {@code getNumberEVRecharged()} method.
     * Verifies that the method correctly sums all electric vehicles
     * recharged across all chargers in the station.
     * Checks both a station with recharged vehicles and a station with none.
     */
    @Test
    public void testGetNumerEVRecharged()
    {
        assertEquals(1, station1.getNumberEVRecharged());
        assertEquals(0, station2.getNumberEVRecharged());
    }
    
    /**
     * Tests the {@link ChargingStation} constructor and some of its getters.
     * Ensures that the station is created with the correct city, ID, and location.
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
     * Tests the {@code addCharger()} method.
     * Ensures that chargers can be added to a station and that
     * the station's list of chargers updates its size correctly.
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
     * Tests the {@code getCompleteInfo()} method.
     * Ensures that the string is in the same format than as required.
     */
    @Test
    public void testGetCompleteInfo()
    {
        ChargingStation otra=new ChargingStation("Cáceres","CC01", new Location(10, 11));
        otra.addCharger(new StandardCharger("CC01_003", 80, 0.8));
        otra.addCharger(new StandardCharger("CC01_002", 60, 0.6));
        otra.addCharger(new StandardCharger("CC01_001", 40, 0.4));
        otra.addCharger(new StandardCharger("CC01_000", 20, 0.2));
        assertEquals("(ChargingStation: CC01, Cáceres, 0, 10-11)\n(StandardCharger: CC01_003, 80kwh, 0.80€, 0, 0.00€)\n(StandardCharger: CC01_002, 60kwh, 0.60€, 0, 0.00€)\n(StandardCharger: CC01_001, 40kwh, 0.40€, 0, 0.00€)\n(StandardCharger: CC01_000, 20kwh, 0.20€, 0, 0.00€)", otra.getCompleteInfo());
    }
}