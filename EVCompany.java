import java.util.*;

/**
 * Models the operation of an Electric Vehicle (EV) Company. 
 * It manages a fleet of {@link ElectricVehicle}s and a network of {@link ChargingStation}s.
 * @author DP classes
 * @version 2024.10.07
 */
public class EVCompany  
{
    /**
    * ID of the charging company
    */
    private String name;
    /**
    * Set of ElectricVehicle objects that are subscribed
    */
    private List<ElectricVehicle> subscribedVehicles;
    /**
    * Collection of charging stations managed by the company
    */
    private List<ChargingStation> stations;
    /**
     * Constructor for objects of class EVCompany.
     * @param name The name of the company.
     */
    public EVCompany(String name)
    {
        this.name = name; 
        this.subscribedVehicles = new ArrayList<>(); 
        this.stations = new ArrayList<>();
    }

     /**
     * @return The name of the company.
     */
    public String getName()
    {
        return this.name;
    }
    
    
    /**
     * @return An unmodifiable list of all {@link ElectricVehicle}s.
     */
    public List<ElectricVehicle> getVehicles()
    {       
        return Collections.unmodifiableList(subscribedVehicles);
    }

    /**
     * Adds an {@link ElectricVehicle} to the company's fleet.
     * @param vehicle The electric vehicle to add.
     */
    public void addElectricVehicle(ElectricVehicle vehicle)
    {       
        if (vehicle != null) {
            this.subscribedVehicles.add(vehicle);
        }
    }

    
    /**
     * Adds a {@link ChargingStation} to the company's network.
     * @param station The charging station to add.
     */
    public void addChargingStation(ChargingStation station)
    {       
        if (station != null) {
            this.stations.add(station);
        }    
    }
    
    
    /**
     * Retrieves a {@link ChargingStation} by its unique ID.
     * @param id The ID of the station to find.
     * @return The {@link ChargingStation} with the matching ID, or {@code null} if not found.
     */
    public ChargingStation getChargingStation(String id)
    {
        for (ChargingStation station : stations) {
            if (station.getId().equals(id)) {
                return station;
            }
        }
        return null; // When not found 
    }

    /**
     * Retrieves a {@link ChargingStation} by its {@link Location}.
     * @param location The {@link Location} of the station to find.
     * @return The {@link ChargingStation} at the matching location, or {@code null} if not found.
     */
    public ChargingStation getChargingStation(Location location)
    {
        for (ChargingStation station : stations) {
            if (station.getLocation().equals(location)) {
                return station;
            }
        }
        return null;
    }
    
    /**
     * @return An unmodifiable list of all managed {@link ChargingStation}s.
     */
    public List<ChargingStation> getCityStations()
    {
       return Collections.unmodifiableList(stations);
    }
    
    
    /**
     * @return The total number of managed {@link ChargingStation}s.
     */
    public int getNumberOfStations(){
        return this.stations.size();
    }
    /**
     * 
     */
    public void setSubscribedVehicles(List<ElectricVehicle> subsVehicles)
    {
        this.subscribedVehicles = subsVehicles;
    }
    /**
     * Clears all managed vehicles and stations, resetting the company to an empty state.
     */
    public void reset(){
        this.subscribedVehicles.clear();
        this.stations.clear();
    }
    /**
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EVCompany)) { 
            return false;
        }
        EVCompany other = (EVCompany) obj;
        
        if (this.name == null) {
            return other.name == null;
        }
        return this.name.equals(other.name);
    }

    /**
     * Generates a hash code for the company.
     * Based on the 'name' field, consistent with equals().
     * @return A hashcode for the company.
     */
    @Override
    public int hashCode()
    {
        int result = 7; 
        result = 3 * result + getName().hashCode();
        return result;
    }
}