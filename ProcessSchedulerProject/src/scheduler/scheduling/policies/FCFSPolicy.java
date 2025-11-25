/* FCFSPolicy.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler.scheduling.policies;

import scheduler.processing.SimpleProcess;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * First-Come First-Served usando ConcurrentLinkedQueue.
 */
public class FCFSPolicy extends Policy {
    private final ConcurrentLinkedQueue<SimpleProcess> queue = new ConcurrentLinkedQueue<>();

    public void enqueue(SimpleProcess p) { 
        queue.add(p);
        printState("Ingreso FCFS: " + p);
    }

    public SimpleProcess nextProcess() {
        return queue.poll();
    }

    public String getName() { return "FCFS"; }

    public int pendingCount() { return queue.size(); }

    public void served(SimpleProcess p, long realServiceTime) {
        servedCount++;
        totalServiceTime += realServiceTime;
    }

    private void printState(String action) {
        System.out.println(action + " | Cola: " + queue);
}
}