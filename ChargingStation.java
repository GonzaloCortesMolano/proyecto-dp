import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Models an Electric Vehicle Charging Station.
 * A charging station contains multiple {@link Charger} units.
 * @author DP classes 
 * @version 2024.10.07
 */
public class ChargingStation
{
    private String id;
    private String city;
    private Location locationE;
    private List<Charger> chargers;
    /**
     * Constructor for objects of class ChargingStation.
     * @param city The city where the station is located.
     * @param id The unique identifier of the station.
     * @param location The {@link Location} of the station.
     */
    public ChargingStation(String city, String id, Location location)
    {
       this.id=id;
       this.city=city;
       this.locationE=location;
       chargers=new ArrayList<Charger>();
    }
    
    /*
     * getters
     */
    /**
     * @return The unique identifier of the charging station.
     */
    public String getId()
    {
        return this.id;
    }
    /**
     * @return The city of the charging station.
     */
    public String getCity(){
        return this.city;
    }
    /**
     * @return The {@link Location} of the charging station.
     */
    public Location getLocation()
    {
        return this.locationE;
    }
    /**
     * @return An unmodifiable list of all {@link Charger}s at the station.
     */
    public List<Charger> getChargers()
    {
        return chargers; //meted lo de no modificable
    }
    
    /*
     * setters
     */
    /**
     * Set the current location of the charging station.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location){
        this.locationE=location;
    }
    
    /**
     * Retrieves the first free {@link Charger} found at the station.
     * @return A free {@link Charger}, or possibly throws an exception if none are found (depending on stream implementation details).
     * **Note: The implementation assumes at least one free charger exists if called.**
     */
    public Charger getFreeCharger()
    {
        Charger c=null;
        for(int i=0; c==null && i<chargers.size();i++){
            if(chargers.get(i).getFree()){
                c=chargers.get(i);
            }
        }  
        return c;
    }
    
    /**
     * Calculates the total number of {@link ElectricVehicle}s recharged across all {@link Charger}s at this station.
     * @return The total number of unique recharges.
     */
    public int getNumberEVRecharged(){
        int total=0;
        for(Charger c: chargers){
            total+=c.getNumberEVRecharged();
        }
        return total;
    }


    /**
     * Returns a string containing complete information about the charging station, 
     * including details of all its {@link Charger}s and their usage history.
     * @return A comprehensive string representation of the station.
     */
    public String getCompleteInfo()
    {
        String texto=this.toString();
        for(Charger c: chargers){
            texto+=c.getCompleteInfo();
        }
        return texto;
    }
    

    /**
     * Shows a final information summary about the charging station (currently the same as {@code toString()}).
     * @return A string representation of the station's final status.
     * @deprecated Consider using {@link #toString()} or {@link #getCompleteInfo()} instead.
     */
    public String showFinalInfo()
    {
        return toString();
    }

    /**
     * @return A string representation of the charging station, including its ID, city, total number of EVs recharged, and location.
     */
    @Override
    public String toString()
    {
        return "(ChargingStation: " + getId() + ", " + getCity() +", " + getNumberEVRecharged() + ", " + getLocation().toString() + ")\n";
    }
    
    /**
     * Adds a new {@link Charger} to the station.
     * @param charger The new charger unit.
     */
    public void addCharger(Charger charger)
    {
        this.chargers.add(charger);
    }
    
}