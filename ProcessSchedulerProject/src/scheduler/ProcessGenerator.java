/* ProcessGenerator.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler;

import java.util.Random;
import scheduler.processing.*;
import scheduler.scheduling.policies.Policy;

/**
 * Genera procesos de forma aleatoria y los encola en la politica dada.
 */
public class ProcessGenerator implements Runnable {
    private final double minDelaySec;
    private final double maxDelaySec;
    private final long arithMs, ioMs, condMs, loopMs;
    private final Policy policy;
    private final Random rnd = new Random();
    private volatile boolean stop = false;
    private int nextId = 1;

    public ProcessGenerator(String rango, long arithMs, long ioMs, long condMs, long loopMs, Policy p) {
        String[] parts = rango.split("-");
        this.minDelaySec = Double.parseDouble(parts[0]);
        this.maxDelaySec = Double.parseDouble(parts[1]);
        this.arithMs = arithMs; this.ioMs = ioMs; this.condMs = condMs; this.loopMs = loopMs;
        this.policy = p;
    }

    public void requestStop() { stop = true; }

    private long nextDelayMs() {
        double v = minDelaySec + rnd.nextDouble()*(maxDelaySec-minDelaySec);
        return (long)(v*1000);
    }

    private SimpleProcess genProcess() {
        int t = rnd.nextInt(4);
        int id = nextId++;
        switch(t) {
            case 0: return new ArithmeticProcess(id, arithMs);
            case 1: return new IOProcess(id, ioMs);
            case 2: return new ConditionalProcess(id, condMs);
            default: return new LoopProcess(id, loopMs);
        }
    }

    public void run() {
        while (!stop && !policy.isStopRequested()) {
            
            try {
                long delay = nextDelayMs();
                Thread.sleep(delay);
                SimpleProcess p = genProcess();
                policy.enqueue(p);
            } catch (InterruptedException e) { break; }
        }
        System.out.println("Generador detenido.");
    }
}

