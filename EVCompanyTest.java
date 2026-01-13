import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Test class for the {@link EVCompany} class.
 * <p>
 * Provides unit tests for core functionalities of {@code EVCompany}, including:
 * <ul>
 *   <li>Creation of a company and initialization of attributes.</li>
 *   <li>Adding electric vehicles and verifying that null values are ignored.</li>
 *   <li>Adding charging stations and checking the station count.</li>
 *   <li>Searching for charging stations by ID or location, including not-found cases.</li>
 *   <li>Resetting company data to ensure a clean state.</li>
 * </ul>
 * </p>
 * <p>
 * Author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano<br>
 * Version: 12-11-2025
 * </p>
 */
public class EVCompanyTest
{
    /** The singleton instance of the company under test. */
    private EVCompany company;

    /** Location for the first charging station used in tests. */
    private Location loc1;

    /** Location for the second charging station used in tests. */
    private Location loc2;

    /** The first charging station instance used for testing. */
    private ChargingStation station1;

    /** The second charging station instance used for testing. */
    private ChargingStation station2;

    /** The first electric vehicle instance used in tests. */
    private ElectricVehicle ev1;

    /** The second electric vehicle instance used in tests. */
    private ElectricVehicle ev2;

    /**
     * Default constructor for test class EVCompanyTest
     */
    public EVCompanyTest()
    {
    }

    /**
     * Sets up the test fixture.
     * <p>
     * Called before every test case method.
     * Initializes the company, locations, stations, and electric vehicles,
     * ensuring a clean environment for each test execution.
     * </p>
     */
    @BeforeEach
    public void setUp()
    {
        //Company
        EVCompany.resetInstance();
        company = EVCompany.getInstance();
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
        ev1 = new StandardEV(company, evStart, evTarget, "EV1", "01-CCC", 60);
        ev2 = new StandardEV(company, evStart, evTarget, "EV2", "02-CCC", 80);
    }

     /**
     * Tears down the test fixture.
     * <p>
     * Called after every test case method.
     * Frees all references to ensure full independence between tests.
     * </p>
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
     * <p>
     * Verifies that:
     * <ul>
     *   <li>The company name is correct.</li>
     *   <li>The lists of vehicles and stations are initialized and empty.</li>
     *   <li>The number of stations is zero.</li>
     * </ul>
     * </p>
     */
    @Test
    public void testConstructor()
    {
        assertEquals("Compañía EVCharging Cáceres", company.getName());
        
        assertNotNull(company.getVehicles());
        assertTrue(company.getVehicles().isEmpty());
        
        assertNotNull(company.getCityStations());
        assertTrue(company.getCityStations().isEmpty());
        
        assertEquals(0, company.getNumberOfStations());
    }

    /**
     * Tests the {@code addElectricVehicle()} method.
     * <p>
     * Verifies that vehicles can be added to the company list
     * and that {@code null} inputs are ignored without affecting existing data.
     * </p>
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
     * <p>
     * Ensures that charging stations can be correctly added to the company,
     * increasing the total count, and that {@code null} entries are ignored.
     * </p>
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
     * Tests {@code getChargingStation(String id)} with valid IDs.
     * <p>
     * Ensures that stations can be retrieved correctly by their ID.
     * </p>
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
     * Tests {@code getChargingStation(String id)} with invalid IDs.
     * <p>
     * Ensures that {@code null} is returned when a station with the given ID does not exist.
     * </p>
     */ 
    @Test
    public void testGetChargingStationByIdNotFound()
    {
        company.addChargingStation(station1);
        
        assertNull(company.getChargingStation("CC99"));
        assertNull(company.getChargingStation("AAAA")); 
    }
    
    /**
     * Tests {@code getChargingStation(Location location)} with valid locations.
     * <p>
     * Ensures that the correct station is returned when a location matches a registered station.
     * </p>
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
     * Tests {@code getChargingStation(Location location)} with unknown locations.
     * <p>
     * Ensures that {@code null} is returned when no station matches the given location.
     * </p>
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
     * <p>
     * Ensures that all company data is cleared, including:
     * <ul>
     *   <li>Electric vehicles list.</li>
     *   <li>Charging stations list.</li>
     *   <li>Station count.</li>
     * </ul>
     * </p>
     */
    @Test
    public void testReset()
    {
        company.addElectricVehicle(ev1);
        company.addChargingStation(station1);
        // Check that data is present
        assertEquals(1, company.getVehicles().size());
        assertEquals(1, company.getNumberOfStations());
        
        // Reset the Company
        company.reset();
        
        // Check that everything is empty
        assertEquals(0, company.getVehicles().size());
        assertEquals(0, company.getNumberOfStations());
        assertTrue(company.getCityStations().isEmpty());
    }
}