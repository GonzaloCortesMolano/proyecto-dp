import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link VtcEV} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of VtcEV, including:
 * - Charger selection strategy (based on lowest charging cost).
 * - Equality checks.
 */
public class VtcEVTest
{
    private VtcEV vehicle;
    private EVCompany company;
    private Location startLoc;
    private Location targetLoc;

    /**
     * Default constructor for test class VtcEVTest.
     */
    public VtcEVTest()
    {
    }

    /**
     * Sets up the test fixture.
     * * Initializes the vehicle with a distant target to ensure 'calculateRoute' 
     * triggers the search for a charging station.
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
     * Tears down the test fixture.
     */
    @AfterEach
    public void tearDown()
    {
        vehicle = null; company = null; startLoc = null; targetLoc = null;
    }

    /**
     * Tests the specific charger selection strategy for VtcEV.
     * * VtcEV should choose the charger with the lowest fee per kWh.
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