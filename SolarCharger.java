
/**
 * Write a description of class SolarCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SolarCharger extends Charger
{
    /**
     * Constructor for objects of class SolarCharger
     */
    public SolarCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        types.add(EnumVehicles.VTC);
    }

    /*OVERRIDE*/
    public double recharge(ElectricVehicle vehicle,int kwsRecharging){
        if(canCharge(vehicle)){
            return (super.recharge(vehicle, kwsRecharging))*0.9;
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