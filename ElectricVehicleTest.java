import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class ElectricVehicleTest.
 *
 * @author Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez
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
     */
    @AfterEach
    public void tearDown()
    {
    }
    
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
    
    @Test
    public void testEnoughBattery(){
        assertTrue(v1.enoughBattery(v1.distanceToTheTargetLocation()));
        v1.setTargetLocation(new Location(120, 120));
        assertFalse(v1.enoughBattery(v1.distanceToTheTargetLocation()));
    }
    
    @Test
    public void testCalculateRoute(){
        v1.calculateRoute();
        assertEquals("5-8 -> 20-20", v1.getStringRoute());
        v1.setTargetLocation(new Location(120, 120));
        v1.calculateRoute();
        assertEquals("5-8 -> 20-20 -> 120-120", v1.getStringRoute());
    }
    
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