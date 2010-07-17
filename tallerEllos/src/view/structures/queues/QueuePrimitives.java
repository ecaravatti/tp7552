/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.queues;

import view.structures.stacks.*;

/**
 *
 * @author Pato
 */
public enum QueuePrimitives {

    enqueue() {

        private static final String ENQUEUE_CODE = "funcion insertar(dato)\n  nodo = crearNodo(dato);\n  si cantidad_nodos = 0 entonces\n    cabeza = nodo;\n  sino\n    cola.siguiente = nodo;\n  fin si\n  cola = nodo;\n  cantidad_nodos = cantidad_nodos + 1;\nfin funcion";

        @Override
        public String getCode() {
            return ENQUEUE_CODE;
        }
    },
    dequeue() {

        private static final String DEQUEUE_CODE = "funcion eliminar()\n  si cantidad_nodos == 0 entonces\n    error \"cola vacía\";\n  sino\n    nodo = cabeza;\n    cabeza = nodo.siguiente;\n    cantidad_nodos = cantidad_nodos - 1;\n    destruirNodo(nodo);\n  fin si\nfin funcion";

        @Override
        public String getCode() {
            return DEQUEUE_CODE;
        }
    };

    public abstract String getCode();
}
