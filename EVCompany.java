import java.util.*;

/**
 * Represents an Electric Vehicle (EV) company that manages a fleet of 
 * {@link ElectricVehicle}s and a network of {@link ChargingStation}s.
 * The {@code EVCompany} class provides operations to register electric vehicles, 
 * manage charging stations, and retrieve them by ID or {@link Location}. 
 * It also supports resetting and replacing the subscribed vehicle list.
 * 
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 */
public class EVCompany  
{
    /**
     * The name of the EV company.
     */
    private String name;
    /**
     * The list of {@link ElectricVehicle}s subscribed to this company.
     */
    private List<ElectricVehicle> subscribedVehicles;
    /**
     * The list of {@link ChargingStation}s managed by this company.
     */
    private List<ChargingStation> stations;
    
    /**
     * Constructs a new {@code EVCompany} with the specified name.
     * 
     * @param name The name of the company.
     */
    public EVCompany(String name)
    {
        this.name = name; 
        this.subscribedVehicles = new ArrayList<>(); 
        this.stations = new ArrayList<>();
    }

    // -------------------------------------------------
    // -------------------- Getters --------------------
    // -------------------------------------------------
    
    /**
     * @return The name of the company.
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * @return An unmodifiable list of all {@link ElectricVehicle}s subscribed to the company.
     */
    public List<ElectricVehicle> getVehicles()
    {       
        return Collections.unmodifiableList(subscribedVehicles);
    }
    
    // ESTE NO SE USA PERO VENIA INCLUIDO YA EN LA PLANTILLA
    /**
     * Retrieves a {@link ChargingStation} by its unique identifier.
     * 
     * @param id The ID of the charging station to find.
     * @return The matching {@link ChargingStation}, or {@code null} if no station matches the given ID.
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
     * Retrieves a {@link ChargingStation} located at the specified {@link Location}.
     * 
     * @param location The {@link Location} of the station to find.
     * @return The {@link ChargingStation} at that location, or {@code null} if not found.
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
     * @return The total number of {@link ChargingStation}s managed by the company.
     */
    public int getNumberOfStations(){
        return this.stations.size();
    }
    
    // -------------------------------------------------
    // -------------------- Setters --------------------
    // -------------------------------------------------
    
    /**
     * Replaces the list of subscribed {@link ElectricVehicle}s with a new list.
     * This method overwrites the current list of vehicles. 
     * Use with caution, as it can remove existing vehicle registrations.
     * 
     * @param subsVehicles The new list of {@link ElectricVehicle}s to assign.
     *      If {@code null}, the operation is ignored.
     */
    public void setSubscribedVehicles(List<ElectricVehicle> subsVehicles)
    {
        if(subsVehicles != null)
            this.subscribedVehicles = subsVehicles;
    }
    
    /**
     * Replaces the list of managed {@link ChargingStation}s with a new list.
     * This method overwrites the current collection of charging stations. 
     * Use with caution, as it will remove any previously managed stations.
     * 
     * @param lStations The new list of {@link ChargingStation}s to assign. 
     *      If {@code null}, the operation is ignored.
     */
    public void setChargingStations(List<ChargingStation> lStations)
    {
        if(lStations != null)
            this.stations = lStations;
    }
    
    // ------------------------------------------------
    // -------------------- Others --------------------
    // ------------------------------------------------
    
    /**
     * Adds a new {@link ElectricVehicle} to the company's subscribed fleet.
     * 
     * @param vehicle The electric vehicle to add. 
     *      If {@code null}, the method does nothing.
     */
    public void addElectricVehicle(ElectricVehicle vehicle)
    {       
        if (vehicle != null) {
            this.subscribedVehicles.add(vehicle);
        }
    }

    /**
     * Adds a new {@link ChargingStation} to the company's managed network.
     * 
     * @param station The charging station to add. 
     *      If {@code null}, the method does nothing.
     */
    public void addChargingStation(ChargingStation station)
    {       
        if (station != null) {
            this.stations.add(station);
        }    
    }
    
    /**
     * Removes all subscribed vehicles and managed stations, 
     * resetting the company to an empty state.
     */
    public void reset(){
        this.subscribedVehicles.clear();
        this.stations.clear();
    }
    
    /**
     * Compares this company to another object for equality.
     * Two {@code EVCompany} instances are considered equal 
     * if they have the same company name.
     * 
     * @param obj The object to compare with.
     * @return {@code true} if both objects represent the same company; {@code false} otherwise.
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
     * Generates a hash code for this company.
     * The hash code is derived from the company name, 
     * consistent with the {@link #equals(Object)} method.
     * 
     * @return The hash code of this company.
     */
    @Override
    public int hashCode()
    {
        int result = 7; 
        result = 3 * result + getName().hashCode();
        return result;
    }
}