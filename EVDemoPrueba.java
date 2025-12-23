import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Class to demonstrate the functionality of the EV Charging System.
 * Updated to force recharges and show the 'Company Info' populated.
 * * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 12-11-2025
 */
public class EVDemoPrueba
{
    private EVCompany company;
    private List<ElectricVehicle> vehicles;
    private List<ChargingStation> stations;

    /**
     * Constructor for objects of class EVDemo
     */
    public EVDemoPrueba()
    {
        company = new EVCompany("Electro Charge Solutions");
        vehicles = new ArrayList<>();
        stations = new ArrayList<>();
        
        // Inicializamos el escenario
        initializeProject();
    }

    /**
     * Inicializa los datos de la simulación.
     * Se han configurado baterías bajas para FORZAR que los vehículos carguen
     * y así aparezcan en el registro de la compañía al final.
     */
public void initializeProject(){
        // 1. Limpiamos estado anterior
        vehicles.clear();
        stations.clear();
        company.reset();
        
        // 2. Creamos Estación en (10, 11)
        Location locStation = new Location(10, 11);
        ChargingStation station1 = new ChargingStation("Cáceres", "CC01", locStation);
        
        // Añadimos mezcla de cargadores
        // NOTA: Asegúrate de que tus cargadores (StandardCharger, etc.) 
        // añaden su tipo a la lista 'types' en sus constructores, 
        // o usa el Charger 'parcheado' que te pasé antes.
        station1.addCharger(new StandardCharger("CC01_STD", 40, 0.40));
        station1.addCharger(new SolarCharger("CC01_SOL", 50, 0.45)); 
        station1.addCharger(new UltraFastCharger("CC01_FST", 100, 0.80)); 
        station1.addCharger(new PriorityCharger("CC01_PRI", 60, 0.50)); 
        
        stations.add(station1);
        company.addChargingStation(station1);

        // 3. Creamos Vehículos CERCA DE LA ESTACIÓN (10, 11)
        // Necesitan batería suficiente para moverse 1 o 2 pasos (coste 5 o 10)
        
        // StandardEV: Empieza en (10, 10). Distancia a estación: 1. Coste: 5. Batería: 15 (LLEGA)
        ElectricVehicle ev1 = new StandardEV(company, new Location(10, 10), new Location(0, 0), "Tesla Model 3", "1234-BBB", 15);
        vehicles.add(ev1);
        company.addElectricVehicle(ev1);
        
        // PremiumEV: Empieza en (10, 12). Distancia a estación: 1. Coste: 5. Batería: 15 (LLEGA)
        ElectricVehicle ev2 = new PremiumEV(company, new Location(10, 12), new Location(15, 15), "Porsche Taycan", "9999-PRM", 15);
        vehicles.add(ev2);
        company.addElectricVehicle(ev2);
        
        // VtcEV: Empieza en (9, 11). Distancia a estación: 1. Coste: 5. Batería: 10 (LLEGA JUSTO)
        ElectricVehicle ev3 = new VtcEV(company, new Location(9, 11), new Location(2, 2), "Toyota Prius", "5555-VTC", 10);
        vehicles.add(ev3);
        company.addElectricVehicle(ev3);
        
        // PriorityEV: Empieza en (11, 11). Distancia a estación: 1. Coste: 5. Batería: 20 (LLEGA)
        ElectricVehicle ev4 = new PriorityEV(company, new Location(11, 11), new Location(19, 19), "Ambulance", "0000-SOS", 20);
        vehicles.add(ev4);
        company.addElectricVehicle(ev4);
    }
    
    /**
     * Ejecuta la simulación durante un número de pasos.
     */
    public void run(int steps){
        showInitialInfo();
        
        for(int i=0; i<steps; i++){
           // Ejecutamos un paso para cada vehículo
           for(ElectricVehicle ev : vehicles){
               ev.act(i);
           }
        }
        
        showFinalInfo();
    }
    
    public void showInitialInfo(){
        System.out.println("(---------------------)");
        System.out.println("( Initial information )");
        System.out.println("(---------------------)");
        System.out.println("(---------------------)");
        System.out.println("( Electric Vehicles   )");
        System.out.println("(---------------------)");
        
        Collections.sort(vehicles, new ComparatorElectricVehiclePlate());
        
        for(ElectricVehicle ev : vehicles){
            System.out.println(ev.getInitialFinalInfo());
        }
        
        System.out.println("(---------------------)");
        System.out.println("( Charging Stations   )");
        System.out.println("(---------------------)");
        
        Collections.sort(stations, new ComparatorChargingStationId());
        
        for(ChargingStation st : stations){
            System.out.println(st.getCompleteInfo());
        }
    }
    
    public void showFinalInfo(){
        System.out.println("\n(---------------------)");
        System.out.println("( Final information   )");
        System.out.println("(---------------------)");
        
        // 1. Vehículos
        System.out.println("(---------------------)");
        System.out.println("( Electric Vehicles   )");
        System.out.println("(---------------------)");
        Collections.sort(vehicles, new ComparatorElectricVehiclePlate());
        for(ElectricVehicle ev : vehicles){
            System.out.println(ev.getInitialFinalInfo());
        }
        
        // 2. Estaciones
        System.out.println("(---------------------)");
        System.out.println("( Charging Stations   )");
        System.out.println("(---------------------)");
        Collections.sort(stations, new ComparatorChargingStationId());
        for(ChargingStation st : stations){
             System.out.println(st.getCompleteInfo());
        }
        
        // 3. COMPANY INFO (Aquí aparecerán los datos porque hemos forzado la carga)
        System.out.println("(---------------------)");
        System.out.println("( Company Info        )");
        System.out.println("(---------------------)");
        
        Map<String, List<ElectricVehicle>> registry = company.getChargesRegistry();
        
        if (registry != null && !registry.isEmpty()) {
            List<String> sortedChargerIds = new ArrayList<>(registry.keySet());
            Collections.sort(sortedChargerIds);
            
            for(String chargerId : sortedChargerIds){
                List<ElectricVehicle> evs = registry.get(chargerId);
                
                System.out.println("(Charger: " + chargerId + ")");
                
                for(ElectricVehicle ev : evs){
                    System.out.println(ev.toString());
                }
            }
        }
    }
}