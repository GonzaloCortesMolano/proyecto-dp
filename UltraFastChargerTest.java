

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class UltraFastChargerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class UltraFastChargerTest
{
    private Charger charger1;
    private Charger charger2;
    private Charger charger3;
    private ElectricVehicle eVehicle1;
    private ElectricVehicle eVehicle2;
    private ElectricVehicle eVehicle3;
    private ElectricVehicle eVehicle4;
    private ElectricVehicle eVehicle5;
    private EVCompany Vectalia; 
    /**
     * Default constructor for test class UltraFastChargerTest
     */
    public UltraFastChargerTest()
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
        charger1 = new UltraFastCharger("CH1", 40, 0.25);
        charger2 = new UltraFastCharger("CH2", 50, 0.30);
        charger3 = new UltraFastCharger("CH3", 30, 0.20);
        
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
        eVehicle1 = new PremiumEV(Vectalia, locEV1, loc1, "Tesla", "CC12", 50);
        eVehicle2 = new PremiumEV(Vectalia, locEV2, loc2, "Tesla", "CC15", 60);
        eVehicle3 = new PriorityEV(Vectalia, locEV3, loc3, "Tesla", "CC20", 50);
        eVehicle4 = new StandardEV(Vectalia, locEV1, loc1, "Tesla", "CC70", 40);
        eVehicle5 = new VtcEV(Vectalia, locEV1, loc1, "Tesla", "CC70", 40);
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
        eVehicle1 = null;
        eVehicle2 = null;
        eVehicle3 = null;
        eVehicle4 = null;
        eVehicle5 = null;
        Vectalia = null;
    }
    
    @Test
    public void testCharger()
    {
        Charger chargerTest = new UltraFastCharger("CHTest", 50, 0.50);
        assertEquals("CHTest", chargerTest.getId());
        assertEquals(50, chargerTest.getChargingSpeed());
        assertEquals(0 ,chargerTest.getEVsRecharged().size());
        assertEquals(0 ,chargerTest.getAmountCollected());
        assertEquals(true ,chargerTest.getFree());
    }
    
    @Test
    public void testGetNumberEVRecharged()
    {
        assertEquals(0, charger3.getNumberEVRecharged());
        charger2.recharge(eVehicle1, 20);
        charger2.recharge(eVehicle2, 25);
        charger3.recharge(eVehicle3, 10);
        charger3.recharge(eVehicle5, 25);
        assertEquals(0, charger3.getNumberEVRecharged());
        assertEquals(2, charger2.getNumberEVRecharged());
    }
    
    @Test
    public void addEvRecharged()
    {
       charger2.addEvRecharged(eVehicle1);
       charger2.addEvRecharged(eVehicle2);
       assertEquals(2, charger2.getNumberEVRecharged());
    }
    
    @Test
    public void testRecharge()
    {
        charger2.recharge(eVehicle1, 20);
        charger2.recharge(eVehicle2, 25);
        assertEquals(2, charger2.getNumberEVRecharged());
        assertEquals(14.85, charger2.getAmountCollected());
        charger3.recharge(eVehicle3, 50);
        charger3.recharge(eVehicle4, 100);
        charger3.recharge(eVehicle5, 20);
        assertEquals(0, charger3.getNumberEVRecharged());
        assertEquals(0, charger3.getAmountCollected());
    }
    
    @Test
    public void updateAmountCollected()
    {
        charger2.updateAmountCollected(300);
        assertEquals(300, charger2.getAmountCollected());
        charger1.setAmountCollected(100);
        charger1.updateAmountCollected(50);
        assertEquals(150, charger1.getAmountCollected());
        charger3.recharge(eVehicle3, 20);
        charger3.recharge(eVehicle4, 100);
        assertEquals(0, charger3.getAmountCollected());
        
    }
    
    @Test
    public void testGetCompleteInfo()
    {
        assertEquals("(Charger: "+charger2.getId()+", "+charger2.getChargingSpeed()+"kwh, "
        +charger2.getChargingFee()+"€, "+charger2.getNumberEVRecharged()+", "+charger2.getAmountCollected()+"€)\n", charger2.getCompleteInfo());
        charger2.recharge(eVehicle1, 20);
        charger2.recharge(eVehicle2, 30);
        assertEquals("(Charger: "+charger2.getId()+", "+charger2.getChargingSpeed()+"kwh, "
        +charger2.getChargingFee()+"€, "+charger2.getNumberEVRecharged()+", "+charger2.getAmountCollected()+"€)\n"
        +eVehicle1.toString()+"\n"+eVehicle2.toString()+"\n", charger2.getCompleteInfo());
    }
}