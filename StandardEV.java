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
        tipo=EnumVehicles.STANDARD;
    }
    
    public void calculateRechargingPosition()
    {
        List<ChargingStation> stations = this.getCompany().getCityStations();
        Iterator<ChargingStation> it = stations.iterator();
        int betterDistance = 999;
        Location betterStation = null;

        while (it.hasNext()) {
            ChargingStation currentStation = it.next();
            Location currentLocation = currentStation.getLocation();
            int distToStation = this.getLocation().distance(currentLocation);
            
            if (enoughBattery(distToStation) && !getLocation().equals(currentLocation)) { //Si puedo llegar a la estación:
                List<Charger> chargers=currentStation.getChargers();
                Iterator<Charger> itCharger = chargers.iterator();
                boolean enc=false;
                //compruebas que tenga algun cargador compatible
                while(itCharger.hasNext() && !enc){
                    Charger currentCharger=itCharger.next();
                    
                    if(currentCharger.canCharge(this)){
                        enc=true; //calculas la distancia si es compatible
                        int distance = distToStation + currentLocation.distance(this.getTargetLocation());
                        if (distance < betterDistance) { // Si es la actual la ruta más corta:
                            betterStation = currentLocation;
                            betterDistance = distance;
                        }
                    }
                }
                enc=false;
            }   
            setRechargingLocation(betterStation); // Si no se encuentra ninguna, se asigna null
        }
    }
    
    //recharge mirar tipo
    //act el texto de cada paso
    //equals comparar tipos
}