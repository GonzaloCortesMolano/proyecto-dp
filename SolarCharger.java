
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
        types.add(VehicleTier.VTC);
    }

    /*OVERRIDE*/
    public double calculateFee(int kwsRecharging){
        //if(canCharge(vehicle)) (ya comprobamos esto dentro de recharge en Charger)
            return (super.calculateFee(kwsRecharging))*0.9;
    }
    
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
            //revisa los tipos que admite
            if(!(obj instanceof SolarCharger)) return false;
            SolarCharger other = (SolarCharger) obj;
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