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
    private Charger charger1; //standard
    private Charger charger2; //solar
    private Charger charger3; //premium
    private Charger charger4; //priority
    private ElectricVehicle eVehicle1; //standard
    private ElectricVehicle eVehicle2; //vtc
    private ElectricVehicle eVehicle3; //premium
    private ElectricVehicle eVehicle4; //priority
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
        
        //Company
        EVCompany.resetInstance();
        Vectalia = EVCompany.getInstance();
        //Chargers
        charger1 = new StandardCharger("CH1", 40, 0.25);
        charger2 = new SolarCharger("CH2", 50, 0.30);
        charger3 = new UltraFastCharger("CH3", 30, 0.20);
        charger4 = new PriorityCharger("CH4", 60, 0.50);
        
        //Locations need it for the Electric Vehicles
        Location loc1 = new Location(5, 7);
        Location loc2 = new Location(12, 9);
        Location loc3 = new Location(8, 8);
        Location locEV1 = new Location(4,7);
        Location locEV2 = new Location(10,9);
        Location locEV3 = new Location(2,14);
        Location locEV4 = new Location(1,1);
        
        //Vehicles
        eVehicle1 = new StandardEV(Vectalia, locEV1, loc1, "Tesla Std", "CC12", 50);
        eVehicle2 = new VtcEV(Vectalia, locEV2, loc2, "Tesla VTC", "CC15", 60);
        eVehicle3 = new PremiumEV(Vectalia, locEV3, loc3, "Tesla Pro", "CC20", 50);
        eVehicle4 = new PriorityEV(Vectalia, locEV4, loc1, "Ambulance", "CC99", 50);
        
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
        charger4 = null;
        eVehicle1 = null;
        eVehicle2 = null;
        eVehicle3 = null;
        eVehicle4 = null;
        Vectalia = null;
    }
    
    /**
     * Tests the {@link Charger} constructor and its getter methods.
     * Ensures that a new charger is initialized with correct properties:
     * ID, charging speed, empty recharged list, zero collected amount,
     * and marked as free.
     */
    @Test
    public void testCharger()
    {
        // Probamos con el Solar (CH2)
        assertEquals("CH2", charger2.getId());
        assertEquals(50, charger2.getChargingSpeed());
        assertEquals(0, charger2.getEVsRecharged().size());
        assertEquals(0, charger2.getAmountCollected());
        assertTrue(charger2.getFree());
    }
    
    /**
     * Tests the {@code getNumberEVRecharged()} method.
     * Verifies that it correctly counts the number of electric vehicles
     * recharged by the charger after multiple operations.
     */
    @Test
    public void testGetNumberEVRecharged()
    {
        assertEquals(1, charger1.getNumberEVRecharged());
        
        //VTC
        charger2.recharge(eVehicle2, 20);
        charger2.recharge(eVehicle2, 25);
        assertEquals(2, charger2.getNumberEVRecharged());
        
        //Premium
        charger3.recharge(eVehicle3, 10);
        assertEquals(1, charger3.getNumberEVRecharged());
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
        
        charger1.recharge(eVehicle1, 20);
        // Standard: 20 * 0.25 = 5.0
        assertEquals(5.0, charger1.getAmountCollected(), 0.01);
        
        
        charger2.recharge(eVehicle2, 20);
        // Solar: 20 * 0.30 * 0.9 = 5.4
        assertEquals(5.4, charger2.getAmountCollected(), 0.01);
        
        
        charger3.recharge(eVehicle3, 50);
        // UltraFast: 50 * 0.20 * 1.1 = 11.0
        assertEquals(11.0, charger3.getAmountCollected(), 0.01);
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
        // UltraFast: 20 * 0.20 * 1.1 = 4.4
        assertEquals(4.4, charger3.getAmountCollected());
        
    }
    
    /**
     * Tests the {@code getCompleteInfo()} method.
     * Verifies that the method returns a formatted string representation
     * of the charger and its associated vehicles (if any).
     */
    @Test
    public void testGetCompleteInfo()
    {
        String fee = String.format(java.util.Locale.US, "%.1f", charger2.getChargingFee());
        
        //verificamos que contenga la clase correcta (SolarCharger) y el ID CH2
        String info = charger2.getCompleteInfo();
        assertTrue(info.contains("SolarCharger"));
        assertTrue(info.contains("CH2"));
        assertTrue(info.contains(fee));
        
        //recargamos y verificamos que salga el coche
        charger2.recharge(eVehicle2, 30);
        info = charger2.getCompleteInfo();
        assertTrue(info.contains("Tesla VTC"));
    }
}