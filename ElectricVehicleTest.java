

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class ElectricVehicleTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ElectricVehicleTest
{
    private ElectricVehicle v1;
    
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
        EVCompany c =new EVCompany("nueva");
        Location l = new Location(5, 8);
        Location target = new Location(20, 20);
        v1=new ElectricVehicle(new EVCompany("nueva"), new Location(5, 8), new Location(20, 20), "name", "plate", 200);
        assertTrue(c.equals(v1.getCompany()) && l.equals(v1.getLocation())
        assertTrue()
        && !v1.hasRechargingLocation() && v1.getTargetLocation().equals(target)
        && v1.getName().equals("name") && v1.getPlate().equals("plate")
        && v1.getBatteryCapacity()==200 && v1.getIdleCount()==0
        && v1.getBatteryLevel()==200 && v1.getIdleCount()==0
        && v1.getKwsCharged()==0 && v1.getChargesCount()==0
        && v1.getChargestCost()==0);
    }
}