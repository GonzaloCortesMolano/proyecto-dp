

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class ChargerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ChargerTest
{
    private Charger charger1;
    private Charger charger2;
    private Charger charger3;
    private ElectricVehicle eVehicle1;
    private ElectricVehicle eVehicle2;
    private ElectricVehicle eVehicle3;
    private EVCompany Vectalia;
    /**
     * Default constructor for test class ChargerTest
     */
    public ChargerTest()
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
        //Chargers
        charger1 = new Charger("CH1", 40, 0.25);
        charger2 = new Charger("CH2", 50, 0.30);
        charger3 = new Charger("CH3", 30, 0.20);
        //Company
        Vectalia = new EVCompany("Vectalia");
        //Locations need it for the Electric Vehicles
        Location loc1 = new Location(5, 7);
        Location loc2 = new Location(12, 9);
        Location loc3 = new Location(8, 8);
        Location locEV1 = new Location(4,7);
        Location locEV2 = new Location(10,9);
        Location locEV3 = new Location(2,14);
        //Vehicles
        eVehicle1 = new ElectricVehicle(Vectalia, locEV1, loc1, "Tesla", "CC12", 50);
        eVehicle1 = new ElectricVehicle(Vectalia, locEV2,loc2, "Tesla", "CC15", 60);
        eVehicle1 = new ElectricVehicle(Vectalia, locEV3,loc3, "Tesla", "CC20", 50);
        
        charger1.addEvRecharged(eVehicle1);
        
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        charger1 = null;
        charger2 = null;
        charger3 = null;
    }
    /**
     * 
     */
    @Test
    public void testCharger()
    {
        Charger chargerTest = new Charger("CHTest", 50, 0.50);
        assertEquals("CHTest", chargerTest.getId());
        assertEquals(50, chargerTest.getChargingSpeed());
        assertEquals(null ,chargerTest.getEVsRecharged());
        assertEquals(0 ,chargerTest.getAmountCollected());
        assertEquals(true ,chargerTest.getFree());
    }
    /**
     * 
     */
    @Test
    public void testGetNumberEVRecharged()
    {
        assertEquals(0, charger3.getNumberEVRecharged());
        assertEquals(1, charger3.getNumberEVRecharged());
    }
}