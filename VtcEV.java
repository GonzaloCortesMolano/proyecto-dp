import java.util.List;
import java.util.Iterator;
import java.util.*;

/**
 * Write a description of class VtcEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VtcEV extends ElectricVehicle {
    public VtcEV(EVCompany company, Location location, Location targetLocation, String name, String plate, int batteryCapacity) {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type = VehicleTier.VTC;
    }
    
    /**
    @Override
    public void calculateRechargingPosition() {
        //Buscar cargador compatible (Standard/Solar) más barato
        Set<ChargingStation> stations = getCompany().getCityStations();
        Location mejorEstacion = null;
        double minFee = Double.MAX_VALUE;

        for (ChargingStation station : stations) {
            Location stationLoc = station.getLocation();
            int dist = getLocation().distance(stationLoc);

            if (enoughBattery(dist) && !getLocation().equals(stationLoc)) {
                for (Charger charger : station.getChargers()) {
                    if (charger.canCharge(this)) {
                        if (charger.getChargingFee() < minFee) {
                            minFee = charger.getChargingFee();
                            mejorEstacion = stationLoc;
                        }
                    }
                }
            }
        }
        setRechargingLocation(mejorEstacion);
    }
    */
   @Override
    protected boolean isBetterCharger(Charger newCharger, Charger currentBest, Location newLoc, Location bestLoc) {
        if (currentBest == null){
            return true;
        }

        // busca el de menor fee
        return newCharger.getChargingFee() < currentBest.getChargingFee();
    }
   
    @Override
    public Charger getFreeChargerFromStation(){
        List<Charger> list=getCompany().getChargingStation(getRechargingLocation()).getChargers();
        double lowestPrice=Double.MAX_VALUE;
        Charger bestCharger = null;
        for(Charger c:list){
            if(c.canCharge(this)){
                if(c.getChargingFee()<lowestPrice){
                    lowestPrice=c.getChargingFee();
                    bestCharger=c;
                }
            }
        }
        return bestCharger;
    }
}