package event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;

public class ObservableBase<T extends EventListener> {

    private Collection<T> listeners;

    public ObservableBase() {
        super();
        this.listeners = new ArrayList<T>();
    }

    /**
     * Agrega un listener.
     * @param listener listener a agregar.
     */
    public synchronized void addListener(T listener) {
        if (listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

    /**
     * Elimina un listener.
     * @param listener listener a eliminar.
     */
    public synchronized void removeListener(T listener) {
        listeners.remove(listener);
    }

    /**
     * Obtiene la lista con los listeners
     * @return la lista con los listeners
     */
    protected synchronized Collection<T> getListeners() {
        return listeners;
    }
}
