/* PriorityPolicy.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler.scheduling.policies;

import scheduler.processing.SimpleProcess;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Priority Policy: prioridad 1 (mayor) a 3 (menor). Se asume que los procesos
 * son instancias de DecoratedPriorityProcess que llevan prioridad.
 */
public class PriorityPolicy extends Policy {
    private static class Entry {
        final SimpleProcess p;
        final int priority;
        final long arrivalOrder;
        Entry(SimpleProcess p, int priority, long arrivalOrder) {
            this.p = p; this.priority = priority; this.arrivalOrder = arrivalOrder;
        }
        public String toString() { return p.toString()+":prio"+priority; }
    }

    private final PriorityQueue<Entry> pq;
    private long counter = 0;

    public PriorityPolicy() {
        pq = new PriorityQueue<>(Comparator.comparingInt((Entry e) -> e.priority)
                .thenComparingLong(e -> e.arrivalOrder));
    }

    /**
     * Para encolar necesitas pasar un SimpleProcess que contenga su prioridad.
     * Aqui, por simplicidad, asumimos que si SimpleProcess implementa HasPriority extraemos.
     */
    public void enqueue(SimpleProcess p) {
        int pr = 3; // default menor
        if (p instanceof HasPriority) pr = ((HasPriority)p).getPriority();
        pq.add(new Entry(p, pr, counter++));
        System.out.println("Ingreso PP: " + p + " | Cola: " + pq);
    }

    public SimpleProcess nextProcess() {
        Entry e = pq.poll();
        return e == null ? null : e.p;
    }

    public String getName() { return "PriorityPolicy"; }

    public int pendingCount() { return pq.size(); }

    public void served(SimpleProcess p, long realServiceTime) {
        servedCount++;
        totalServiceTime += realServiceTime;
    }

    public interface HasPriority { 
        int getPriority(); }
}