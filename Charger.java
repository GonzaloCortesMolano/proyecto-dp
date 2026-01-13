import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Represents a charging unit within a {@link ChargingStation}.
 * A {@code Charger} models an electric vehicle charger, keeping track of its 
 * maximum charging speed, fee per kWh, total amount of money collected, and 
 * the electric vehicles it has recharged. It also maintains its availability 
 * status (whether it is free or currently in use).
 *
 * @author: Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano 
 * @version 12-11-2025
 */
public abstract class Charger
{
    private String id; 
    private int chargingSpeed;
    private double chargingFee;
    private List<ElectricVehicle> eVsRecharged;
    private double amountCollected;
    private boolean free;

    protected List<Enum> types;
    
    /**
     * Constructs a new {@code Charger}.
     * 
     * @param id The unique identifier of the charger.
     * @param speed The maximum charging speed in kWh.
     * @param fee The cost per kWh for charging.
     */
    public Charger(String id, int speed, double fee)
    {   try{
            if(id == null || id.isEmpty()){
                throw new NullPointerException("Charger need a valid Id (Not null or empty)");
            }
            if(speed <= 0){
                throw new IllegalArgumentException("Charging speed of Charger must be positive");
            }
            if(fee <= 0) {
                throw new IllegalArgumentException("Charging fee of Charger must be positive");
            }        
            this.id=id;
            this.chargingSpeed=speed;
            this.chargingFee = fee;
            this.eVsRecharged = new ArrayList<ElectricVehicle>();
            this.amountCollected = 0;
            this.free = true;
            types=new ArrayList<Enum>();
        } catch(RuntimeException e){
            System.err.println("Error in the creation of the Charger: " + e.getMessage());
        }
    }

    // -------------------------------------------------
    // -------------------- Getters --------------------
    // -------------------------------------------------
    
    /**
     * @return The unique identifier of the charger.
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * @return The maximum charging speed of the charger (in kWh).
     */
    public int getChargingSpeed(){
        return this.chargingSpeed;
    }
    
    /**
     * @return The charging fee (cost per kWh).
     */
    public double getChargingFee(){
        return this.chargingFee;
    }
    
    /**
     * @return An unmodifiable list of electric vehicles that have been recharged by this charger.
     */
    public List<ElectricVehicle> getEVsRecharged(){
        return Collections.unmodifiableList(this.eVsRecharged);
    }
    
    /**
     * @return The total amount of money collected by this charger.
     */
    public double getAmountCollected(){
        return this.amountCollected;
    }
    
    /**
     * @return {@code true} if the charger is available for use, {@code false} otherwise.
     */
    public boolean getFree(){
        return this.free;
    }
    
    /**
     * @return The total number of {@link ElectricVehicle}s that have been recharged by this charger.
     */
    public int getNumberEVRecharged(){
        return eVsRecharged.size();
    }
    
    // -------------------------------------------------
    // -------------------- Setters --------------------
    // -------------------------------------------------
    
    /**
     * Sets the charger ID.
     * 
     * @param id The new ID for the charger.
     */
    public void setId(String id){
        this.id=id;
    }
    
    /**
     * Sets the charging speed.
     * 
     * @param speed The new charging speed (in kWh).
     */
    public void setChargingSpeed(int speed){
        this.chargingSpeed=speed;
    }
    
    /**
     * Sets the charging fee.
     * 
     * @param fee The new fee per kWh.
     */
    public void setChargingFee(double fee){
        this.chargingFee = fee;
    }
    
    /**
     * Sets the total amount of money collected by this charger.
     * 
     * @param amount The new total amount collected.
     */
    public void setAmountCollected(double amount){
        this.amountCollected = amount;
    }
    
    /**
     * Updates the availability status of the charger.
     * 
     * @param free {@code true} if the charger is available, {@code false} otherwise.
     */
    public void setFree(boolean free){
        this.free=free;
    }
    
    //No hacemos setEVsRecharged porque rompe la encapsulación y no hace falta,
    //ya que tenemos el método addEvRecharged
    
