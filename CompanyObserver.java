/**
 * Subscriber interface (Observer).
 * Defines the contract for any object that wishes to receive notifications 
 * about state changes from the company.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 15-01-2026
 */
public interface CompanyObserver { //Interfaz de suscriptores, contiene el método de notificación/cambio de estado
    /**
     * Update method.
     * The Subject calls this method to notify observers about a state change event.
     *
     * @param vehicle The {@link ElectricVehicle} that generated the event.
     * @param charger The {@link Charger} where the event occurred.
     */
    public void onRechargeRegistered(ElectricVehicle vehicle, Charger charger);
}