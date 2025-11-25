/* LCFSPolicy.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler.scheduling.policies;

import java.util.Stack;
import scheduler.processing.SimpleProcess;

/**
 * Last-Come First-Served usando Stack.
 */
public class LCFSPolicy extends Policy {
    private final Stack<SimpleProcess> stack = new Stack<>();

    public void enqueue(SimpleProcess p) {
        stack.push(p);
        System.out.println("Ingreso LCFS: " + p + " | Pila: " + stack);
    }

    public SimpleProcess nextProcess() {
        if (stack.isEmpty()) return null;
        return stack.pop();
    }

    public String getName() { return "LCFS"; }

    public int pendingCount() { return stack.size(); }

    public void served(SimpleProcess p, long realServiceTime) {
        servedCount++;
        totalServiceTime += realServiceTime;
    }
}