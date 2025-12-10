
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
        tipos.add(EnumVehicles.STANDARD);
        tipos.add(EnumVehicles.VTC);
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
            return this.tipos.equals(obj.tipos);
        }
        return false;
    }
}