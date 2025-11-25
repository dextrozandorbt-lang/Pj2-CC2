/* Enqueable.java */
/**
 ** Hecho por: Tu Nombre
 ** Carnet: ########
 ** Seccion: X
 **/
package scheduler.scheduling.policies;

import scheduler.processing.SimpleProcess;

/**
 * Interface para politicas que permiten encolar procesos.
 */
public interface Enqueable {
    void enqueue(SimpleProcess p);
}