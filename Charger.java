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
 * @author: Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez 
 * @version 12-11-2025
 */
public class Charger
{
    private String id; 
    private int chargingSpeed;
    private double chargingFee;
    private List<ElectricVehicle> eVsRecharged;
    private double amountCollected;
    private boolean free;

    /**
     * Constructs a new {@code Charger}.
     * 
     * @param id The unique identifier of the charger.
     * @param speed The maximum charging speed in kWh.
     * @param fee The cost per kWh for charging.
     */
    public Charger(String id, int speed, double fee)
    {
        this.id=id;
        this.chargingSpeed=speed;
        this.chargingFee = fee;
        this.eVsRecharged = new ArrayList<ElectricVehicle>();
        this.amountCollected = 0;
        this.free = true;
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
    
    /*
     * Set the list new of charged {@link ElectricVehicles}
     * @param v New charger's list of {@link ElectricVehicles}
     */
    /*
    public void setEVsRecharged(ArrayList<ElectricVehicle> v){
        this.eVsRecharged = v;
    }
    */ //Rompe la encapsulación y no hace falta ya que tenemos el método addEvRecharged
    
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
    
    /**
     * Returns a string representation of the charger, including its ID, speed, fee, 
     * number of recharged vehicles, and total amount collected.
     * 
     * @return A formatted string representing this charger.
     */
    @Override
    public String toString()
    {
        return "(Charger: "+getId()+", "+getChargingSpeed()+"kwh, "+String.format(java.util.Locale.US,"%.1f", getChargingFee())+"€, "+getNumberEVRecharged()+", "+getAmountCollected()+"€)";
    }

    /**
     * Returns detailed information about the charger, including data for all 
     * {@link ElectricVehicle}s it has recharged.
     * 
     * @return A detailed string representation of this charger and its usage history.
     */
    public String getCompleteInfo()
    {
         String texto = this.toString(); //n
         for(ElectricVehicle v: eVsRecharged){
            texto +="\n"+ v.toString();
         }
         texto+="\n";
         return texto;
    }
    
    /**
     * @return The total number of {@link ElectricVehicle}s that have been recharged by this charger.
     */
    public int getNumberEVRecharged(){
        return eVsRecharged.size();
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
    public double recharge(ElectricVehicle vehicle,int kwsRecharging){
        setFree(false); //el cargador está siendo utilizado
        
        double fee=kwsRecharging*getChargingFee();
        updateAmountCollected(fee);
        addEvRecharged(vehicle);
        
        setFree(true);
        return fee;
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
        return this.id.equals(other.id);
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
}