import java.util.List;
import java.util.Iterator;

/**
 * Write a description of class PremiumEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PremiumEV extends ElectricVehicle {
    public PremiumEV(EVCompany company, Location location, Location targetLocation, String name, String plate, int batteryCapacity) {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type = EnumVehicles.PREMIUM;
    }


    @Override
    public void calculateRechargingPosition() {
        //Buscar cargador con mayor velocidad
        List<ChargingStation> stations = getCompany().getCityStations();
        Location mejorEstacion = null;
        int maxSpeed = -1;

        for (ChargingStation station : stations) {
            Location stationLoc = station.getLocation();
            int distancia = getLocation().distance(stationLoc);

            //miramos si podemos llegar
            if (enoughBattery(distancia) && !getLocation().equals(stationLoc)) {
                // Buscamos el mejor cargador compatible en esta estación
                for (Charger charger : station.getChargers()) {
                    if (charger.canCharge(this)) {
                        if (charger.getChargingSpeed() > maxSpeed) {
                            maxSpeed = charger.getChargingSpeed();
                            mejorEstacion = stationLoc;
                        }
                    }
                }
            }
        }
        setRechargingLocation(mejorEstacion);
    }
}