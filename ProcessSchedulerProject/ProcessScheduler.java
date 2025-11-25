/* ProcessScheduler.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
import scheduler.ProcessGenerator;
import scheduler.Processor;
import scheduler.scheduling.policies.FCFSPolicy;
import scheduler.scheduling.policies.LCFSPolicy;
import scheduler.scheduling.policies.Policy;
import scheduler.scheduling.policies.PriorityPolicy;
import scheduler.scheduling.policies.RRPolicy;

/**
 * Clase principal que parsea argumentos y arranca la simulacion.
 */
public class ProcessScheduler {
    /**
     * main.
     * @param args argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Uso:");
            System.out.println("java ProcessScheduler -fcfs rango arith io cond loop");
            System.out.println("java ProcessScheduler -lcfs rango arith io cond loop");
            System.out.println("java ProcessScheduler -pp   rango arith io cond loop");
            System.out.println("java ProcessScheduler -rr   rango arith io cond loop quantum");
            return;
        }

        String flag = args[0].toLowerCase();
        String rango = args[1];
        double arith = Double.parseDouble(args[2]);
        double io = Double.parseDouble(args[3]);
        double cond = Double.parseDouble(args[4]);
        double loop = Double.parseDouble(args[5]);
        double quantum = 0;
        if (flag.equals("-rr")) {
            if (args.length < 7) { System.out.println("RR requiere quantum"); return; }
            quantum = Double.parseDouble(args[6]);
        }

        Policy policy = null;
        switch (flag) {
            case "-fcfs": policy = new FCFSPolicy(); break;
            case "-lcfs": policy = new LCFSPolicy(); break;
            case "-pp":   policy = new PriorityPolicy(); break;
            case "-rr":   policy = new RRPolicy((long)(quantum*1000)); break;
            default: System.out.println("Politica no reconocida"); return;
        }

        // Arrancar generador y procesador
        ProcessGenerator gen = new ProcessGenerator(rango, (long)(arith*1000), (long)(io*1000),
                (long)(cond*1000), (long)(loop*1000), policy);
        Processor cpu = new Processor(policy);

        Thread genThread = new Thread(gen, "Generator");
        Thread cpuThread = new Thread(cpu, "Processor");

        genThread.start();
        cpuThread.start();

        // Listener de 'q' para terminar
        try {
            int c;
            while ((c = System.in.read()) != -1) {
                if (c == 'q' || c == 'Q') {
                    System.out.println("\nSolicitud de terminacion recibida (q). Deteniendo...");
                    gen.requestStop();
                    policy.requestStop();
                    cpu.requestStop();
                    break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        try {
            genThread.join();
            cpuThread.join();
        } catch (InterruptedException e) { e.printStackTrace(); }

        // Impresion final
        System.out.println("=== FIN DE SIMULACION ===");
        System.out.println("Politica: " + policy.getName());
        System.out.println("Procesos atendidos: " + policy.getServedCount());
        System.out.println("Procesos en cola: " + policy.pendingCount());
        System.out.printf("Tiempo promedio de atencion (ms): %.2f\n", policy.getAverageServiceTime());
    }
}
