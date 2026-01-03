
/**
 * Write a description of class PriorityCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PriorityCharger extends Charger
{
    /**
     * Constructor for objects of class PriorityCharger
     */
    public PriorityCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        types.add(VehicleTier.PRIORITY);
    }
    
    /*
    public double recharge(ElectricVehicle vehicle,int kwsRecharging){
        if(canCharge(vehicle)){
            return super.recharge(vehicle, kwsRecharging);
        }
        
        return 0;
    }
    */ 
   
    // Ocurre lo mismo que con StandardCharger.
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
            //revisa los tipos que admite
            if(!(obj instanceof PriorityCharger)) return false;
            PriorityCharger other = (PriorityCharger) obj;
            return this.types.equals(other.types);
        }
        return false;
    }
    
    /**
     * TODO devuelve si el vehiculo puede cargar ahi
    @Override
    public boolean canCharge(ElectricVehicle vehicle){
        return compareType(vehicle.getType());
    }
    */
}