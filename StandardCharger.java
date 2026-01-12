
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
        types.add(VehicleTier.STANDARD);
        types.add(VehicleTier.VTC);
    }
    
    // NO se implementa calculateFee().
    // Se reutiliza la lógica base del padre automáticamente por Template Method.
    
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