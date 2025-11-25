/* SimpleProcess.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler.processing;

/**
 * Clase abstracta que representa un proceso simple.
 */
public abstract class SimpleProcess {
    protected final int id;
    protected final long totalTimeMs; // tiempo total en ms
    protected final String shortType; // "A","IO","C","L"

    /**
     * Constructor.
     * @param id id del proceso.
     * @param totalTimeMs tiempo total en ms.
     * @param shortType tipo abreviado.
     */
    public SimpleProcess(int id, long totalTimeMs, String shortType) {
        this.id = id;
        this.totalTimeMs = totalTimeMs;
        this.shortType = shortType;
    }

    public int getId() { return id; }
    public long getTotalTimeMs() { return totalTimeMs; }
    public String getShortType() { return shortType; }

    public String toString() {
        return String.format("[%d,%dms,%s]", id, totalTimeMs, shortType);
        }
}