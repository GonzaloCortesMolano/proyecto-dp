import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test implementation of the {@link Location} class.
 * Provides unit tests for methods like {@code distance()} and {@code nextLocation()}.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2016.02.29
 * @version 2024.10.07 DP classes (adaptado a Java 8+)
 */
public class LocationTest
{
    private Location ubi1= new Location(0,0);
    private Location ubi2= new Location(4,3);
    private Location ubi3= new Location(10,10);
    private Location ubi4=new Location(1,1);
    
    /**
     * Default constructor for test class LocationTest.
     */
    public LocationTest()
    {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // No setup required for Location tests
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // No teardown required for Location tests
    }
    
    /**
     * Test the {@code Location} parameterized constructor method of the {@link Location} class.
     * Verifies that the x and y coordinates are correctly initialized 
     * when creating a new Location object with specific values.
     */
    @Test
    public void testLocation()
    {
       Location a=new Location(5, 2);
       assertEquals(5, a.getX());
       assertEquals(2, a.getY());
    }

    /**
     * Test the {@code distance} method of the {@link Location} class.
     * Checks distances from a central location to surrounding points.
     */
    @Test
    public void testDistance()
    {
        assertEquals(4 ,ubi1.distance(ubi2));
        assertEquals(0 ,ubi2.distance(ubi2));
        assertEquals(7 ,ubi3.distance(ubi2));
    }
    
    /**
     * Test the {@code nextLocation} method when the destination is adjacent 
     * (one step away in any direction).
     */
    @Test
    public void testAdjacentLocations()
    {
        assertEquals( ubi1.nextLocation(ubi4), ubi4 );
        assertEquals( ubi4.nextLocation(ubi1), ubi1 );
    }
    
    /**
     * Test the {@code nextLocation} method when the destination is not adjacent 
     * (more than one step away).
     */
    @Test
    public void testNonAdjacentLocations()
    {
        Location nextStepLocation = new Location(3,2);
        Location destiny = new Location(3,2);
        //caso1 restan ambos
        assertEquals( ubi2.nextLocation(ubi1), nextStepLocation );
        
        //caso2 suman ambos
        nextStepLocation.setX(5);
        nextStepLocation.setY(4);
        assertEquals( ubi2.nextLocation(ubi3), nextStepLocation );
        
        //caso3 quieto
        assertEquals( ubi2.nextLocation(ubi2), ubi2 );
        
        //caso4 x resta e y suma
        nextStepLocation.setX(3);
        nextStepLocation.setY(4);
        destiny.setX(2);
        destiny.setY(4);
        assertEquals( ubi2.nextLocation(destiny), nextStepLocation );
    }
    
    /**
     * Tests the {@code equals(Object)} method of the {@link Location} class.
     * Ensures that two {@link Location} objects with the same coordinates 
     * are considered equal, and that objects with different coordinates are not.
     */
    @Test
    public void testEquals(){
        assertFalse(ubi1.equals(ubi2));
        Location otro=new Location(4, 3);
        assertTrue(ubi2.equals(otro));
    }
    
    /**
     * Tests the {@code toString()} method of the {@link Location} class.
     * Verifies that the string representation of a {@link Location} object
     * matches the expected format containing its x and y coordinates.
     */
    @Test
    public void testToString(){
        assertEquals("4-3", ubi2.toString());
    }
}