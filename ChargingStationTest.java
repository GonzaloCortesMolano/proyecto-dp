import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class ChargingStationTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
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
        
        station1 = new ChargingStation("Caceres", "CC1", loc1);
        station2 = new ChargingStation("Caceres", "CC2", loc2);
        
        //Chargers for the ChargingStation
        charger1 = new Charger("CH1", 40, 0.25f);
        charger2 = new Charger("CH2", 50, 0.30f);
        charger3 = new Charger("CH3", 30, 0.20f);
        charger4 = new Charger("CH4", 60, 0.40f);
        
        //Is necesary for one test
        charger3.setFree(false);
        
        //Add the chargers to the station
        station1.getChargers().add(charger1);
        station1.getChargers().add(charger2);
        station1.getChargers().add(charger3);
        station1.getChargers().add(charger4);
        //Company
        Vectalia = new EVCompany();
        //Vehicles
        eVehicle1 = new ElectricVehicle(Vectalia, locEV1, loc1, "Tesla", "CC12", 50);
        eVehicle1 = new ElectricVehicle(Vectalia, locEV2,loc2, "Tesla", "CC15", 60);
        
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
     * 
     */
    @Test
    public void testGetFreeCharger()
    {
       Charger chargerFree = station1.getFreeCharger();
       assertEquals(charger3, chargerFree);
    }
    /**
     * 
     */
    @Test
    public void testGetNumerEVRecharged()
    {
        assertEquals(2, station1.getNumberEVRecharged());
        assertEquals(0, station2.getNumberEVRecharged());
    }
    /**
     * 
     */
    @Test
    public void testChargingStation()
    {
        Location loc3 = new Location(10,9);
        ChargingStation stationTest = new ChargingStation("Caceres", "CC3", loc3);
        assertEquals("Caceres", stationTest.getCity());
        assertEquals("CC3", stationTest.getId());
        assertEquals(loc3, stationTest.getLocation());
    }
    /**
     * 
     */
    @Test
    public void testAddCharger()
    {
        
    }
}