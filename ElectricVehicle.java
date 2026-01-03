import java.util.*;
/**
 * Model the common elements of an Electric Vehicle (EV) that operates 
 * within the simulation, moving towards a target and potentially recharging.
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano  
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
    private double chargesCost;
    
    protected Enum type;

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
        this.batteryLevel=batteryCapacity; //battery level is maxed
        this.kwsCharged=0;
        this.chargesCount=0;
        this.chargesCost=0;
        this.type = null;
    }

    // -------------------------------------------------
    // -------------------- Getters --------------------
    // -------------------------------------------------
    
    /**
     * Get the current location.
     * @return Where this vehicle is currently located.
     */
    public Location getLocation()
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
     * Get the name of the vehicle.
     * @return The vehicle's name.
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Get the license plate of the vehicle.
     * @return The vehicle's unique license plate.
     */
    public String getPlate()
    {
        return this.plate;
    }
    
    /**
     * Get the maximum battery capacity.
     * @return The battery capacity in kWh.
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
      * @return The current battery level in kWh.
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
    
    /**
     * Gets the company this vehicle is subscribed to.
     * @return The {@link EVCompany} instance.
     */
    public EVCompany getCompany(){
        return this.company;
    }
    
    /**
     * Gets the total kwh charged by this vehicle over its lifetime.
     * @return The total kwh charged.
     */
    public int getKwsCharged(){
        return this.kwsCharged;
    }
    
    /**
     * Gets the total cost of all recharges.
     * @return The total cost in euros.
     */
    public double getChargesCost(){
        return this.chargesCost;
    }
    
    /*TODO*/
    public Enum getType(){
        return this.type;
    }

    // -------------------------------------------------
    // -------------------- Setters --------------------
    // -------------------------------------------------
    
    /**
     * Set the current location.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    /**
     * Set the required final target location.
     * @param location Where to go. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setTargetLocation(Location location)
    {
        this.targetLocation = location;
    }
    
    /**
     * Set the intermediate recharging location.
     * @param location The {@link Location} of the station to visit, or {@code null} to clear it.
     */
    public void setRechargingLocation(Location location)
    {
        this.rechargingLocation = location;
    }
    
    /**
     * Set the current battery level.
     * @param level The new battery level in kWh.
     */
    public void setBatteryLevel(int level)
    {
        this.batteryLevel = level;
    }
    
    // ------------------------------------------------
    // -------------------- Others --------------------
    // ------------------------------------------------
    
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
    public void incrementChargesCost(double cost)
    {
         this.chargesCost+=cost;
    } 
    
    /**
     * Increment the number of steps on which this vehicle has been idle.
     */
    public void incrementIdleCount()
    {
        this.idleCount++;
    }
    
    /**
     * Reduces the battery level by the cost of one movement step (defined in {@link EVCompany#MOVINGCOST}).
     * Ensures the battery level does not go below zero.
     */
    public void reduceBatteryLevel(){
        this.batteryLevel-=5;  
        if (this.batteryLevel < 0){
            this.batteryLevel = 0; //No negative battery level
        }
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
     * Checks if the current battery level is sufficient to cover a given distance.
     * @param distanceToTargetLocation The distance to check.
     * @return {@code true} if the battery level is enough, {@code false} otherwise.
     */
    public boolean enoughBattery(int distanceToTargetLocation)
    {
        boolean enough=false;
        if(distanceToTargetLocation*5 <= batteryLevel)
            enough=true;
        return enough;
    }
    
    /**
     * Determines the optimal intermediate {@link ChargingStation} to visit for recharging
     * if the vehicle cannot reach the final target directly.
     * Sets {@code rechargingLocation} to the chosen station's location or null if doesn't exists one.
     */
    public void calculateRechargingPosition()
    {
        Set<ChargingStation> stations = this.getCompany().getCityStations();
        Iterator<ChargingStation> it = stations.iterator();
        int betterDistance = 999;
        Location betterStation = null;

        while (it.hasNext()) {
            ChargingStation currentStation = it.next();
            Location currentLocation = currentStation.getLocation();
            int distToStation = this.getLocation().distance(currentLocation);
            if (requirements(distToStation, currentLocation)) { //Si cumplo los requisitos
                int distance = distToStation + currentLocation.distance(this.getTargetLocation());
                if (distance < betterDistance) { // Si es la actual la ruta más corta:
                    betterStation = currentLocation;
                    betterDistance = distance;
                }
            }
            setRechargingLocation(betterStation); // Si no se encuentra ninguna, se asigna null
        }
    } 
    //requisitos necesarios para tomar la estacion como correcta
    boolean requirements(int distToStation, Location currentLocation){
        if(enoughBattery(distToStation) && !getLocation().equals(currentLocation)){
            return true;
        }
        return false;
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
     
     //devuelve si puede llegar a la estacion, o false si ni siquiera tiene
     public boolean canArriveStation(){
        return hasRechargingLocation() && enoughBattery(getLocation().distance(getRechargingLocation()));    
    }
    
    public boolean canArriveTarget(){
        return enoughBattery(distanceToTheTargetLocation());
    }
    //devuelve si esta en el objetivo
    public boolean isInTarget(){
        return location.equals(targetLocation);
    }
    
    public boolean isInStation(){
        return hasRechargingLocation() && location.equals(rechargingLocation);
    }
    // devuelve si acaba de recargar
    public boolean hasRecharged(){
        return getBatteryLevel()==getBatteryCapacity();
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

       Charger freeCharger = getFreeChargerFromStation();
       
       if (freeCharger != null){
           load(step, freeCharger);
       }
    } 
    //proceso de carga
    public void load(int step, Charger freeCharger){
        double cost = freeCharger.recharge(this, getBatteryCapacity() - getBatteryLevel());
           
           setBatteryLevel(getBatteryCapacity()); //Ponemos la batería al máximo
           incrementCharges();
           incrementChargesCost(cost);
           
           notifyCompany(freeCharger); //NOTIFICAMOS A EVCOMPANY (NUEVO)
           
           setRechargingLocation(null);
           calculateRoute();
    }
    
    
    /**
     * Notifica a la compañía que se ha realizado una carga.
     */
    protected void notifyCompany(Charger charger) { //este método lo llaman las compañías que necesiten 
                                                    //notificar; PriorityEV, no.
        if (getCompany() != null) {
            getCompany().registerRecharge(charger, this);
        }
    }
    
    
    //coge un cargador de su tipo
    public Charger getFreeChargerFromStation(){
        if(type==null){
            return company.getChargingStation(rechargingLocation).getFreeCharger();
        }
        else{
            return company.getChargingStation(rechargingLocation).getFreeCharger(this.type);
        }
    }
    
    /**
     * Generates a string containing the vehicle's details prefixed with the current step number.
     * @param step The current simulation step.
     * @return A formatted string for a step log.
     */
    public String getStepInfo(int step){
        String info = this.toString();
        
        return "(step: " + step + " - " + info.substring(1); // quie hay un \n
    }
    //muestra su tipo en modo string
    public String getTypeInfo(){
        return "ElectricVehicle: ";
    }
    //muestra el texto de llegada
    public String getArrivalInfo(int step){
        return "(step: " + step + " - "+ getTypeInfo() + this.getPlate() + " at target destination ********)";
    }
    //muestra el texto al llegar a una estacion
    public String getChargingInfo(int step){
        ChargingStation station = getCompany().getChargingStation(getRechargingLocation());
        Charger charger=station.getFreeCharger();
        return "(step: "+step+" - "+getTypeInfo()+getPlate()+ " recharges: "+(getBatteryCapacity() - getBatteryLevel())+"kwh at charger: "+charger.getId()+" with cost: "
        +String.format(java.util.Locale.US, "%.1f", charger.getChargingFee()*(getBatteryCapacity() - getBatteryLevel()))+"€ ********)";
    }
    
    /**
     * Gets a string representation of the planned route, including the recharging stop if one exists.
     * @return A string showing the route: {@code currentLocation -> [rechargingLocation ->] targetLocation}.
     */
    public String getStringRoute()
    {
        String route = location.toString();
        if(hasRechargingLocation()){
            route = route + " -> " + rechargingLocation.toString();
        }
        route = route + " -> " + targetLocation.toString();
        return route;
    }
    
    /**
     * Returns a detailed string representation of the electric vehicle.
     * @return A string containing the vehicle's name, plate, battery info, charge counts, costs, idle count, and route.
     */
    @Override
    public String toString(){
        String route = this.location.toString();
        
        if (hasRechargingLocation()){
            route = route + ", " + this.rechargingLocation.toString();
        }
        
        route += ", " + this.targetLocation.toString();
        
        return "("+ getTypeInfo() + this.name + ", " + 
               this.plate + ", " + this.batteryCapacity + "kwh, " + 
               this.batteryLevel + "kwh, " + this.chargesCount + ", " + 
               String.format(java.util.Locale.US, "%.1f", this.chargesCost) + "€, " + 
               this.idleCount + ", " + route + ")";
    }
    
    /**
     * Generates a string of the vehicle's initial or final status for summary display.
     * @return The output of {@link #toString()} wrapped in parentheses.
     */
    public String getInitialFinalInfo(){
         return this.toString();
    }
    
    /**
      * Carries out a single step of the vehicle's actions.
      * Moves one step towards the target (recharging or final) or stays idle.
      * @param step The current step of the simulation.
      */
     public void act(int step)
     {   
        boolean requirement = (canArriveStation() || canArriveTarget());
         if(requirement){
             if(isInTarget()) { //estamos en el destino, contamos tiempo parado
               incrementIdleCount();
             }
             else { //nos movemos 
                move(step); 
            }
        }
            else{
                if (!hasRechargingLocation()) {
                calculateRechargingPosition();
                }
                incrementIdleCount();
            }
             //Añadir info del paso (step)
             System.out.println(getStepInfo(step));
         
    }
    
    /*
     * Refactorizamos para permitir que las subclases reutilicen la lógica de movimiento
     */
    protected void move(int step){
            Location destination;
    
                if (hasRechargingLocation()) {
                    destination = rechargingLocation;
                }   else {
                    destination = targetLocation;
                }
                
            setLocation(location.nextLocation(destination));
            if(isInTarget()) { //si llega a la estacion muestra mensaje
                 System.out.println(getArrivalInfo(step));
            }
            reduceBatteryLevel();
                 
            //si llega a una estacion recarga
            if(isInStation()) {
               System.out.println(getChargingInfo(step));
               recharge(step); 
            }    
    }
    
    /**
     * Compares this vehicle to another object for equality.
     * Two vehicles are considered equal if they have the same license plate.
     *
     * @param obj The object to compare with.
     * @return {@code true} if both objects represent the same vehicle; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true; 
        }
        if(!(obj instanceof ElectricVehicle)) {
            return false;
        }
        ElectricVehicle other = (ElectricVehicle) obj;
        return this.plate.equals(other.plate); //Añadir algun campo mas
    }

    /**
     * Generates a hash code for this vehicle.
     * The hash code is derived from the vehicle's plate,
     * consistent with the {@link #equals(Object)} method.
     *
     * @return The hash code of this vehicle.
     */
    @Override
    public int hashCode()
    {
        int result = 7; 
        result = 3 * result + getPlate().hashCode(); //Añadir el cmapo que añadamos arriba
        return result;
    }
}
