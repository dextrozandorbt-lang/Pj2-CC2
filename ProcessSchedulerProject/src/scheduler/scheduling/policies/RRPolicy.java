/* RRPolicy.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler.scheduling.policies;

import scheduler.processing.SimpleProcess;
import java.util.LinkedList;

/**
 * Round Robin usando LinkedList (una politica puede usar LinkedList).
 * Mantiene tiempo restante en objetos adornados.
 */
public class RRPolicy extends Policy {
    private static class RRItem {
        final SimpleProcess p;
        long remaining;
        RRItem(SimpleProcess p) { this.p = p; this.remaining = p.getTotalTimeMs(); }
        public String toString() { return p.toString() + "(rem:" + remaining + "ms)"; }
    }

    private final LinkedList<RRItem> queue = new LinkedList<>();
    private final long quantumMs;

    public RRPolicy(long quantumMs) { this.quantumMs = quantumMs; }

    public void enqueue(SimpleProcess p) {
        queue.add(new RRItem(p));
        System.out.println("Ingreso RR: " + p + " | Cola: " + queue);
    }

    public SimpleProcess nextProcess() {
        if (queue.isEmpty()) return null;
        RRItem item = queue.removeFirst();
        long run = Math.min(item.remaining, quantumMs);
        // Simulamos que se atenderá 'run' ms, pero la lógica real la maneja Processor.
        item.remaining -= run;
        if (item.remaining > 0) {
            // re-enqueue al final con remaining actualizado
            queue.addLast(item);
        } else {
            // terminado, contar servicio total
            servedCount++;
            totalServiceTime += item.p.getTotalTimeMs();
        }
        return item.p; // el Processor debe saber cuánto tiempo correr: min(total, quantum)
    }

    public long getQuantumMs() { return quantumMs; }

    public String getName() { return "RR"; }

    public int pendingCount() { return queue.size(); }
}