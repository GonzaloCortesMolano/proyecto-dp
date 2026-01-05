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
 * * @author: Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez
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
    private static final DemoType DEMO=DemoType.NANO;
    
        
    /**
     * Constructs an {@code EVDemo} instance.
     * Initializes the company, vehicles, and stations; creates all simulation
     * components (vehicles, stations, chargers); configures their routes; and 
     * displays the initial state.
     */
    public EVDemo()
    {
        EVCompany.resetInstance();
        company= EVCompany.getInstance();
        vehicles=new ArrayList<ElectricVehicle>();
        stations=new ArrayList<ChargingStation>();
        //TODO: Complete this code
        createElectricVehicles();
        createStations(); 
        createChargers();
        configureRoutes();
        showInitialInfo();
    }

    /**
     * Runs the simulation for the fixed number of steps defined in {@link #MAXSTEPS}.
     * During each step, every electric vehicle executes one action cycle.</p>
     * 
     * After completing all steps, the demo applies several final sortings:</p>
     * 
     *   Stations sorted by number of EVs recharged
     *   Vehicles sorted by idle/arrival priority
     *   Chargers sorted by speed, fee, and ID
     * 
     * Finally, it displays the overall summary.
     */
    public void run()
    {        
        
        //Ejecutamos un número de pasos la simulación.
        //En cada paso, cada persona de reparto realiza su acción
        for(int step = 0; step < MAXSTEPS; step++) {
            step(step);
        }
        //ordena finalmente las estaciones
        /*Collections.sort(stations, new ComparatorChargingStationNumberRecharged());
        company.setChargingStations(stations);
        //ordena finalmente los vehiculos
        Collections.sort(vehicles, new ComparatorElectricVehicleIdleCount());
        company.setSubscribedVehicles(vehicles);*/
        //ordena finalmente los cargadores
        for (ChargingStation station : stations){
            station.orderList();
        } 
        
        showFinalInfo();

    }

    /**
     * Executes one step of the simulation.
     * Each vehicle performs its action for the given step.
     *
     * @param step The current simulation step.
     */
    public void step(int step)
    {
        for(ElectricVehicle vehicle: vehicles){
            vehicle.act(step);
        }
    }

    /**
     * Resets the simulation to its initial state.
     * Clears all vehicles and stations, resets the company configuration, recreates
     * all simulation components, and shows the initial information again.
     */
    public void reset()
    {
        vehicles.clear();
        stations.clear();

        createElectricVehicles();
        createStations(); 
        createChargers();
        configureRoutes();
        showInitialInfo();
    }
    
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
            company.addElectricVehicle(ev);
            vehicles.add(ev);
        }
    }
    
    /**
     * Creates predefined {@link ChargingStation}s and adds them to the company.
     * The stations list is sorted by ID using {@link ComparatorChargingStationId}.
     */
    private void createStations() {  
        Location [] locations = {new Location(5,5), new Location(15,15), new Location(5,15), new Location(15,5), new Location(10,10)};
                                
        for (int i=0;i<DEMO.getNumStationsToCreate();i++){
            ChargingStation station=new ChargingStation("Cáceres","CC0" + i,locations[i]);
            company.addChargingStation(station);
            stations.add(station);
        }
        
        // TODO: Complete code here if needed
    }
    
    /**
     * Creates a fixed number of {@link Charger} units for each {@link ChargingStation}
     * and orders the chargers within each station.
     */
    private void createChargers() {  
        Set<ChargingStation> stations = company.getCityStations();
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
            j++;
            
            station.orderList();
        }    
    }
    
    /**
     * Instructs each {@link ElectricVehicle} to compute its route,
     * determining whether it must stop to recharge before reaching its target.
     */
     private void configureRoutes() {
        Iterator<ElectricVehicle> it = vehicles.iterator();
        while(it.hasNext()){
            it.next().calculateRoute();
        } 
     }

    /**
     * Displays the initial state of the simulation, including all vehicles
     * and charging stations with their associated chargers.
     */
    private void showInitialInfo() {
        System.out.println("( Compañía "+company.getName()+" )");
        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        
        for(ElectricVehicle vehicle: vehicles){
            System.out.println(vehicle.getInitialFinalInfo());
        }

        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");
       
        for(ChargingStation station: stations){
            System.out.println(station.getCompleteInfo());
        }
        
        System.out.println("(------------------)");
        System.out.println("( Simulation start )");
        System.out.println("(------------------)");
    }

    /**
     * Displays the final summary after the simulation ends.
     * Vehicles and stations are shown with updated statistics.
     */
    private void showFinalInfo() {
 
        System.out.println("(-------------------)");
        System.out.println("( Final information )");        
        System.out.println("(-------------------)");

        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        
        for(ElectricVehicle vehicle: vehicles){
            System.out.println(vehicle.getInitialFinalInfo());
        }

        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");
       
        for(ChargingStation station: stations){
            System.out.println(station.getCompleteInfo());
        }

    }
    
    /**
     * Entry point for running the EV simulation.
     * Creates a new {@link EVDemo} instance and launches the demo.
     */
    public static void main() {
        EVDemo demo = new EVDemo();
        demo.run();
    }
}