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
    private static final DemoType DEMO=DemoType.ADVANCED;
    
    
    /**
     * Creates the electric vehicles defined by the selected {@link DemoType}.
     * <p>
     * Each vehicle is assigned an initial location, a target destination,
     * a vehicle tier, and a battery capacity. Vehicles are added to the
     * {@link EVCompany} and their routes are calculated before the
     * simulation starts.
     * </p>
     */
    private void createElectricVehicles() {
        Location [] locations = {
            new Location(1,1), new Location(1,1), new Location(1,19), new Location(1,19), 
            new Location(19,1), new Location(19,1), new Location(10,19), new Location(19,10),
            new Location(10,10), new Location(10,10)
        };

        Location [] targetLocations = {
            new Location(20,20), new Location(20,20), new Location(19,1), new Location(19,1), 
            new Location(1,19), new Location(1,19), new Location(19,10), new Location(10,19),
            new Location(10,20), new Location(20,10)
        };
                                        
        for (int i = 0; i < DEMO.getNumVehiclesToCreate(); i++) {
            ElectricVehicle ev;
            int module = i % VehicleTier.numTiers();

            if (VehicleTier.values()[module] == VehicleTier.PRIORITY)
                ev = new PriorityEV(company, locations[i], targetLocations[i], "EV"+i, i+"CCC", (i+1)*(20-i));
            else if (VehicleTier.values()[module] == VehicleTier.VTC)
                ev = new VtcEV(company, locations[i], targetLocations[i], "EV"+i, i+"CCC", (i+1)*(20-i));
            else if (VehicleTier.values()[module] == VehicleTier.PREMIUM)
                ev = new PremiumEV(company, locations[i], targetLocations[i], "EV"+i, i+"CCC", (i+1)*(20-i));
            else
                ev = new StandardEV(company, locations[i], targetLocations[i], "EV"+i, i+"CCC", (i+1)*(20-i));

            ev.calculateRoute();
            company.addElectricVehicle(ev);
        }

        this.vehicles = new ArrayList<>(company.getVehicles());
    }
    

    /**
     * Creates the charging stations defined by the selected {@link DemoType}
     * and adds them to the {@link EVCompany}.
     */
    private void createStations() {  
        Location [] locations = {
            new Location(5,5), new Location(15,15),
            new Location(5,15), new Location(15,5),
            new Location(10,10)
        };
                                
        for (int i = 0; i < DEMO.getNumStationsToCreate(); i++) {
            company.addChargingStation(
                new ChargingStation("Cáceres", "CC0" + i, locations[i])
            );
        }
        
        this.stations = new ArrayList<>(company.getCityStations());
    }

    /**
     * Creates chargers for each charging station.
     * <p>
     * Different charger types are created depending on the station index
     * and charger index. Chargers are ordered once added to each station.
     * </p>
     */
    private void createChargers() {  
        List<ChargingStation> stations = new ArrayList<>(company.getCityStations());
        int j = 0;

        for (ChargingStation station : stations) {
            for (int i = 0; i < DEMO.getNumChargersToCreate(); i++) {
                Charger ch;

                if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate() - 1)) {
                    ch = new SolarCharger(station.getId() + "_00" + i, (i+j+1)*20, (i+1)*0.20f);
                }    
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate())) {
                    ch = new UltraFastCharger(station.getId() + "_00" + i, (i+j+1)*20, (i+1)*0.20f);
                } 
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate()) + 1) {
                    ch = new PriorityCharger(station.getId() + "_00" + i, (i+j+1)*20, (i+1)*0.20f);
                }    
                else {
                    ch = new StandardCharger(station.getId() + "_00" + i, (i+1)*20, (i+1)*0.20f);
                }    

                station.addCharger(ch);
            }
            station.orderList();
            j++;            
        }    
    }

    /**
     * Constructs the demo environment and runs the simulation.
     */
    public EVDemo() {
        company = EVCompany.getInstance();
        reset();            
        showInitialInfo();  
        run();              
    }

    /**
     * Resets the simulation environment.
     * <p>
     * Clears the company data and recreates charging stations,
     * chargers, and electric vehicles.
     * </p>
     */
    public void reset() {
        company.reset();
        createStations();
        createChargers();
        createElectricVehicles();
    }

    /**
     * Executes the simulation for a fixed number of steps.
     */
    public void run() {
        System.out.println("(------------------)");
        System.out.println("( Simulation start )");
        System.out.println("(------------------)");

        for (int i = 0; i < MAXSTEPS; i++) {
            step(i); 
        }
        
        showFinalInfo();
    }

    /**
     * Executes a single simulation step.
     *
     * @param step The current simulation step.
     */
    public void step(int step) {
        for (ElectricVehicle ev : vehicles) {
            ev.act(step);
        }
    }

    /**
     * Displays the initial state of the simulation, including
     * vehicles and charging stations.
     */
    private void showInitialInfo() {
        System.out.println("( " + company.getName() + " )");

        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        for (ElectricVehicle ev : vehicles) {
            System.out.println(ev.toString()); 
        }
        
        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");
        for (ChargingStation station : stations) {
            System.out.println(station.getCompleteInfo()); 
        }
    }

    /**
     * Displays the final state of the simulation, including
     * vehicle arrival information, charging station usage,
     * and company statistics.
     */
    private void showFinalInfo() {
        System.out.println("(-------------------)");
        System.out.println("( Final information )");
        System.out.println("(-------------------)");
        
        List<ElectricVehicle> finalVehicles = new ArrayList<>(vehicles);
        Collections.sort(finalVehicles, new ComparatorElectricVehicleIdleCount());

        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        for (ElectricVehicle ev : finalVehicles) {
            System.out.println(ev.getInitialFinalInfo());
        }

        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");

        List<ChargingStation> finalStations = new ArrayList<>(stations);
        Collections.sort(finalStations, new ComparatorChargingStationNumberRecharged()); 
        
        for (ChargingStation cs : finalStations) {
            System.out.println(cs.getCompleteInfo());
        }
        
        System.out.println("(--------------)");
        System.out.println("( Company Info )");
        System.out.println("(--------------)");
        System.out.println("(EVCompany: " + company.getName() + ")");  

        Map<Charger, List<ElectricVehicle>> registry = company.getChargesRegistry();
        if (registry != null) {
            for (Map.Entry<Charger, List<ElectricVehicle>> entry : registry.entrySet()) {
                System.out.println(entry.getKey().toString());
                for (ElectricVehicle ev : entry.getValue()) {
                    System.out.println(ev.toString());
                }
            }
        }
    }

    /**
     * Entry point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new EVDemo();
    }
    
}