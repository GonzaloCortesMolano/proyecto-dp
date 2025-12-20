

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class StandardEVTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StandardEVTest
{
    private ElectricVehicle v1;
    private EVCompany c;
    private Location l;
    private Location target;
    private Charger ch;
    
    /**
     * Default constructor for test class StandardEVTest
     */
    public StandardEVTest()
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
        l = new Location(0, 0);
        v1 = new StandardEV(c, l, new Location(15, 15), "name", "plate", 200);
        target = new Location(15, 15);
        ChargingStation stationBad = new ChargingStation("Cáceres", "1", target);
        
        ChargingStation stationGood = new ChargingStation("Cáceres", "2", new Location(21,21));
        c.addChargingStation(stationBad);
        c.addChargingStation(stationGood);
        ch=new SolarCharger("id", 60, 0.1);
        stationBad.addCharger(ch);
        ch=new SolarCharger("id3", 60, 0.1);
        ch=new StandardCharger("id2", 60, 0.1);
        stationGood.addCharger(ch);
    }
    
    @Test
    public void testCalculateRoute(){
        v1.calculateRoute();
        assertEquals("0-0 -> 15-15", v1.getStringRoute());
        v1.setTargetLocation(new Location(120, 120));
        v1.calculateRoute();
        assertEquals("0-0 -> 21-21 -> 120-120", v1.getStringRoute());
    }
    
    @Test
    public void testCreation(){
        v1=new StandardEV(new EVCompany("nueva"), new Location(5, 8), new Location(15, 15), "name", "plate", 200);
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
        assertEquals(v1.getChargesCost(), 0);
        
        assertEquals(v1.getType(), EnumVehicles.STANDARD);
    }
    
    @Test
    public void testGetFreeChargerFromStation(){
        v1.calculateRechargingPosition();
        Charger ch=new StandardCharger("id2", 60, 0.1);
        assertEquals(v1.getFreeChargerFromStation(), ch);
    }
    
    @Test
    public void testEquals(){
        assertEquals(v1, new StandardEV(new EVCompany("nueva"), new Location(5, 8), new Location(15, 15), "name", "plate", 200));
        assertNotEquals(v1, new PriorityEV(new EVCompany("nueva"), new Location(5, 8), new Location(15, 15), "name", "plate", 200));
        assertNotEquals(v1, new StandardEV(new EVCompany("nueva"), new Location(5, 8), new Location(15, 15), "name", "plate2", 200));
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}