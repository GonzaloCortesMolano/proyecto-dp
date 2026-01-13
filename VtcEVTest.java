import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link VtcEV} class.
 * <p>
 * Provides unit tests for core functionalities of VtcEV:
 * <ul>
 *   <li>Creation and initialization of VtcEV objects.</li>
 *   <li>Calculation of the recharging position.</li>
 *   <li>Retrieval of free chargers from a station.</li>
 *   <li>Comparison of chargers to determine the better one.</li>
 *   <li>Equality checks using {@code equals(Object)}.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano<br>
 * Version: 12-11-2025
 * </p>
 */
public class VtcEVTest
{
    
    /** The VtcEV instance under test. */
    private ElectricVehicle v1;

    /** The singleton EVCompany instance used in tests. */
    private EVCompany c;

    /** A generic location used in tests. */
    private Location l;

    /** The target location for the vehicle. */
    private Location target;

    /** A charger instance used in tests. */
    private Charger ch;

    /** A charging station used to test charger selection (bad choice). */
    private ChargingStation stationBad;

    /** A charging station used to test charger selection (good choice). */
    private ChargingStation stationGood;
    
    /**
     * Default constructor for the test class.
     * <p>
     * Initializes a new instance of {@code VtcEVTest}.
     * </p>
     */
    public VtcEVTest()
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
        c = EVCompany.getInstance();
        l = new Location(0, 15);
        v1 = new VtcEV(c, l, new Location(200, 15), "name", "plate", 200);
        target = new Location(200, 15);
        stationBad = new ChargingStation("Cáceres", "1", new Location(14,16));
        
        stationGood = new ChargingStation("Cáceres", "2", new Location(13,16));
        c.addChargingStation(stationBad);
        c.addChargingStation(stationGood);
        ch=new UltraFastCharger("id", 60, 0.1);
        stationBad.addCharger(ch);
        ch=new UltraFastCharger("id3", 60, 0.1);
        stationGood.addCharger(ch);
        ch=new SolarCharger("id2", 60, 0.1);
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
        v1 = null;
        c = null;
        l = null;
        target = null;
        ch = null;
        stationBad = null;
        stationGood = null;
    }
    
    /**
     * Tests the creation and proper initialization of a VtcEV instance.
     */
    @Test
    public void testCreation(){
        EVCompany.resetInstance();
        EVCompany otra= EVCompany.getInstance();
        v1=new VtcEV(otra, new Location(5, 8), new Location(200, 15), "name", "plate", 200);
        
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
        
        assertEquals(v1.getType(), VehicleTier.VTC);
    }
    
    /**
     * Tests the {@code calculateRechargingPosition()} method.
     */
    @Test
    public void testCalculateRechargingPosition(){
        v1.calculateRechargingPosition();
        assertEquals(v1.getRechargingLocation(), stationGood.getLocation());
        
        ch=new StandardCharger("id3", 10, 0.0); 
        stationBad.addCharger(ch);
        v1.calculateRechargingPosition();
        assertEquals(v1.getRechargingLocation(), stationBad.getLocation());
    }
    
    @Test
    public void testGetFreeChargerFromStation(){
        v1.setRechargingLocation(stationGood.getLocation());
        Charger c=v1.getFreeChargerFromStation();
        assertEquals(c, new SolarCharger("id2", 60, 0.1));
        
        ch=new StandardCharger("id3", 60, 0.0);
        stationGood.addCharger(ch);
        ch=new SolarCharger("id4", 60, 0.1);
        stationGood.addCharger(ch);
        c=v1.getFreeChargerFromStation();
        assertEquals(c, new StandardCharger("id3", 60, 0.0));
    }
    
    @Test
    public void testIsBetterCharger(){
        //Caso cargador null
        assertEquals(true, v1.isBetterCharger(ch, null, l, null));
        //Caso 2 cargadores
        Location badLocation = new Location(30, 30);
        Charger badCharger = new UltraFastCharger("C001", 60, 1.0);
        assertEquals(true, v1.isBetterCharger(ch, badCharger, l, badLocation));
    }
    
    @Test
    public void testEquals(){
        assertEquals(v1, new VtcEV(EVCompany.getInstance(), new Location(5, 8), new Location(15, 15), "name", "plate", 200));
        assertNotEquals(v1, new StandardEV(EVCompany.getInstance(), new Location(5, 8), new Location(15, 15), "name", "plate", 200));
        assertNotEquals(v1, new VtcEV(EVCompany.getInstance(), new Location(5, 8), new Location(15, 15), "name", "plate2", 200));
    }
}