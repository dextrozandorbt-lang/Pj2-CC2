/* IOProcess.java */
/**
** Hecho por: Michael Chang
 ** Carnet: 24000414
** Hecho por: Miguel Alvarado
 ** Carnet: 24001670
 ** Seccion: D
 **/
package scheduler.processing;

public class IOProcess extends SimpleProcess {
    public IOProcess(int id, long timeMs) {
        super(id, timeMs, "IO");
    }
}