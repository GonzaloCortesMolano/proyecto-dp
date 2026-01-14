/**
 * Subscriber class (ConcreteObserver).
 * Represents a concrete entity that listens to company notifications.
 * Implements the {@link CompanyObserver} interface.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 15-01-2026
 */
public class Suscriptor implements CompanyObserver {
    
    private String nombre;

    /**
     * Constructor for the Suscriptor class.
     * @param nombre The identifier of the subscriber (e.g., "Logistics Department").
     */
    public Suscriptor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Update method.
     * This method is automatically executed when the Subject ({@link EVCompany}) notifies a state change.
     *
     * @param vehicle The {@link ElectricVehicle} involved in the event.
     * @param charger The {@link Charger} where the event occurred.
     */
    @Override
    public void onRechargeRegistered(ElectricVehicle vehicle, Charger charger) {
        //Ejemplo de qué es lo que podría hacer este patrón observer
        String mensaje = "Suscriptor: " + this.nombre + "," +
                         "Notificación recibida: El vehículo " + vehicle.getPlate() + 
                         " ha cargado en " + charger.getId();
                         
        //System.out.println(mensaje); 
        //No lo imprimimos en pantalla para que la salida por consola coincida con la del fichero
    }
}