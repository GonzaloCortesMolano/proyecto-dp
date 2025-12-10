
/**
 * Write a description of class StandardEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardEV extends ElectricVehicle
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class StandardEV
     */
    public StandardEV(EVCompany company, Location location, Location targetLocation, String name, String plate, int batteryCapacity)
    {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        tipo=EnumVehicles.STANDARD;
    }
}