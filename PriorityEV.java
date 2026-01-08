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
        type=VehicleTier.PRIORITY;
    }
    
    /*
     * Sobrescribe la notificación para no avisar a la compañía
     */
    @Override
    protected void notifyCompany(Charger charger) {
        // No hacemos nada. PriorityEV no se registra.
    }
    
   
    @Override
    protected void move(int step) {
        // Mueve una vez
        super.move(step);
        
        // Si no ha llegado y tiene batería, mueve otra vez (Doble velocidad)
        if (!isInTarget() && !isInStation() && getBatteryLevel() > 0) {
            super.move(step);
        }
    }
    /**
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
    */
   @Override
   protected boolean isBetterCharger(Charger newCharger, Charger currentBest, Location newLoc, Location bestLoc) {
        if (currentBest == null) {
            return true;   
        }

        // priority solo tiene en cuenta si llega antes al destino final
        int newDistToTarget = newLoc.distance(this.getTargetLocation());
        int currentDistToTarget = bestLoc.distance(this.getTargetLocation());

        // Es mejor si está más cerca del destino
        return newDistToTarget < currentDistToTarget;
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