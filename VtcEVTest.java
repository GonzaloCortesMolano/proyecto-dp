import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
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
    private VtcEV vehicle;

    /** The singleton EVCompany instance used in tests. */
    private EVCompany company;

    /** The starting location of the vehicle. */
    private Location startLoc;

    /** The target location of the vehicle. */
    private Location targetLoc;
    
    /**
     * Default constructor for test class VtcEVTest.
     * <p>
     * Initializes a new instance of {@code VtcEVTest}.
     * </p>
     */
    public VtcEVTest()
    {
    }

    /**
     * Sets up the test fixture before each test method.
     * <p>
     * Initializes the VtcEV instance with a distant target to force charger selection.
     * </p>
     */
    @BeforeEach
    public void setUp()
    {
        EVCompany.resetInstance();
        company = EVCompany.getInstance();
        startLoc = new Location(0,0);
        targetLoc = new Location(100, 100); // Destino lejano
        
        // 100 batería vs 1000 coste destino -> Busca cargador.
        vehicle = new VtcEV(company, startLoc, targetLoc, "VTC", "VTC01", 100);
    }

    /**
     * Tears down the test fixture after each test method.
     * <p>
     * Resets object references to ensure a clean state between test methods.
     * </p>
     */
    @AfterEach
    public void tearDown()
    {
        vehicle = null; company = null; startLoc = null; targetLoc = null;
    }


    /**
     * Tests the charger selection strategy for VtcEV.
     * <p>
     * Ensures that the VtcEV chooses the charger with the lowest cost per kWh,
     * even if it requires traveling a longer distance.
     * </p>
     */
    @Test
    public void testChargerSelectionStrategy()
    {
        // Station A: Expensive (0.90 €/kWh) at (5,5). 
        // Distancia: 10. Coste: 50.
        Location locA = new Location(5,5);
        ChargingStation stA = new ChargingStation("City", "A", locA);
        stA.addCharger(new StandardCharger("EXP", 50, 0.90));
        
        // Station B: Cheap (0.10 €/kWh) at (10,10). 
        // Distancia: 20. Coste: 100.
        Location locB = new Location(10,10);
        ChargingStation stB = new ChargingStation("City", "B", locB);
        stB.addCharger(new SolarCharger("CHEAP", 50, 0.10));
        
        company.addChargingStation(stA);
        company.addChargingStation(stB);
        
        // Ponemos batería 200:
        // Suficiente para llegar a la A (50) y a la B (100).
        vehicle.setBatteryLevel(200);
        
        vehicle.calculateRoute();
        
        // Debe elegir B porque es más barata
        assertEquals(locB, vehicle.getRechargingLocation());
    }

    /**
     * Tests the {@code equals(Object)} method.
     * <p>
     * Ensures that two VtcEV instances with the same ID are considered equal,
     * while vehicles with different IDs are not equal.
     * </p>
     */
    @Test
    public void testEquals()
    {
        VtcEV same = new VtcEV(company, startLoc, targetLoc, "VTC", "VTC01", 100);
        VtcEV diff = new VtcEV(company, startLoc, targetLoc, "VTC", "VTC99", 100);
        
        assertEquals(vehicle, same);
        assertNotEquals(vehicle, diff);
    }
}