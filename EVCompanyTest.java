import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Test implementation of the {@link EVCompany} class.
 * Provides unit tests for methods like {@code addElectricVehicle()}, 
 * {@code addChargingStation()}, and {@code getChargingStation()}.
 * @author (Tu nombre)
 * @version (Hoy)
 */
public class EVCompanyTest
{
    private EVCompany company;
    private Location loc1;
    private Location loc2;
    private ChargingStation station1;
    private ChargingStation station2;
    private ElectricVehicle ev1;
    private ElectricVehicle ev2;

    /**
     * Default constructor for test class EVCompanyTest
     */
    public EVCompanyTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     * This ensures each test runs with a clean, independent company object.
     */
    @BeforeEach
    public void setUp()
    {
        company = new EVCompany("EVCharging Cáceres");
        
        loc1 = new Location(10, 5);
        loc2 = new Location(1, 1);
        
        station1 = new ChargingStation("Cáceres", "CC00", loc1);
        station2 = new ChargingStation("Cáceres", "CC01", loc2);
        
        Location evStart = new Location(0, 0);
        Location evTarget = new Location(19, 19);
        ev1 = new ElectricVehicle(company, evStart, evTarget, "EV1", "01-CCC", 60);
        ev2 = new ElectricVehicle(company, evStart, evTarget, "EV2", "02-CCC", 80);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        company = null;
        loc1 = null;
        loc2 = null;
        station1 = null;
        station2 = null;
        ev1 = null;
        ev2 = null;
    }

    /**
     * Test the EVCompany constructor.
     * Verifies that the name is correct and all collections are initialized empty.
     */
    @Test
    public void testConstructor()
    {
        assertEquals("EVCharging Cáceres", company.getName());
        
        assertNotNull(company.getVehicles());
        assertTrue(company.getVehicles().isEmpty());
        
        assertNotNull(company.getCityStations());
        assertTrue(company.getCityStations().isEmpty());
        
        assertEquals(0, company.getNumberOfStations());
    }

    /**
     * Test the {@code addElectricVehicle} method.
     */
    @Test
    public void testAddElectricVehicle()
    {
        company.addElectricVehicle(ev1);
        assertEquals(1, company.getVehicles().size());
        assertTrue(company.getVehicles().contains(ev1));
        
        company.addElectricVehicle(ev2);
        assertEquals(2, company.getVehicles().size());
        assertTrue(company.getVehicles().contains(ev2));
        
        company.addElectricVehicle(null);
        assertEquals(2, company.getVehicles().size());
    }
    
    /**
     * Test the {@code addChargingStation} method.
     */
    @Test
    public void testAddChargingStation()
    {
        company.addChargingStation(station1);
        assertEquals(1, company.getCityStations().size());
        assertEquals(1, company.getNumberOfStations());
        assertTrue(company.getCityStations().contains(station1));
        
        company.addChargingStation(station2);
        assertEquals(2, company.getNumberOfStations());
        assertTrue(company.getCityStations().contains(station2));

        company.addChargingStation(null);
        assertEquals(2, company.getNumberOfStations());
    }
    
    /**
     * Test the {@code getChargingStation(String id)} method.
     */
    @Test
    public void testGetChargingStationById()
    {
        company.addChargingStation(station1);
        company.addChargingStation(station2);

        assertEquals(station1, company.getChargingStation("CC00"));
        assertEquals(station2, company.getChargingStation("CC01"));
    }
    
    /**
     * Test the {@code getChargingStation(String id)} method for a non-existent ID.
     */
    @Test
    public void testGetChargingStationByIdNotFound()
    {
        company.addChargingStation(station1);
        
        assertNull(company.getChargingStation("CC99"));
        assertNull(company.getChargingStation("AAAA")); 
    }
    
    /**
     * Test the {@code getChargingStation(Location location)} method.
     */
    @Test
    public void testGetChargingStationByLocation()
    {
        company.addChargingStation(station1);
        company.addChargingStation(station2);
        
        assertEquals(station1, company.getChargingStation(loc1));
        assertEquals(station2, company.getChargingStation(loc2));
    }
    
    /**
     * Test the {@code getChargingStation(Location location)} method for a non-existent location.
     */
    @Test
    public void testGetChargingStationByLocationNotFound()
    {
        company.addChargingStation(station1);
        
        Location loc3 = new Location(5, 10);
        assertNull(company.getChargingStation(loc3));
    }
    
    /**
     * Test the {@code reset} method.
     */
    @Test
    public void testReset()
    {
        // Añadir datos
        company.addElectricVehicle(ev1);
        company.addChargingStation(station1);
        
        // Comprobar que los datos están
        assertEquals(1, company.getVehicles().size());
        assertEquals(1, company.getNumberOfStations());
        
        // Resetear la compañía
        company.reset();
        
        // Comprobar que todo está vacío
        assertEquals(0, company.getVehicles().size());
        assertEquals(0, company.getNumberOfStations());
        assertTrue(company.getCityStations().isEmpty());
    }
}