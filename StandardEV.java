import java.util.List;
import java.util.Iterator;

/**
 * Write a description of class StandardEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardEV extends ElectricVehicle
{
    /**
     * Constructor for objects of class StandardEV
     */
    public StandardEV(EVCompany company, Location location, Location targetLocation, String name, String plate, int batteryCapacity)
    {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type=VehicleTier.STANDARD;
    }
    //requisitos para calcular el lugar de destino
    /**@Override
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
    protected boolean isBetterCharger (Charger newCharger, Charger currentBest, Location newLoc, Location bestLoc) {
    if (currentBest == null){
        return true;
    }
    // Calculamos distancias totales
    int newTotalDist = this.getLocation().distance(newLoc) + newLoc.distance(this.getTargetLocation());
    int currentTotalDist = this.getLocation().distance(bestLoc) + bestLoc.distance(this.getTargetLocation());

    // Es mejor si la distancia total es MENOR (es decir, la mejor de las dos)
    return newTotalDist < currentTotalDist;
    }    
   
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
                if(this == obj) {
                return true; 
            }
            if(!(obj instanceof StandardEV)) {
                return false;
            }
            //revisa su tipo 
            StandardEV other=(StandardEV) obj;
            return this.type.equals(other.type);
        }
        return false;
    }
}