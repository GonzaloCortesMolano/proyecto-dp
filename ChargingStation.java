import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents an Electric Vehicle Charging Station that contains multiple
 * {@link Charger} units.
 * Each station is identified by a unique ID and is associated with a specific
 * city and {@link Location}.
 *
 * The class provides methods to manage its chargers, obtain information about
 * available chargers, and calculate the total number of vehicles recharged.
 *
 * This class forms part of the electric vehicle simulation model.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 13-1-2026
 */
public class ChargingStation
{
    
    /**
     * Unique identifier of the charging station.
     */
    private String id;
    
    /**
     * City where the charging station is located.
     */
    private String city;
    
    /**
     * Physical {@link Location} of the charging station.
     */
    private Location location;
    
    /**
     * List of {@link Charger}s available at this station.
     */
    private List<Charger> chargers;
    
    /**
     * Constructs a new {@link ChargingStation}.
     *
     * @param city The city where the station is located. Must not be {@code null} or empty.
     * @param id The unique identifier of the station. Must not be {@code null} or empty.
     * @param location The {@link Location} of the station. Must not be {@code null}.
     * @throws NullPointerException If {@code city}, {@code id}, or {@code location} is {@code null} or invalid.
     */
    public ChargingStation(String city, String id, Location location)
    {   
        try{
            if(id == null || id.isEmpty()){
                throw new NullPointerException("ChargingStation need a valid id (Not null or empty)");
            }
            if(city == null || city.isEmpty()) {
                throw new NullPointerException("ChargingStation need a valid city (Not null or empty)");
            }
            if(location == null) {
                throw new NullPointerException("ChargingStation need a valid Location");
            }        
            this.id = id;
            this.city = city;
            this.location = location;
            chargers = new ArrayList<Charger>();
        } catch(NullPointerException e){
            System.err.println("Error in the creation of the ChargingStation: " + e.getMessage());
        }
    }
    
    // -------------------------------------------------
    // -------------------- Getters --------------------
    // -------------------------------------------------
    
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
    
    // -------------------------------------------------
    // -------------------- Setters --------------------
    // -------------------------------------------------

    /**
     * Sets the {@link Location} of this charging station.
     *
     * @param location The new location.
     */
    public void setLocation(Location location){
        this.location = location;
    }
    
    // ------------------------------------------------
    // -------------------- Others --------------------
    // ------------------------------------------------
    
    /**
     * Retrieves the first free {@link Charger} available at the station.
     *
     * @return A free {@link Charger}, or {@code null} if no charger is free.
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
     * Retrieves the first free {@link Charger} compatible with a given vehicle type.
     *
     * @param typeVehicle The vehicle type required by the charger.
     * @return A compatible free {@link Charger}, or {@code null} if none is available.
     */
    public Charger getFreeCharger(Enum typeVehicle)
    {
        Charger c=null;
        for(int i=0; c == null && i < chargers.size();i++){
            Charger actual=chargers.get(i);
            if(actual.getFree()&&actual.compareType(typeVehicle)){
                c = chargers.get(i);
            }
        }  
        return c;
    }

    /**
     * Returns a detailed textual description of this charging station,
     * including all contained {@link Charger}s and their usage information.
     *
     * @return A complete multi-line description of the station.
     */
    public String getCompleteInfo()

    {

        String texto=this.toString();

        for(Charger c: chargers){

            texto+= c.getCompleteInfo();

        }
        texto+="\n";
        texto = texto.trim();
        return texto;

    }
    
    /**
     * Returns a short textual summary of this charging station.
     *
     * @return A formatted string containing the station's main information.
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
        return "(ChargingStation: " + getId() + ", " + getCity() +", " + getNumberEVRecharged() + ", " + getLocation().toString() + ")\n";
    }
    
    /**
     * Adds a new {@link Charger} to this charging station.
     *
     * @param charger The charger to add.
     */
    public void addCharger(Charger charger)
    {
        this.chargers.add(charger);
    }
    
    /**
     * Searches for the cheapest charger in the station that is free and compatible with the vehicle.
     * This method iterates through the available chargers and selects the one with the lowest fee.
     *
     * @param vehicle The {@link ElectricVehicle} requesting the charge.
     * @return The cheapest compatible {@link Charger} available, or {@code null} if none are found.
     */
    public Charger getCheapestCharger(ElectricVehicle vehicle) {
        Charger best = null;
        for (Charger c : chargers) {
            if (c.getFree() && c.canCharge(vehicle)) {
                if (best == null || c.getChargingFee() < best.getChargingFee()) {
                    best = c;
                }
            }
        }
        return best;
    }

    /**
     * Searches for the fastest charger in the station that is free and compatible with the vehicle.
     * This method iterates through the available chargers and selects the one with the highest charging speed.
     *
     * @param vehicle The {@link ElectricVehicle} requesting the charge.
     * @return The fastest compatible {@link Charger} available, or {@code null} if none are found.
     */
    public Charger getFastestCharger(ElectricVehicle vehicle) {
        Charger best = null;
        for (Charger c : chargers) {
            if (c.getFree() && c.canCharge(vehicle)) {
                if (best == null || c.getChargingSpeed() > best.getChargingSpeed()) {
                    best = c;
                }
            }
        }
        return best;
    }
    
    /**
     * Compares this charging station with another object for equality.
     * Two stations are considered equal if they share the same ID.
     *
     * @param obj The object to compare.
     * @return {@code true} if both objects represent the same station;
     *         {@code false} otherwise.
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
        return this.id.equals(other.id); //añadir mas
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
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
    
    /**
     * Sorts the internal list of chargers using {@link ComparatorChargers}.
     */
    public void orderList(){
        Collections.sort(chargers, new ComparatorChargers());
    }
}