    public void addType(Enum type) {
        if (!this.types.contains(type)) {
            this.types.add(type);
        }
    }
    
    // ------------------------------------------------
    // -------------------- Others --------------------
    // ------------------------------------------------
    /**
     * Returns a string representation of the charger, including its ID, speed, fee, 
     * number of recharged vehicles, and total amount collected.
     * 
     * @return A formatted string representing this charger.
     */
    @Override
    public String toString()
    {
        return "(" + this.getClass().getSimpleName() + ": "+getId()+", "+getChargingSpeed()+"kwh, "+String.format(java.util.Locale.US,"%.1f", getChargingFee())+"€, "+getNumberEVRecharged()+", "+String.format(java.util.Locale.US,"%.2f", getAmountCollected())+"€)";
    }

    /**
     * Returns detailed information about the charger, including data for all 
     * {@link ElectricVehicle}s it has recharged.
     * 
     * @return A detailed string representation of this charger and its usage history.
     */
    public String getCompleteInfo()
    {
         String texto = this.toString();
         for(ElectricVehicle v: eVsRecharged){
            texto +="\n"+ v.toString();
         }
         texto+="\n";
         return texto;
    }
    
    
    /**
     * Adds an {@link ElectricVehicle} to the list of vehicles that have been recharged.
     * 
     * @param vehicle The electric vehicle that was recharged.
     */
    public void addEvRecharged(ElectricVehicle vehicle){
        eVsRecharged.add(vehicle);
    }
    
    /**
     * Simulates the charging process for an {@link ElectricVehicle}.
     * This method marks the charger as busy, calculates the fee based on the 
     * number of kWh recharged, updates the total amount collected, and registers 
     * the vehicle as recharged.
     * 
     * @param vehicle The electric vehicle to recharge.
     * @param kwsRecharging The number of kWh to recharge.
     * @return The total cost of the recharge operation.
     */
    public final double recharge(ElectricVehicle vehicle,int kwsRecharging){
        
        if (!canCharge(vehicle)) {
            return 0.0;
        }

        setFree(false);
        
        // Delegamos en las subclases el cálculo específico del precio
        // Template Method
        double fee=calculateFee(kwsRecharging);
        
        updateAmountCollected(fee);
        addEvRecharged(vehicle);
        
        setFree(true); //el cargador se termina de utilizar, lo ponemos inmediatamente despues porque la carga es inmediata 
                       //y no coinciden dos coches en el mismo cargador
        return fee;
    }
    
    /**
     * MÉTODO GANCHO (Hook Method) o Primitiva.
     * Implementación por defecto del cálculo del coste.
     * Las subclases (Solar, UltraFast) sobrescribirán esto.
     */
    protected double calculateFee(int kwsRecharging) {
        return kwsRecharging * getChargingFee();
    }
    
    /**
     * Updates the total amount collected by adding the specified amount.
     * 
     * @param money The additional amount to add to the total collected.
     */
    public void updateAmountCollected(double money){
        this.amountCollected+=money;
    }
    
    /**
     * Compares this charger to another object for equality.
     * Two chargers are considered equal if they share the same unique ID.
     * 
     * @param obj The object to compare.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true; 
        }
        if(!(obj instanceof Charger)) {
            return false; 
        }
        Charger other = (Charger) obj;
        return this.id.equals(other.id); //añadir mas
    }

    /**
     * Generates a hash code for this charger, based on its ID.
     * 
     * @return The hash code value.
     */
    @Override
    public int hashCode()
    {
        int result = 7; 
        result = 3 * result + getId().hashCode(); 
        return result;
    }
    
    /**
     * TODO devuelve si el vehiculo puede cargar ahi
     */
    public boolean canCharge(ElectricVehicle vehicle){
        if (vehicle == null) {
            return false; 
        }
        
        if (this.types.isEmpty()) {
            return true;
        }
        
        return compareType(vehicle.getType()); 
    }
    //mira si el tipo introducido es compatible con el mismo
    public boolean compareType(Enum type){
        return types.contains(type);
    }
}