
/**
 * Write a description of class UltraFastCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UltraFastCharger extends Charger
{
    
    /**
     * @override
     * Constructor for objects of class StandardCharger
     */
    public UltraFastCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        types.add(EnumVehicles.PREMIUM);
    }
    
    public double recharge(ElectricVehicle vehicle,int kwsRecharging){
        if(canCharge(vehicle)){
            return (super.recharge(vehicle, kwsRecharging))*1.1;
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