import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class StandardChargerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StandardChargerTest
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
     * Default constructor for test class StandardChargerTest
     */
    public StandardChargerTest()
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
        charger1 = new StandardCharger("CH1", 40, 0.25);
        charger2 = new StandardCharger("CH2", 50, 0.30);
        charger3 = new StandardCharger("CH3", 30, 0.20);
        
        //Company
        EVCompany.resetInstance();
        Vectalia = EVCompany.getInstance();
        
        //Locations need it for the Electric Vehicles
        Location loc1 = new Location(5, 7);
        Location loc2 = new Location(12, 9);
        Location loc3 = new Location(8, 8);
        Location locEV1 = new Location(4,7);
        Location locEV2 = new Location(10,9);
        Location locEV3 = new Location(2,14);
        
        //Vehicles
        eVehicle1 = new StandardEV(Vectalia, locEV1, loc1, "Tesla", "CC12", 50);
        eVehicle2 = new StandardEV(Vectalia, locEV2, loc2, "Tesla", "CC15", 60);
        eVehicle3 = new VtcEV(Vectalia, locEV3, loc3, "Tesla", "CC20", 50);
        eVehicle4 = new PremiumEV(Vectalia, locEV1, loc1, "Tesla", "CC70", 40);
        eVehicle5 = new PremiumEV(Vectalia, locEV1, loc1, "Tesla", "CC70", 40);

        
        //Add one recharged vehicle manually
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
        Charger chargerTest = new StandardCharger("CHTest", 50, 0.50);
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
        charger2.recharge(eVehicle2, 20);
        charger2.recharge(eVehicle3, 25);
        charger3.recharge(eVehicle4, 10);
        charger3.recharge(eVehicle5, 15);
        assertEquals(0, charger3.getNumberEVRecharged());
        assertEquals(2, charger2.getNumberEVRecharged());
    }
    
    @Test
    public void addEvRecharged()
    {
       charger2.addEvRecharged(eVehicle2);
       charger2.addEvRecharged(eVehicle3);
       assertEquals(2, charger2.getNumberEVRecharged());
    }
    
    @Test
    public void testRecharge()
    {
        charger2.recharge(eVehicle2, 20);
        charger2.recharge(eVehicle3, 25);
        assertEquals(2, charger2.getNumberEVRecharged());
        assertEquals(13.5, charger2.getAmountCollected());
        charger3.recharge(eVehicle4, 50);
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
        assertEquals(4, charger3.getAmountCollected());
        
    }
    
    @Test
    public void testGetCompleteInfo()
    {
        String fee = String.format(java.util.Locale.US, "%.1f", charger2.getChargingFee());
        String amount = String.format(java.util.Locale.US, "%.2f", charger2.getAmountCollected());

        assertEquals("(" + charger2.getClass().getSimpleName() + ": "+charger2.getId()+", "+charger2.getChargingSpeed()+"kwh, "
        + fee +"€, "+charger2.getNumberEVRecharged()+", "+ amount +"€)\n", charger2.getCompleteInfo());
    
        charger2.recharge(eVehicle1, 20);
        charger2.recharge(eVehicle2, 30);
    
        amount = String.format(java.util.Locale.US, "%.2f", charger2.getAmountCollected());

        assertEquals("(" + charger2.getClass().getSimpleName() + ": "+charger2.getId()+", "+charger2.getChargingSpeed()+"kwh, "
    + fee +"€, "+charger2.getNumberEVRecharged()+", "+ amount +"€)\n"
    +eVehicle1.toString()+"\n"+eVehicle2.toString()+"\n", charger2.getCompleteInfo());
    }
    
    @Test
    public void testEquals(){
        Charger sameRef = charger1;
        Charger differentRef = new StandardCharger("CH1", 40, 0.25);
        Charger difType = new PriorityCharger("CH1", 40, 0.25);
        
        //Caso el mismo objeto creo, pq apunta a la misma direccion de memoria
        assertEquals(true, charger1.equals(sameRef));
        //Mismo tipo distinto id
        assertEquals(false, charger2.equals(sameRef));
        //Distintas direcciones de memoria
        assertEquals(true, charger1.equals(differentRef));
        //Mismos datos, distintas direcciones de memoria, y distinto tipo
        assertEquals(false, charger1.equals(difType));
        //Con null
        assertFalse(charger1.equals(null));
    }
}