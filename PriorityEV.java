
/**
 * Write a description of class PriorityEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PriorityEV extends ElectricVehicle
{
    
    /**
     * Constructor for objects of class PriorityEV
     */
    public PriorityEV(EVCompany company, Location location, Location targetLocation, String name, String plate, int batteryCapacity)
    {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type=EnumVehicles.PRIORITY;
    }
    
    
    @Override
    public void act(int step) {
        // Lógica copiada del padre para verificar si puede empezar a moverse
       boolean requirement = (hasRechargingLocation() && enoughBattery(getLocation().distance(getRechargingLocation()))) || enoughBattery(getLocation().distance(getTargetLocation()));

        if (requirement) {
            if (getLocation().equals(getTargetLocation())) {
                incrementIdleCount();
            } else {
                //Cambiar esto a sintaxis más simple
                Location immediateDest = hasRechargingLocation() ? getRechargingLocation() : getTargetLocation();
                
                move(step);

                boolean arrived = getLocation().equals(immediateDest);

                if (!arrived && getBatteryLevel() >= 5) {
                     Location dest = hasRechargingLocation() ? getRechargingLocation() : getTargetLocation();
                     if (!getLocation().equals(dest)) {
                         move(step);
                     }
                }
            }
        } else {
            incrementIdleCount();
        }

        System.out.println(getStepInfo(step));
    }
}
    
