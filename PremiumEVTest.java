

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class PremiumEVTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PremiumEVTest
{
    private ElectricVehicle v1;
    private EVCompany c;
    private Location l;
    private Location target;
    private Charger ch;
    private ChargingStation stationBad;
    private ChargingStation stationGood;
    /**
     * Default constructor for test class PremiumEVTest
     */
    public PremiumEVTest()
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
        l = new Location(0, 15);
        v1 = new PremiumEV(c, l, new Location(200, 15), "name", "plate", 200);
        target = new Location(200, 15);
        stationBad = new ChargingStation("Cáceres", "1", new Location(14,16));
        
        stationGood = new ChargingStation("Cáceres", "2", new Location(13,16));
        c.addChargingStation(stationBad);
        c.addChargingStation(stationGood);
        ch=new StandardCharger("id", 60, 0.1);
        stationBad.addCharger(ch);
        ch=new StandardCharger("id3", 60, 0.1);
        stationGood.addCharger(ch);
        ch=new UltraFastCharger("id2", 60, 0.1);
        stationGood.addCharger(ch);
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
    
    @Test
    public void testCreation(){
        v1=new PremiumEV(new EVCompany("nueva"), new Location(5, 8), new Location(200, 15), "name", "plate", 200);
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
        
        assertEquals(v1.getType(), VehicleTier.PREMIUM);
    }
    
    @Test
    public void testCalculateRechargingPosition(){
        v1.calculateRechargingPosition();
        assertEquals(v1.getRechargingLocation(), stationGood.getLocation());
        
        ch=new UltraFastCharger("id3", 300, 0.3); 
        stationBad.addCharger(ch);
        v1.calculateRechargingPosition();
        assertEquals(v1.getRechargingLocation(), stationBad.getLocation());
    }
    
    @Test
    public void testGetFreeChargerFromStation(){
        v1.setRechargingLocation(stationGood.getLocation());
        Charger c=v1.getFreeChargerFromStation();
        assertEquals(c, new UltraFastCharger("id2", 60, 0.1));
        
        ch=new UltraFastCharger("id3", 500, 0.0);
        stationGood.addCharger(ch);
        ch=new UltraFastCharger("id4", 400, 0.1);
        stationGood.addCharger(ch);
        c=v1.getFreeChargerFromStation();
        assertEquals(c, new UltraFastCharger("id3", 500, 0.0));
    }
}