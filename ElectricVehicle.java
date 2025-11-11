import java.util.List;
/**
 * Model the common elements of an Electric Vehicle (EV) that operates 
 * within the simulation, moving towards a target and potentially recharging.
 * * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2024.10.07
 */
public class ElectricVehicle 
{
    private String plate;
    private String name;
    private EVCompany company;
    private Location location;
    private Location targetLocation;
    private Location rechargingLocation;
    private int idleCount;
    private int batteryCapacity;
    private int batteryLevel;
    private int kwsCharged;
    private int chargesCount;
    private double chargestCost;

    /**
     * Constructor of class ElectricVehicle.
     * @param company The EV's operating company. Must not be null.
     * @param location The EV's starting {@link Location}. Must not be null.
     * @param targetLocation The EV's final destination {@link Location}. Must not be null.
     * @param name The name of the vehicle.
     * @param plate The license plate of the vehicle.
     * @param batteryCapacity The maximum capacity of the battery.
     * @throws NullPointerException If company, location, or targetLocation is null.
     */
    public ElectricVehicle(EVCompany company, Location location, Location targetLocation, String name, String plate, int batteryCapacity)
    {
        this.company=company;
        this.location=location;
        this.targetLocation=targetLocation;
        this.rechargingLocation=null;
        this.name=name;
        this.plate=plate;
        this.batteryCapacity=batteryCapacity;
        this.idleCount=0;
        this.batteryLevel=batteryCapacity; //suponemos que la bateria esta al maximo al crear el coche
        this.kwsCharged=0;
        this.chargesCount=0;
        this.chargestCost=0;
    }

    /*
     * getters
     */
    /**
     * Get the current location.
     * @return Where this vehicle is currently located.
     */
    private Location getLocation()
    {
        return this.location;
    }
    /**
     * Get the final target location.
     * @return Where this vehicle is ultimately headed.
     */
    public Location getTargetLocation()
    {
        return this.targetLocation;
    }
    /**
     * Get the final target location.
     * @return Where this vehicle is ultimately headed.
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * Get the final target location.
     * @return Where this vehicle is ultimately headed.
     */
    public String getPlate()
    {
        return this.plate;
    }
    /**
     * Get the final target location.
     * @return Where this vehicle is ultimately headed.
     */
    public int getBatteryCapacity()
    {
        return this.batteryCapacity;
    }
     /**
      * @return The number of simulation steps this vehicle has been idle.
      */
    public int getIdleCount()
    {
        return this.idleCount;
    }
     /**
      * @return The count of total recharges performed by this vehicle.
      */
    public int getChargesCount()
    {  
        return this.chargesCount;
    }
     /**
      * @return The count of total recharges performed by this vehicle.
      */
    public int getBatteryLevel()
    {  
        return this.batteryLevel;
    }
    /**
     * Get the temporary recharging location.
     * @return The {@link Location} of the next {@link ChargingStation} to visit, or null if no recharge is planned.
     */
    public Location getRechargingLocation()
    {
        return this.rechargingLocation;
    }
    public EVCompany getCompany(){
        return this.company;
    }

    /*
     * setters
     */
    /**
     * Set the current location.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    private void setLocation(Location location)
    {
        this.location=location;
    }
    /**
     * Set the required final target location.
     * @param location Where to go. Must not be null.
     * @throws NullPointerException If location is null.
     */
    private void setTargetLocation(Location location)
    {
        this.targetLocation=location;
    }
    /**
     * Set the required final target location.
     * @param location Where to go. Must not be null.
     * @throws NullPointerException If location is null.
     */
    private void setRechargingLocation(Location location)
    {
        this.rechargingLocation=location;
    }
    
    
    /**
     * Get the simulation step when the vehicle arrived at its final target location.
     * @return The arriving step.
     */
    public int getArrivingStep()
    {
        return this.idleCount;
    }
    
    /**
     * Calculates the optimal route for the vehicle. 
     * If there isn't enough battery to reach the target, it attempts to find an intermediate 
     * {@link ChargingStation} and sets it as the {@code rechargingLocation}.
     */
    public void calculateRoute()
    {
        if(!enoughBattery(distanceToTheTargetLocation())){
            calculateRechargingPosition();
        }
        else{
            setRechargingLocation(null);
        }
    }
    
    /**
     * Gets a string representation of the planned route, including the recharging stop if one exists.
     * @return A string showing the route: {@code currentLocation -> [rechargingLocation ->] targetLocation}.
     */
    public String getStringRoute()
    {
        //TODO: Complete this code
        return null;
    }
    

