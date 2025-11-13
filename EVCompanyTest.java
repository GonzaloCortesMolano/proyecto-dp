import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Test class for the {@link EVCompany} class.
 * 
 * @author: Sergio Zambrano, Gonzalo Cofrtes, Ricardo ALvarez
 * @version 12-11-2025
 * 
 * Provides unit tests for the core functionalities of the {@code EVCompany} class,
 * including:
 * - Creation of a company and initialization of attributes
 * - Adding electric vehicles and charging stations
 * - Searching charging stations by ID or location
 * - Resetting company data
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
     * Initializes the company, locations, stations, and electric vehicles,
     * ensuring a clean environment for each test execution.
     */
    @BeforeEach
    public void setUp()
    {
        //Company
        company = new EVCompany("EVCharging Cáceres");
        
        //Locations
        loc1 = new Location(10, 5);
        loc2 = new Location(1, 1);
        
        //Stations
        station1 = new ChargingStation("Cáceres", "CC00", loc1);
        station2 = new ChargingStation("Cáceres", "CC01", loc2);
        
        //Locations for the Vehicle
        Location evStart = new Location(0, 0);
        Location evTarget = new Location(19, 19);
        //Vehicles
        ev1 = new ElectricVehicle(company, evStart, evTarget, "EV1", "01-CCC", 60);
        ev2 = new ElectricVehicle(company, evStart, evTarget, "EV2", "02-CCC", 80);
    }

     /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     * Frees all references to ensure full independence between tests.
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
     * Tests the {@link EVCompany} constructor.
     * Ensures that a company object is correctly initialized with:
     * - The expected name
     * - Empty lists for vehicles and stations
     * - A station count of zero
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
     * Tests the {@code addElectricVehicle()} method.
     * Verifies that vehicles can be added to the company list
     * and that {@code null} inputs are ignored without affecting existing data.
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
     * Tests the {@code addChargingStation()} method.
     * Ensures that charging stations can be correctly added to the company,
     * increasing the total count, and that {@code null} entries are ignored.
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
     * Tests the {@code getChargingStation(String id)} method.
     * Verifies that stations can be correctly retrieved
     * by their unique identifier.
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
     * Tests the {@code getChargingStation(String id)} method
     * for cases where the ID does not correspond to any registered station.
     * Ensures that the method returns {@code null}.
     */
    @Test
    public void testGetChargingStationByIdNotFound()
    {
        company.addChargingStation(station1);
        
        assertNull(company.getChargingStation("CC99"));
        assertNull(company.getChargingStation("AAAA")); 
    }
    
    /**
     * Tests the {@code getChargingStation(Location location)} method.
     * Ensures that the correct station is returned when a valid
     * location is provided.
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
     * Tests the {@code getChargingStation(Location location)} method
     * when the location does not match any registered station.
     * Verifies that the method returns {@code null}.
     */
    @Test
    public void testGetChargingStationByLocationNotFound()
    {
        company.addChargingStation(station1);
        
        Location loc3 = new Location(5, 10);
        assertNull(company.getChargingStation(loc3));
    }
    
    /**
     * Tests the {@code reset()} method.
     * Verifies that invoking {@code reset()} clears all company data,
     * including its list of electric vehicles and charging stations.
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