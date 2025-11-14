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
    private static final DemoType DEMO=DemoType.ADVANCED;
    
        
    /**
     * Constructs an {@code EVDemo} instance.
     * Initializes the company, vehicles, and stations; creates all simulation
     * components (vehicles, stations, chargers); configures their routes; and 
     * displays the initial state.
     */
    public EVDemo()
    {
        company=new EVCompany("EVCharging Cáceres");
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
        Collections.sort(stations, new ComparatorChargingStationNumberRecharged());
        company.setChargingStations(stations);
        //ordena finalmente los vehiculos
        Collections.sort(vehicles, new ComparatorElectricVehicleIdleCount());
        company.setSubscribedVehicles(vehicles);
        //ordena finalmente los cargadores
        for (ChargingStation station : stations){
            List<Charger> copia=new ArrayList<>(station.getChargers());
            Collections.sort(copia, new ComparatorChargers());
            station.setChargers(copia);
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
                
        // TODO: Complete the code here

        createElectricVehicles();
        createStations(); 
        createChargers();
        configureRoutes();
        showInitialInfo();
    }

    
    /**
     * Creates the electric vehicles defined by the selected {@link DemoType}.
     * Each vehicle is assigned:
     *   a starting {@link Location}
     *   a target {@link Location}
     *   a plate and ID
     *   a battery capacity
     * 
     * Vehicles are then sorted by plate and registered in the company.
     */
    private void createElectricVehicles() {
        Location [] locations = {new Location(10,13), new Location(8,4), new Location(8,4), new Location(15,10), 
                                new Location(1,1), new Location(2,2), new Location(11,13), new Location(14,16)};
        Location [] targetLocations = {new Location(1,1), new Location(19,19), new Location(12,17), new Location(4,4), 
                                        new Location(1,10), new Location(5,5), new Location(8,7), new Location(19,19)};
                                        
        //createLocations(locations,targetLocations);
        for (int i=0;i < DEMO.getNumVehiclesToCreate();i++){
            ElectricVehicle ev = new ElectricVehicle(company, locations[i],targetLocations[i],("EV"+i),(i+"CCC"),(i+1)*15);
            vehicles.add(ev);
        }
        Collections.sort(vehicles, new ComparatorElectricVehicle());
        company.setSubscribedVehicles(vehicles);
        
    }
    
    /**
     * Creates all {@link ChargingStation} instances for the selected scenario.
     * Stations are sorted by ID before being registered in the company.
     */
    private void createStations() {  
        Location [] locations = {new Location(10,5), new Location(10,11), new Location(14,16), new Location(8,4)};
                                
        for (int i=0;i<DEMO.getNumStationsToCreate();i++){
            stations.add(new ChargingStation("Cáceres","CC0" + i,locations[i]));
        }
        Collections.sort(stations, new ComparatorChargingStationId());
        company.setChargingStations(stations);
    }

    /**
     * Creates a fixed set of {@link Charger} units for each station.
     * Charger speed and fee scale with their index. Chargers are then sorted
     * using {@link ComparatorChargers}.
     */
    private void createChargers() {  
        for (ChargingStation station : stations){
            for (int i=0;i<DEMO.getNumChargersToCreate();i++){
                // Creates chargers with varying speed and fee based on index 'i'.
                station.addCharger(new Charger(station.getId() + "_00" + i,((i+1)*20),((i+1)*0.20f)));
            }
            List<Charger> copia=new ArrayList<>(station.getChargers());
            Collections.sort(copia, new ComparatorChargers());
            station.setChargers(copia);
        }    
    }
    
    /**
     * Instructs each {@link ElectricVehicle} to compute its route,
     * determining whether it must stop to recharge before reaching its target.
     */
     private void configureRoutes() {
         for(ElectricVehicle vehicle: vehicles){
            vehicle.calculateRoute();
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
