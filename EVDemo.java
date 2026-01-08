import java.util.*;
/**
 * Provides a simple demonstration and simulation environment for the 
 * Electric Vehicle (EV) and Charging Station model.
 * It sets up the environment and runs the simulation for a fixed number of steps.
 *
 * Scenarios: 
 * <ul>
 * <li>Demo SIMPLE (demo=DemoType.SIMPLE): Two vehicles are created.</li>
 * <li>Demo MEDIUM (demo=DemoType.MEDIUM): Five vehicles are created.</li>
 * <li>Demo ADVANCED(demo=DemoType.ADVANCED): Eight vehicles are created.</li>
 * </ul>
 * * @author DP Clasess
 * @version 2025
 */
public class EVDemo
{
    
    /** The maximum X coordinate for the grid (number of rows). */
    public static final int MAXX = 20; 
    /** The maximum Y coordinate for the grid (number of columns). */
    public static final int MAXY = 20; 
    
    /** The maximum number of turns. */
    public static final int MAXSTEPS = 50;
    
    /** The company managing the EVs and charging stations. */
    private EVCompany company;
    
    /** * The simulation's vehicles; they are the electric vehicles of the company. 
     * @see ElectricVehicle 
     */
    private List<ElectricVehicle> vehicles; 
    
    /** * The charging stations available in the city. 
     * @see ChargingStation 
     */
    private List<ChargingStation> stations;
    
    /** Constant for selecting the demo scenario, using the {@link DemoType} enumeration. */
    private static final DemoType DEMO=DemoType.MEDIUM;
    
    
    /**
     * Creates the {@link ElectricVehicle}s based on {@code numVehiclesCreated}, assigns them
     * starting and target {@link Location}s, and adds them to the company.
     * The vehicles list is sorted by plate using {@link ComparatorEVPlate}.
     */
    private void createElectricVehicles() {
        Location [] locations = {new Location(1,1), new Location(1,1), new Location(1,19), new Location(1,19), 
                                new Location(19,1), new Location(19,1), new Location(10,19), new Location(19,10),
                                new Location(10,10), new Location(10,10)};

        Location [] targetLocations = {new Location(20,20), new Location(20,20), new Location(19,1), new Location(19,1), 
                                       new Location(1,19), new Location(1,19), new Location(19,10), new Location(10,19),
                                       new Location(10,20), new Location(20,10)};
                                        
        //createLocations(locations,targetLocations);
        for (int i=0;i < DEMO.getNumVehiclesToCreate();i++){
            ElectricVehicle ev;
            int module = i % VehicleTier.numTiers();
            if (VehicleTier.values()[module] == VehicleTier.PRIORITY) ev = new PriorityEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            else if (VehicleTier.values()[module] == VehicleTier.VTC) ev = new VtcEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            else if (VehicleTier.values()[module] == VehicleTier.PREMIUM) ev = new PremiumEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            else ev = new StandardEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            ev.calculateRoute();
            company.addElectricVehicle(ev);
        }
        // TODO: Complete code here if needed
        this.vehicles = new ArrayList<>(company.getVehicles());
        Collections.sort(this.vehicles, new ComparatorElectricVehiclePlate());
    }
    

    /**
     * Creates predefined {@link ChargingStation}s and adds them to the company.
     * The stations list is sorted by ID using {@link ComparatorChargingStationId}.
     */
    private void createStations() {  
        Location [] locations = {new Location(5,5), new Location(15,15), new Location(5,15), new Location(15,5), new Location(10,10)};
                                
        for (int i=0;i<DEMO.getNumStationsToCreate();i++){
            company.addChargingStation(new ChargingStation("Cáceres","CC0" + i,locations[i]));
        }
        
        // TODO: Complete code here if needed
        this.stations = new ArrayList<>(company.getCityStations());
        Collections.sort(this.stations, new ComparatorChargingStationId());
    }

