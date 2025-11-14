import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Charger} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * 
 * Provides unit tests for the core functionalities of the {@code Charger} class,
 * including:
 * - Charger construction and initial state
 * - Counting recharged electric vehicles
 * - Adding vehicles to the recharged list
 * - Performing recharges and updating collected amounts
 * - Retrieving complete information in formatted string output
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
     * Initializes chargers, company, vehicles, and links them
     * to simulate a realistic charging scenario.
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
        eVehicle2 = new ElectricVehicle(Vectalia, locEV2,loc2, "Tesla", "CC15", 60);
        eVehicle3 = new ElectricVehicle(Vectalia, locEV3,loc3, "Tesla", "CC20", 50);
        
        //Add one recharged vehicle manually
        charger1.addEvRecharged(eVehicle1);
        
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     * Clears all object references to ensure test isolation.
     */
    @AfterEach
    public void tearDown()
    {
        charger1 = null;
        charger2 = null;
        charger3 = null;
        eVehicle1 = null;
        eVehicle2 = null;
        eVehicle3 = null;
        Vectalia = null;
    }
    
    /**
     * Tests the {@link Charger} constructor and its getter methods.
     * Ensures that a new charger is initialized with correct properties:
     * ID, charging speed, empty recharged list, zero collected amount,
     * and marked as free.
     */
    public void testCharger()
    {
        Charger chargerTest = new Charger("CHTest", 50, 0.50);
        assertEquals("CHTest", chargerTest.getId());
        assertEquals(50, chargerTest.getChargingSpeed());
        assertEquals(0 ,chargerTest.getEVsRecharged().size());
        assertEquals(0 ,chargerTest.getAmountCollected());
        assertEquals(true ,chargerTest.getFree());
    }
    
    /**
     * Tests the {@code getNumberEVRecharged()} method.
     * Verifies that it correctly counts the number of electric vehicles
     * recharged by the charger after multiple operations.
     */
    @Test
    public void testGetNumberEVRecharged()
    {
        assertEquals(0, charger3.getNumberEVRecharged());
        charger2.recharge(eVehicle2, 20);
        charger2.recharge(eVehicle2, 25);
        assertEquals(2, charger2.getNumberEVRecharged());
    }
    
    /**
     * Tests the {@code addEvRecharged()} method.
     * Ensures that manually adding vehicles to the recharged list
     * correctly increments the internal counter.
     */
    @Test
    public void addEvRecharged()
    {
       charger2.addEvRecharged(eVehicle2);
       charger2.addEvRecharged(eVehicle3);
       assertEquals(2, charger2.getNumberEVRecharged());
    }
    
    /**
     * Tests the {@code recharge()} method.
     * Validates that performing recharges increases the counter
     * of recharged electric vehicles appropriately and the amount collected.
     */
    @Test
    public void testRecharge()
    {
        charger2.recharge(eVehicle2, 20);
        charger2.recharge(eVehicle2, 25);
        assertEquals(2, charger2.getNumberEVRecharged());
        assertEquals(13,5, charger2.getAmountCollected());
    }
    
    /**
     * Tests the {@code updateAmountCollected()} method.
     * Ensures that the amount collected by a charger updates correctly
     * when new amounts are added, either directly or through recharges.
     */
    @Test
    public void updateAmountCollected()
    {
        charger2.updateAmountCollected(300);
        assertEquals(300, charger2.getAmountCollected());
        charger1.setAmountCollected(100);
        charger1.updateAmountCollected(50);
        assertEquals(150, charger1.getAmountCollected());
        charger3.recharge(eVehicle3, 20);
        assertEquals(4, charger3.getAmountCollected());
        
    }
    
    /**
     * Tests the {@code getCompleteInfo()} method.
     * Verifies that the method returns a formatted string representation
     * of the charger and its associated vehicles (if any).
     */
    @Test
    public void testGetCompleteInfo()
    {
        assertEquals("(Charger: "+charger2.getId()+", "+charger2.getChargingSpeed()+"kwh, "
        +charger2.getChargingFee()+"€, "+charger2.getNumberEVRecharged()+", "+charger2.getAmountCollected()+"€)\n", charger2.getCompleteInfo());
        charger2.recharge(eVehicle2, 20);
        charger2.recharge(eVehicle3, 30);
        assertEquals("(Charger: "+charger2.getId()+", "+charger2.getChargingSpeed()+"kwh, "
        +charger2.getChargingFee()+"€, "+charger2.getNumberEVRecharged()+", "+charger2.getAmountCollected()+"€)\n"
        +eVehicle2.toString()+"\n"+eVehicle3.toString()+"\n", charger2.getCompleteInfo());
    }
}