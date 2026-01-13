import java.util.*;

/**
 * Represents an Electric Vehicle (EV) company that manages a fleet of
 * {@link ElectricVehicle}s and a network of {@link ChargingStation}s.
 * The {@code EVCompany} class provides operations to register electric vehicles,
 * manage charging stations, and retrieve them by ID or {@link Location}.
 * It also supports resetting and replacing the subscribed vehicle list.
 *
 * This class follows the Singleton design pattern.
 *
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 13-1-2026
 */
public class EVCompany  
{
    /**
     * The name of the EV company.
     */
    private String name;
    /**
     * The set of {@link ElectricVehicle}s subscribed to this company.
     */
    private Set<ElectricVehicle> subscribedVehicles;
    /**
     * The set of {@link ChargingStation}s managed by this company.
     */
    private Set<ChargingStation> stations;
    
    /**
     * Registry of charging operations.
     * Maps each {@link Charger} to the list of {@link ElectricVehicle}s
     * that have been recharged using it.
     */
    private Map<Charger, List<ElectricVehicle>> chargesRegistry;

    /**
     * Singleton instance of the company.
     */
    private static EVCompany instance; 
    
    /**
     * Constructs a new {@code EVCompany} with the specified name.
     * This constructor is private to enforce the Singleton pattern.
     *
     * @param name The name of the company. Must not be {@code null} or empty.
     * @throws IllegalArgumentException if the name is {@code null} or empty.
     */
    private EVCompany(String name)
    {
        try{
            if(name == null || name.isEmpty()){
                throw new NullPointerException("The company need a name");
            }
            this.name = name; 
            this.subscribedVehicles = new TreeSet<>(new ComparatorElectricVehicleIdleCount()); 
            this.stations = new TreeSet<>(new ComparatorChargingStationNumberRecharged());
            this.chargesRegistry = new TreeMap<>((c1, c2) -> c1.getId().compareTo(c2.getId())); //mapa para guardar los registros de las cargas de cada vehículo
        } catch(IllegalArgumentException e){
            System.err.println("Error in the creation of the company: " + e.getMessage());
        }
    }
    
    /**
     * Returns the unique instance of the {@code EVCompany}.
     * If the instance does not exist, it is created with a default name.
     *
     * @return The singleton instance of {@code EVCompany}.
     */
    public static EVCompany getInstance() { //SINGLETON
        if (instance == null) {
            instance = new EVCompany("Compañía EVCharging Cáceres");
        }
        return instance;
    }
    
    /**
     * Resets the singleton instance.
     * This method is mainly intended for testing purposes.
     */
    public static void resetInstance() {
        instance = null;
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
     * Returns an unmodifiable view of the subscribed electric vehicles.
     *
     * @return An unmodifiable set of {@link ElectricVehicle}s.
     */
    public Set<ElectricVehicle> getVehicles()
    {       
        return Collections.unmodifiableSet(subscribedVehicles);
    }
    
    // ESTE NO SE USA PERO VENIA INCLUIDO YA EN LA PLANTILLA
    /**
     * Retrieves a {@link ChargingStation} by its unique identifier.
     *
     * @param id The identifier of the charging station.
     * @return The matching {@link ChargingStation}, or {@code null} if not found.
     */
    public ChargingStation getChargingStation(String id)
    {
        ChargingStation initialStation = null;
        
        Iterator<ChargingStation> it = stations.iterator();
        while (it.hasNext() && initialStation == null) {
            ChargingStation station = it.next();
            if (station.getId().equals(id)) {
                initialStation = station;
            }
        }
         
        return initialStation;
    }
    
    /**
     * Retrieves a {@link ChargingStation} located at the specified {@link Location}.
     *
     * @param location The {@link Location} of the station to find.
     * @return The {@link ChargingStation} at that location, or {@code null} if not found.
     */
    public ChargingStation getChargingStation(Location location)
    {
        ChargingStation initialStation = null;
        
        Iterator<ChargingStation> it = stations.iterator();
        while (it.hasNext() && initialStation == null) {
            ChargingStation station = it.next();
            if (station.getLocation().equals(location)) {
                initialStation = station;
            }
        }
        return initialStation;
    }
    
    /**
     * @return An unmodifiable list of all managed {@link ChargingStation}s.
     */
    public Set<ChargingStation> getCityStations()
    {
       return Collections.unmodifiableSet(stations);
    }
    
    /**
     * @return The total number of {@link ChargingStation}s managed by the company.
     */
    public int getNumberOfStations(){
        return this.stations.size();
    }
    
    /**
     * Returns an unmodifiable view of the charging registry.
     *
     * @return A map that associates each {@link Charger} with the list of
     *         {@link ElectricVehicle}s recharged using it.
     */
    public Map<Charger, List<ElectricVehicle>> getChargesRegistry() {
        return Collections.unmodifiableMap(chargesRegistry);
    }
    
    
    // -------------------------------------------------
    // -------------------- Setters --------------------
    // -------------------------------------------------
    
    /**
     * Replaces the set of subscribed {@link ElectricVehicle}s.
     *
     * @param subsVehicles The new set of vehicles.
     *                     If {@code null}, the operation is ignored.
     */
    public void setSubscribedVehicles(Set<ElectricVehicle> subsVehicles)
    {
        if(subsVehicles != null)
            this.subscribedVehicles = subsVehicles;
    }
    
    /**
     * Replaces the set of managed {@link ChargingStation}s.
     *
     * @param sStations The new set of charging stations.
     *                  If {@code null}, the operation is ignored.
     */
    public void setChargingStations(Set<ChargingStation> sStations)
    {
        if(sStations != null)
            this.stations = sStations;
    }
    
    // ------------------------------------------------
    // -------------------- Others --------------------
    // ------------------------------------------------
    
    /**
     * Adds a new {@link ElectricVehicle} to the subscribed fleet.
     *
     * @param vehicle The electric vehicle to add.
     */
    public void addElectricVehicle(ElectricVehicle vehicle)
    {       
        if (vehicle != null) {
            this.subscribedVehicles.add(vehicle);
        }
    }

    /**
     * Adds a new {@link ChargingStation} to the managed network.
     *
     * @param station The charging station to add.
     */
    public void addChargingStation(ChargingStation station)
    {       
        if (station != null) {
            this.stations.add(station);
        }    
    }
    
    /**
     * Removes all subscribed vehicles, charging stations and charging records,
     * leaving the company in an empty state.
     */
    public void reset(){
        this.subscribedVehicles.clear();
        this.stations.clear();
        this.chargesRegistry.clear(); //nuevo
    }
    
    
    /**
     * Registers a charging operation performed by a vehicle using a specific charger.
     * Each vehicle is recorded only once per charger.
     *
     * @param charger The charger used for the recharge.
     * @param vehicle The electric vehicle that was recharged.
     */
    public void registerRecharge(Charger charger, ElectricVehicle vehicle)
    {
        if (charger != null && vehicle != null) {
                
        List<ElectricVehicle> list = chargesRegistry.get(charger);
        
        if (list == null) {
            list = new ArrayList<>();
            chargesRegistry.put(charger, list);
        }
        
        if (!list.contains(vehicle)) {
            list.add(vehicle);
        }
    }
    }
    
    
    /**
     * Compares this company to another object for equality.
     * Two {@code EVCompany} instances are considered equal
     * if they have the same company name.
     *
     * @param obj The object to compare with.
     * @return {@code true} if both objects represent the same company;
     *         {@code false} otherwise.
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
     * consistent with {@link #equals(Object)}.
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