    /**
     * Creates a fixed number of {@link Charger} units for each {@link ChargingStation}
     * and orders the chargers within each station.
     */
    private void createChargers() {  
        List<ChargingStation> stations = new ArrayList<>(company.getCityStations());
        int j=0;
        for (ChargingStation station : stations){
            for (int i=0;i<DEMO.getNumChargersToCreate();i++){
                // Creates chargers with varying speed and fee based on index 'i' and 'j'.
                Charger ch;
                if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate()-1)) {
                    ch = new SolarCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                }    
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate())) {
                    ch = new UltraFastCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                } 
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate())+1) {
                    ch = new PriorityCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                }    
                else {
                    ch = new StandardCharger(station.getId() + "_00" + i, ((i+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                }    
                station.addCharger(ch);
            }
            station.orderList();
            j++;            
        }    
    }


    public EVDemo() {
        company = EVCompany.getInstance();
        reset();            
        showInitialInfo();  
        run();              
    }

    /**
     * 
     */
    public void reset() {
       
        company.reset();
        
        // crear objetos y almacenarlos (hemos ordenado dentro de estos métodos)
        createStations();
        createChargers();
        createElectricVehicles();
    }

    /**
     * 
     */
    public void run() { //ejecuta la simulación
        System.out.println("(------------------)");
        System.out.println("( Simulation start )");
        System.out.println("(------------------)");
        for (int i = 0; i < MAXSTEPS; i++) {
            step(i); 
        }
        
        showFinalInfo();
    }

    /**
     * 
     */
    public void step(int step) {
        for (ElectricVehicle ev : vehicles) {
            ev.act(step);
        }
    }

    /**
     * 
     */
    private void showInitialInfo() {
        
        System.out.println("( " + company.getName() + " )");
        
        //Vehículos ordenados por matrícula
        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        for(ElectricVehicle ev : vehicles) {
            System.out.println(ev.toString()); 
        }
        
        //Estaciones por ID y sus cargadores ordenados
        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");
        for(ChargingStation station : stations) {
            
            System.out.println(station.getCompleteInfo()); 
            
            
            /*
            List<Charger> cargadoresOrdenados = new ArrayList<>(station.getChargers());
            Collections.sort(cargadoresOrdenados, new ComparatorChargers());
            
            for(Charger charger : cargadoresOrdenados) {
                System.out.println(charger.toString());
            }
            */
        }
       
    }

    /**
     *
     */
    private void showFinalInfo() {
        System.out.println("(-------------------)");
        System.out.println("( Final information )");
        System.out.println("(-------------------)");
        
        // Orden: Turno de llegada creciente (idleCount decreciente), luego Matrícula.
        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        


        //usar um set
        List<ElectricVehicle> finalVehicles = new ArrayList<>(vehicles);
        Collections.sort(finalVehicles, new ComparatorElectricVehicleIdleCount());
        /*{
            @Override
            public int compare(ElectricVehicle v1, ElectricVehicle v2) {
                int idleCompare = Integer.compare(v2.getIdleCount(), v1.getIdleCount());
                if (idleCompare != 0) {
                    return idleCompare;
                }
                return v1.getPlate().compareTo(v2.getPlate());
            }
        });*/

        for(ElectricVehicle ev : finalVehicles) {
            System.out.println(ev.getInitialFinalInfo());
        }

        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");
        
        //cambiar por set
        /**
        List<ChargingStation> finalStations = new ArrayList<>(stations);
        Collections.sort(finalStations, new Comparator<ChargingStation>() {
            @Override
            public int compare(ChargingStation s1, ChargingStation s2) {
                // Decreciente por número total de recargas 
                int total1 = 0;
                for(Charger c : s1.getChargers()) total1 += c.getNumberEVRecharged();
                
                int total2 = 0;
                for(Charger c : s2.getChargers()) total2 += c.getNumberEVRecharged();
                
                // Decreciente por recargas
                int comp = Integer.compare(total2, total1);
                if(comp != 0) return comp;
                // Creciente por ID
                return s1.getId().compareTo(s2.getId());
            }
        });
        */
        // Registro de notificaciones por cargadores
        Map<Charger, List<ElectricVehicle>> registry = company.getChargesRegistry();
        
        for(ChargingStation cs : stations) {
            System.out.println(cs.getCompleteInfo());
            /*
            List<Charger> cargadoresOrdenados = new ArrayList<>(cs.getChargers());
            /*
            Collections.sort(cargadoresOrdenados, new ComparatorChargers());
            
            for(Charger ch : cargadoresOrdenados) {
                System.out.println(ch.getCompleteInfo());
                /*
                if (registry.containsKey(ch)) {
                    for(ElectricVehicle ev : registry.get(ch)) {
                        System.out.println(ev.getInitialFinalInfo());
                    }
                }
                */
        }
        System.out.println("(--------------)");
        System.out.println("( Company Info )");
        System.out.println("(--------------)");
        System.out.println("(EVCompany: " + company.getName() + ")");  
        
        if (registry != null){
            for (Map.Entry<Charger, List<ElectricVehicle>> mapa : registry.entrySet()){
                
                Charger charger = mapa.getKey(); 
                List<ElectricVehicle> evs = mapa.getValue();
                
                System.out.println(charger.toString());
                
                for(ElectricVehicle ev : evs){
                    System.out.println(ev.toString());
                }
            }
        }
        
        
    }

    public static void main(String[] args) {
        new EVDemo();
    }
    
}