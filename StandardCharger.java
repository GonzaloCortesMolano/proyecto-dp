
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
    
    public canCharge(ElectricVehicle vehicle){
        
    }
}