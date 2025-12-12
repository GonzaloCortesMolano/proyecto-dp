
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
        types.add(EnumVehicles.PRIORITY);
    }

    /*OVERRIDE*/
    public double recharge(ElectricVehicle vehicle,int kwsRecharging){
        if(canCharge(vehicle)){
            return super.recharge(vehicle, kwsRecharging);
        }
        
        return 0;
    }
    
    public boolean equals(Charger obj){
        if(super.equals(obj)) {
            //revisa los tipos que admite
            return this.types.equals(obj.types);
        }
        return false;
    }
}