    /**
     * Checks if the current battery level is sufficient to cover a given distance.
     * @param distanceToTargetLocation The distance to check.
     * @return {@code true} if the battery level is enough, {@code false} otherwise.
     */
    public boolean enoughBattery(int distanceToTargetLocation)
    {
        boolean enough=false;
        if(distanceToTargetLocation*5<batteryLevel)
            enough=true;
        return enough;
    }
    
    
    /**
     * Determines the optimal intermediate {@link ChargingStation} to visit for recharging
     * if the vehicle cannot reach the final target directly.
     * Sets {@code rechargingLocation} to the chosen station's location.
     */
    public void calculateRechargingPosition()
    {
        //buscar la mejor estacion
        List<ChargingStations> stations=this.getCompany().getCityStations();
        Iterator<ChargingStations> it = stations.iterator();
        int betterDistance=0;
        Location betterStation=null;
        if(it.hasNext()){
            betterStation=it.next();
            betterDistance=this.getLocation().distance(betterLocation)+betterLocation.distance(this.getTargetLocation());
        }
        while(it.hasNext()){
            Location currentLocation=it.next();
            if(enoughBattery(currentLocation)){
                int distance=this.getLocation().distance(currentLocation)+currentLocation.distance(this.getTargetLocation());
                if(betterDistance>distance){
                    betterLocation=currentLocation;
                    betterDistance=distance;
                }
            }
        }
        setRechargingLocation(betterLocation);
    }
    
    
     /**
      * Checks if the vehicle has a planned recharging stop.
      * @return Whether or not this vehicle has a recharging location set.
      */
     public boolean hasRechargingLocation(){
        if(getRechargingLocation()==null)
            return false;
        return true;
     }

    
    
    
    /**
     * Increment the number of steps on which this vehicle has been idle.
     */
    public void incrementIdleCount()
    {
        this.idleCount++;
    }

    
     /**
      * Get the Manhattan-like distance to the final target location from the current location.
      * @return The distance to the target location.
      */
     public int distanceToTheTargetLocation()
     {
        return this.location.distance(this.targetLocation);
     }

     /**
      * Simulates the recharging process when the vehicle arrives at a {@code rechargingLocation}.
      * The battery is charged to full capacity, the cost is calculated, and the route is recalculated.
      * @param step The current step of the simulation.
      */
    public void recharge(int step)
    {
       ChargingStation station = company.getChargingStation(rechargingLocation);
       Charger cargadorLibre = null;
       
       Iterator<Charger> it = station.getChargers().iterator();
       while (it.hasNext() && cargadorLibre == null){
           Charger cargador = it.next();
           if(charger.getFree()){
               cargadorLibre = cargador; //Guardamos el primer cargador que se encuentre libre
           }
       }
       
       if (cargadorLibre != null){
           int kwhNeeded = getBatteryCapacity() - getBatteryLevel();
           
           double cost = cargadorLibre.recharge(kwhNeeded, this);
           
           setBatteryLevel(getBatteryCapacity()); //Ponemos la batería al máximo
           incrementCharges();
           incrementChargesCost(cost);
           
           setRechargingLocation(null);
           calculateRoute();
       }
    } 
    
    /**
     * Increments the count of recharges performed by this vehicle.
     */
    public void incrementCharges()
    {
         this.chargesCount++;
    }
    
    /**
     * Adds a cost amount to the total charges cost.
     * @param cost The cost of the last recharge.
     */
    public void incrementChargesCost(float cost)
    {
         this.chargestCost+=cost;
    }   
     
     /**
      * Carries out a single step of the vehicle's actions.
      * Moves one step towards the target (recharging or final) or stays idle.
      * @param step The current step of the simulation.
      */
     public void act(int step)
     {
        //TODO: Complete this code     
    }
     
    /**
     * Reduces the battery level by the cost of one movement step (defined in {@link EVCompany#MOVINGCOST}).
     * Ensures the battery level does not go below zero.
     */
    public void reduceBatteryLevel(){
        this.batteryLevel-=5;   
    }

    
    /**
     * Returns a detailed string representation of the electric vehicle.
     * @return A string containing the vehicle's name, plate, battery info, charge counts, costs, idle count, and route.
     */
    @Override
    public String toString(){ //hay que poner el de la recarga aqui
        String texto= "(ElectricVehicle: "+this.getName()+", "+this.getPlate()+", "+this.getBatteryCapacity()+"kwh, "+this.getBatteryLevel()+"kws, "+this.getChargesCount()+", "+this.getChargestCost+"€, "+this.getIdleCount()+", "+this.getLocation().toString()+", ";
        if(){
            
        
        }this.getTargetLocation().toString()+", "+this.getTargetLocation().toString()+")\n";
    }

    /**
     * Generates a string containing the vehicle's details prefixed with the current step number.
     * @param step The current simulation step.
     * @return A formatted string for a step log.
     */
    public String getStepInfo(int step){
         //TODO: Complete this code
         return null;
    }
    
    /**
     * Generates a string of the vehicle's initial or final status for summary display.
     * @return The output of {@link #toString()} wrapped in parentheses.
     */
    public String getInitialFinalInfo(){
         //TODO: Complete this code
         return null;
    }
}