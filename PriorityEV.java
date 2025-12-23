import java.util.Iterator;
import java.util.List;

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
    
    /**
     * Sobrescribe la notificación para no avisar a la compañía
     */
    @Override
    protected void notifyCompany(Charger charger) {
        // No hacemos nada. PriorityEV no se registra.
    }
    
    /*
    @Override
    public void act(int step) {
        // Lógica copiada del padre para verificar si puede empezar a moverse
       boolean requirement = (hasRechargingLocation() && enoughBattery(getLocation().distance(getRechargingLocation()))) || enoughBattery(getLocation().distance(getTargetLocation()));

        if (requirement) {
            if (getLocation().equals(getTargetLocation())) {
                incrementIdleCount();
            } else {
                
                Location actual;
                if (hasRechargingLocation()) {
                    actual = getRechargingLocation();
                } else {
                    actual = getTargetLocation();
                }
                move(step);

                boolean arrived = getLocation().equals(actual);

                if (!arrived && getBatteryLevel() >= 5) {
                     Location dest;
                     if (hasRechargingLocation()) {
                         dest = getRechargingLocation();
                     } else {
                         dest = getTargetLocation();
                     }
                     
                     if (!getLocation().equals(dest)) {
                         move(step);
                     }
                }
            }
        } else {
            incrementIdleCount();
        }

        System.out.println(getStepInfo(step));
    }*/
    
    @Override
    public void act(int step) {
        super.act(step);
        if(!(getBatteryLevel()==getBatteryCapacity()) && !isInTarget()){ //si ha recargado o ha llegado al destino, termina el turno
            super.act(step);
        }
    }
    
    //consigue una estacion solo de su tipo
    public Charger getFreeChargerFromStation(){
        return getCompany().getChargingStation(getRechargingLocation()).getFreeCharger(this.type);
    }
    
    //devuelve su tipo en string
    public String getTypeInfo(){
        return "PriorityVehicle: ";
    }
    
    @Override
    boolean requirements(int distToStation, Location currentLocation){
        if(super.requirements(distToStation, currentLocation)){
            List<Charger> chargers=this.getCompany().getChargingStation(currentLocation).getChargers();
            Iterator<Charger> itCharger = chargers.iterator();
            boolean enc=false;
            //compruebas que tenga algun cargador compatible
            while(itCharger.hasNext() && !enc){
                Charger currentCharger=itCharger.next();
                if(currentCharger.canCharge(this)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
                if(this == obj) {
                return true; 
            }
            if(!(obj instanceof PriorityEV)) {
                return false;
            }
            //revisa su tipo 
            PriorityEV other=(PriorityEV) obj;
            return this.type.equals(other.type);
        }
        return false;
    }
}
    
