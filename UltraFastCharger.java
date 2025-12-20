
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
    
    
    public double calculateFee(int kwsRecharging) {
        return super.calculateFee(kwsRecharging) * 1.1;
    }
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
            //revisa los tipos que admite
            if(!(obj instanceof UltraFastCharger)) return false;
            UltraFastCharger other = (UltraFastCharger) obj;
            return this.types.equals(other.types);
        }
        return false;
    }
}