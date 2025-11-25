/* Policy.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler.scheduling.policies;

import scheduler.processing.SimpleProcess;

/**
 * Clase abstracta para politicas.
 */
public abstract class Policy implements Enqueable {
    protected volatile boolean stopRequested = false;
    protected long servedCount = 0;
    protected long totalServiceTime = 0;

    public abstract SimpleProcess nextProcess(); // obtiene el siguiente a atender (o null)
    public abstract String getName();
    public void requestStop() { stopRequested = true; }
    public long getServedCount() { return servedCount; }
    public int pendingCount() { return 0; } // override if aplica
    public double getAverageServiceTime() {
        return servedCount == 0 ? 0.0 : ((double) totalServiceTime) / servedCount;
    }

       public boolean isStopRequested() {
    return stopRequested;
}
}