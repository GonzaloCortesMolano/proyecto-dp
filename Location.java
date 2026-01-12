import java.util.*;

/**
 * Represents an immutable position in a two-dimensional grid-based city,
 * defined by non-negative (x, y) coordinates.
 * <p>
 * Locations are used to model positions of vehicles, charging stations,
 * and destinations within the simulation.
 * </p>
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2026.13.1
 */
public class Location
{
    private int x;
    private int y; 

    /**
     * Model a location in the city.
     * @param x The x coordinate. Must be non-negative.
     * @param y The y coordinate. Must be non-negative.
     * @throws IllegalArgumentException If a coordinate is negative.
     */
    public Location(int x, int y)
    {
        if(x < 0) {
            throw new IllegalArgumentException(
                        "Negative x-coordinate: " + x);
        }
        if(y < 0) {
            throw new IllegalArgumentException(
                        "Negative y-coordinate: " + y);
        }        
        this.x = x;
        this.y = y;
    }
    
    /**
     * Computes the next intermediate location when moving from this location
     * towards a destination.
     * <p>
     * Movement follows a Chebyshev-style step, allowing diagonal or straight
     * movement of one unit per axis.
     * </p>
     *
     * @param destination The destination location.
     * @return A new {@link Location} one step closer to the destination,
     *         or the destination itself if it has already been reached.
     */
    public Location nextLocation(Location destination)
    {
        Location siguiente = new Location(this.getX(),this.getY());
        int resultado=Integer.compare(this.getX(),destination.getX());
        if(resultado!=0)
            siguiente.setX(this.getX()-resultado);
        resultado=Integer.compare(this.getY(),destination.getY());
        if(resultado!=0)
            siguiente.setY(this.getY()-resultado);
        return siguiente;
    }
    
    /**
     * Calculates the distance from this location to another one using
     * Chebyshev distance.
     * <p>
     * This distance corresponds to the maximum difference between
     * the x or y coordinates and represents the number of movement steps
     * required to reach the destination.
     * </p>
     *
     * @param destination The destination location.
     * @return The number of movement steps required.
     */
    public int distance(Location destination)
    {
        return Math.max(Math.abs(destination.getX()-this.x), Math.abs(destination.getY()-this.y));
    }
    
    /**
     * Compares this location with another object for equality.
     * <p>
     * Two locations are considered equal if they have the same x and y coordinates.
     * </p>
     *
     * @param other The object to compare with.
     * @return {@code true} if the object is a {@link Location} with the same
     *         coordinates, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other)
    {
        if(other instanceof Location) {
            Location otherLocation = (Location) other;
            return x == otherLocation.getX() &&
                   y == otherLocation.getY();
        }
        else {
            return false;
        }
    }
    
    /**
     * Returns a string representation of this location.
     *
     * @return A string in the format {@code "x-y"}.
     */
    @Override
    public String toString()
    {
        return x + "-" + y;
    }

    /**
     * Returns a hash code for this location.
     * <p>
     * The hash code is built using the x and y coordinates and is consistent
     * with the {@link #equals(Object)} method.
     * </p>
     *
     * @return A hash code value for this location.
     */
    @Override
    public int hashCode()
    {
        return (y << 16) + x;
    }

    /**
     * @return The x coordinate.
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return The y coordinate.
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * Set the current X position .
     * @param x New X position it is.
     */
    public void setX(int x) {
        this.x=x;
    }
    
    /**
     * Set the current Y position .
     * @param y New Y position it is.
     */
    public void setY(int y){
        this.y=y;
    }
}