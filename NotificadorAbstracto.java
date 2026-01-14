import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class NotificadorAbstracto (AbstractSubject).
 * Implements the {@link NotificadorInterfaz} interface and manages the list of observers.
 * Provides the base notification functionality for child classes.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 15-01-2026
 */
public abstract class NotificadorAbstracto implements NotificadorInterfaz {
    
    /**
     * List of subscribers (observers).
     */
    private List<CompanyObserver> observers;  // Lista de suscriptores

    
    /**
     * Default constructor.
     * Initializes the empty list of observers.
     */
    public NotificadorAbstracto() {
        this.observers = new ArrayList<>();
    }

    /**
     * Registers a new observer.
     * Adds the observer to the internal list if it is not null and not already registered.
     * * @param observer The observer to be added.
     */
    @Override
    public void addObserver(CompanyObserver observer) { //suscribir
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an existing observer from the internal list.
     * * @param observer The observer to be removed.
     */
    @Override
    public void removeObserver(CompanyObserver observer) { //anularSuscripción
        observers.remove(observer);
    }

    /**
     * Protected method to notify all registered observers.
     * This method iterates through the list of observers and invokes their update method.
     * It is not part of the public interface as it is intended for internal use by subclasses.
     *
     * @param charger The {@link Charger} where the event occurred.
     * @param ev The {@link ElectricVehicle} involved in the event.
     */
    public void notifyObservers(Charger charger, ElectricVehicle ev) { //notificar
        for (CompanyObserver observer : observers) {
            observer.onRechargeRegistered(ev, charger);
        }
    }
    
    /**
     * Clears the list of observers.
     * Useful for resetting the state of the subject without recreating it.
     */
    public void clearObservers() { //limpiar lista
        observers.clear();
    }
}