/* Processor.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler;

import scheduler.processing.SimpleProcess;
import scheduler.scheduling.policies.Policy;
import scheduler.scheduling.policies.RRPolicy;

/**
 * Simula un procesador que saca procesos de la policy y "los atiende".
 */
public class Processor implements Runnable {
    private final Policy policy;
    private volatile boolean stop = false;

    public Processor(Policy policy) { this.policy = policy; }

    public void requestStop() { stop = true; }

    public void run() {
        while (!stop && !policy.isStopRequested()) {
            try {
                SimpleProcess p = policy.nextProcess();
                if (p == null) {
                    Thread.sleep(100); // espera corta si no hay procesos
                    continue;
                }

                long runTime;
                if (policy instanceof RRPolicy) {
                    // en este diseño, RRPolicy.define el quantum
                    runTime = Math.min(p.getTotalTimeMs(), ((RRPolicy)policy).getQuantumMs());
                    // Nota: para un RR correcto debería trackear remaining por proceso
                } else {
                    runTime = p.getTotalTimeMs();
                }

                System.out.println("Atendiendo: " + p + " por " + runTime + " ms");
                long start = System.currentTimeMillis();
                Thread.sleep(runTime);
                long real = System.currentTimeMillis() - start;
                System.out.println("Terminado: " + p + " (tiempo real " + real + " ms)");

                // registrar servicio en policy si implementa método
                try {
                    policy.getClass().getMethod("served", SimpleProcess.class, long.class)
                            .invoke(policy, p, real);
                } catch (Exception ex) {
                    // no importa si no existe
                }
            } catch (InterruptedException e) { break; }
        }
        System.out.println("Procesador detenido.");
    }
}
