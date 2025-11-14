import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents an Electric Vehicle Charging Station that contains multiple {@link Charger} units.
 * Each station is identified by a unique ID and is associated with a specific city and {@link Location}.
 * The class provides methods to manage its chargers, obtain information about available chargers,
 * and calculate the total number of vehicles recharged.
 *
 * This class forms part of the electric vehicle simulation model.
 *
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 11-11-2025
 */

public class ChargingStation
{
    private String id;
    private String city;
    private Location location;
    private List<Charger> chargers;
    
    /**
     * Constructs a new {@link ChargingStation}.
     *
     * @param city The city where the station is located.
     * @param id The unique identifier of the station.
     * @param location The {@link Location} of the station.
     */
    public ChargingStation(String city, String id, Location location)
    {
       this.id = id;
       this.city = city;
       this.location = location;
       chargers = new ArrayList<Charger>();
    }
    // ---------------------------------------------------
    // Getters
    // ---------------------------------------------------
    /**
     * @return The unique identifier of the charging station.
     */
    public String getId()
    {
        return this.id;
    }
    /**
     * @return The city where this station is located.
     */
    public String getCity(){
        return this.city;
    }
    
    /**
     * Returns the {@link Location} of the charging station. 
     *
     * @return The location coordinates.
     */
    public Location getLocation()
    {
       return this.location;
    }
    
    /**
     * Returns an unmodifiable list of all {@link Charger}s in this station.
     *
     * @return An unmodifiable list of chargers.
     */
    public List<Charger> getChargers()
    {
        return Collections.unmodifiableList(chargers); 
    }
    
    // ---------------------------------------------------
    // Setters
    // ---------------------------------------------------

    /**
     * Sets the current {@link Location} of this charging station.
     *
     * @param location The new location (must not be {@code null}).
     * @throws NullPointerException If the provided location is {@code null}.
     */
    public void setLocation(Location location){
        this.location = location;
    }
    
     /**
     * Replaces the list of managed {@link Charger}s with a new list.
     * This method overwrites the current collection of chargers. 
     * Use with caution, as it will remove any previously managed stations.
     * 
     * @param chargers The new list of {@link Charger}s to assign. 
     *      If {@code null}, the operation is ignored.
     */
    public void setChargers(List<Charger> chargers)
    {
        if(chargers != null)
            this.chargers = chargers;
    }
    
    /**
     * Retrieves the first free {@link Charger} available at the station.
     *
     * @return A free {@link Charger}, or {@code null} if no charger is currently free.
     * Note: The implementation assumes at least one free charger exists if called.**
     */
    public Charger getFreeCharger()
    {
        Charger c=null;
        for(int i=0; c == null && i < chargers.size();i++){
            if(chargers.get(i).getFree()){
                c = chargers.get(i);
            }
        }  
        return c;
    }
    
    /**
     * Calculates the total number of {@link ElectricVehicle}s recharged
     * across all {@link Charger}s at this station.
     *
     * @return The total number of recharge operations performed.
     */
    public int getNumberEVRecharged(){
        int total = 0;
        for(Charger c: chargers){
            total += c.getNumberEVRecharged();
        }
        return total;
    }

    /**
     * Returns a detailed textual description of this charging station,
     * including all contained {@link Charger}s and their individual usage information.
     *
     * @return A full multi-line string representation of this station and its chargers.
     */
    public String getCompleteInfo()

    {

        String texto=this.toString();

        for(Charger c: chargers){

            texto+=c.getCompleteInfo();

        }
        texto=texto.substring(0, texto.length()-1); //para quitar el ultimo enter
        return texto;

    }
    
    /**
     * Shows a final summary about this charging station.
     * Currently identical to {@link #toString()}.
     *
     * @return A string summary of the station’s final state.
     * @deprecated Use {@link #toString()} or {@link #getCompleteInfo()} instead.
     */
    @Deprecated
    public String showFinalInfo()
    {
        return toString();
    }

    /**
     * Returns a short string representation of this charging station,
     * including its ID, city, number of EVs recharged, and location.
     *
     * @return A formatted string with the station’s main information.
     */
    @Override
    public String toString()
    {
        return "(ChargingStation: " + getId() + ", " + getCity() +", " + getNumberEVRecharged() + ", " + getLocation().toString() + ")"+ "\n";
    }
    
    /**
     * Adds a new {@link Charger} to this charging station.
     *
     * @param charger The new charger to add (must not be {@code null}).
     * @throws NullPointerException If {@code charger} is {@code null}.
     */
    public void addCharger(Charger charger)
    {
        this.chargers.add(charger);
    }
    
    /**
     * Compares this charging station to another object for equality.
     * Two stations are considered equal if they share the same ID.
     *
     * @param obj The object to compare to.
     * @return {@code true} if the objects represent the same station; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof ChargingStation)) {
            return false;
        }
        ChargingStation other = (ChargingStation) obj;
        return this.id.equals(other.id);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     * The hash is derived from the station ID.
     *
     * @return A hash code for this charging station.
     */
    @Override
    public int hashCode()
    {
        int result = 7; 
        result = 3 * result + getId().hashCode(); 
        return result;
    }    
}