/**
 * Subject interface.
 * Defines the contract for subscription management.
 * Represents the top abstraction layer of the Observer pattern.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 15-01-2026
 */
public interface NotificadorInterfaz {
    
    /**
     * Registers a new observer.
     * @param observer The observer to be added.
     */
    void addObserver(CompanyObserver observer);

    /**
     * Removes an existing observer.
     * @param observer The observer to be removed.
     */
    void removeObserver(CompanyObserver observer);
}