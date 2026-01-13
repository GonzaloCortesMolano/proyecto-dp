import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PremiumEV} class.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 * * Provides unit tests for specific behaviors of PremiumEV, including:
 * - Charger selection strategy (based on highest charging speed).
 * - Equality checks.
 */
public class PremiumEVTest
{
    private PremiumEV vehicle;
    private EVCompany company;
    private Location startLoc;
    private Location targetLoc;

    /**
     * Default constructor for test class PremiumEVTest.
     */
    public PremiumEVTest()
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
        targetLoc = new Location(100, 100); // Destino lejano para forzar recarga
        
        // Inicializamos con batería 100.
        // Distancia a (100,100) es 200 pasos. Coste 1000. 
        // 100 < 1000 -> enoughBattery = false -> Buscará estación.
        vehicle = new PremiumEV(company, startLoc, targetLoc, "Premium", "PRM01", 100);
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
     * Tests the specific charger selection strategy for PremiumEV.
     * * PremiumEV should choose the charger with the highest charging speed,
     * regardless of distance (assuming reachable).
     */
    @Test
    public void testChargerSelectionStrategy()
    {
        // Station A: Slow Charger (50 kwh) at (5,5). 
        // Distancia: 10. Coste llegada: 50.
        Location locA = new Location(5,5);
        ChargingStation stA = new ChargingStation("City", "A", locA);
        stA.addCharger(new UltraFastCharger("SLOW", 50, 1.0));
        
        // Station B: Fast Charger (150 kwh) at (10,10). 
        // Distancia: 20. Coste llegada: 100.
        Location locB = new Location(10,10);
        ChargingStation stB = new ChargingStation("City", "B", locB);
        stB.addCharger(new UltraFastCharger("FAST", 150, 1.0));
        
        company.addChargingStation(stA);
        company.addChargingStation(stB);
        
        // Ponemos batería 200:
        // - No llega al destino final (necesita 1000).
        // - Llega a Station A (necesita 50).
        // - Llega a Station B (necesita 100).
        vehicle.setBatteryLevel(200); 
        
        vehicle.calculateRoute();
        
        // Debe elegir B porque es más rápida, aunque esté más lejos
        assertEquals(locB, vehicle.getRechargingLocation());
    }

    /**
     * Tests the {@code equals(Object)} method.
     */
    @Test
    public void testEquals()
    {
        PremiumEV same = new PremiumEV(company, startLoc, targetLoc, "Premium", "PRM01", 100);
        PremiumEV diff = new PremiumEV(company, startLoc, targetLoc, "Premium", "PRM99", 100);
        
        assertEquals(vehicle, same);
        assertNotEquals(vehicle, diff);
    }
}