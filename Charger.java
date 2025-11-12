import java.util.ArrayList;
import java.util.List;
/**
 * Model a charger unit within a {@link ChargingStation}.
 * It tracks its charging capabilities, fee, and the electric vehicles it has recharged.
 * * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2024.10.07
 */
public class Charger
{
    private String id; 
    private int chargingSpeed;
    private double chargingFee;
    private ArrayList<ElectricVehicle> eVsRecharged;
    private double amountCollected;
    private boolean free;

    /**
     * Constructor for objects of class Charger.
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
    /**
     * getters
     */
    
    /**
     * @return The charger's id .
     */
    public String getId(){
        return this.id;
    }
    
    /**
     * @return The charger's charging velocity.
     */
    public int getChargingSpeed(){
        return this.chargingSpeed;
    }
    
    /**
     * @return The charger's charging fee.
     */
    public double getChargingFee(){
        return this.chargingFee;
    }
    
    /**
     * @return The charger's list of charged vehicles.
     */
    public ArrayList<ElectricVehicle> getEVsRecharged(){
        return this.eVsRecharged;
    }
    
    /**
     * @return The charger's cuantity of collected money.
     */
    public double getAmountCollected(){
        return this.amountCollected;
    }
    /**
     * @return if the charger is or not free for use.
     */
    public boolean getFree(){
        return this.free;
    }
    
    /**
     * setters
     */
    
    /**
     * Set the charger's id
     * @param id New id for the charger
     */
    public void setId(String id){
        this.id=id;
    }
    /**
     * Set the charging speed
     * @param speed New charging speed for the charger
     */
    public void setChargingSpeed(int speed){
        this.chargingSpeed=speed;
    }
    /**
     * Set the charger's fee
     * @param fee New fee for the charger
     */
    public void setChargingFee(double fee){
        this.chargingFee = fee;
    }
    
    /**
     * Set the list new of charged {@link ElectricVehicles}
     * @param v New charger's list of {@link ElectricVehicles}
     */
    /*
    public void setEVsRecharged(ArrayList<ElectricVehicle> v){
        this.eVsRecharged = v;
    }
    */ //Rompe la encapsulación y no hace falta ya que tenemos el método addEvRecharged
    
    /**
     * Set the charger's money collected
     * @param amount The new total amount collected
     */
    public void setAmountCollected(double amount){
        this.amountCollected = amount;
    }
    
    /**
     * Sets the free status of the charger.
     * @param free The new free status (true or false).
     */
    public void setFree(boolean free){
        this.free=free;
    }
    
    /**
     * Returns a string representation of the charger, including its ID, speed, fee, and the number of EVs recharged.
     * @return A string representation of the charger.
     */
    @Override
    public String toString()
    {
        // Formato: (Charger: CC00_003, 80kwh, 0.8€, 0, 0.0€)
        // El \n (salto de línea) al final es incorrecto, debe estar en la clase Demo.
        return "(Charger: " + getId() + ", " + getChargingSpeed() + "kwh, " + 
               String.format("%.1f", getChargingFee()) + "€, " + getNumberEVRecharged() + ", " + 
               String.format("%.1f", getAmountCollected()) + "€)";
    }

    
    /**
     * Returns a complete string representation of the charger, including details of all {@link ElectricVehicle}s it has recharged.
     * @return A string containing complete information about the charger and its usage history.
     */
    public String getCompleteInfo()
    {
         String texto = this.toString() + "/n";
         for(ElectricVehicle v: eVsRecharged){
            texto += v.toString() + "/n";
         }
         return texto;
    }
    
    /**
     * @return The total number of {@link ElectricVehicle}s that have been recharged by this charger.
     */
    public int getNumberEVRecharged(){
        return eVsRecharged.size();
    }
    
    /**
     * Adds an {@link ElectricVehicle} to the list of vehicles that have been recharged by this charger.
     * @param vehicle The electric vehicle that was recharged.
     */
    public void addEvRecharged(ElectricVehicle vehicle){
        eVsRecharged.add(vehicle);
    }
    
    
    /**
     * Simulates the charging process for an {@link ElectricVehicle}.
     * Increases the amount collected and registers the vehicle as recharged.
     * @param vehicle The vehicle to recharge.
     * @param kwsRecharging The amount of kWh to be recharged.
     * @return The cost of the recharge operation.
     */
    public double recharge(ElectricVehicle vehicle,int kwsRecharging){
        setFree(false); //el cargador está siendo utilizado
        
        double fee=kwsRecharging*getChargingFee();
        updateAmountCollected(fee);
        addEvRecharged(vehicle);
        
        setFree(true); //el cargador deja de ser utilizado
        return fee;
    }
    
    public void updateAmountCollected(double money){
        this.amountCollected+=money;
    }
}