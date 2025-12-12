
/**
 * Write a description of class StandardCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardCharger extends Charger
{    

    /**
     * @override
     * Constructor for objects of class StandardCharger
     */
    public StandardCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        types.add(EnumVehicles.STANDARD);
        types.add(EnumVehicles.VTC);
    }
    /*OVERRIDE*/
    public double recharge(ElectricVehicle vehicle,int kwsRecharging){
        if(canCharge(vehicle)){
            return super.recharge(vehicle, kwsRecharging);
        }
        
        return 0;
    }
    
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
                if(this == obj) {
                return true; 
            }
            if(!(obj instanceof StandardCharger)) {
                return false;
            }
            //revisa su tipo 
            StandardCharger other=(StandardCharger) obj;
            return this.types.equals(other.types);
        }
        return false;
    }
}