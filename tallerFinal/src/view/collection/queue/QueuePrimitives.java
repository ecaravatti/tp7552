/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;


/**
 *
 * 
 */
public enum QueuePrimitives {

    enqueue() {

        private static final String ENQUEUE_CODE = "función encolar(dato) {\n" +
        										   "  nodo := crearNodo(dato);\n" +
        										   "  si (cantidad_nodos = 0) entonces {\n" +
        										   "    frente := nodo;\n" +
        										   "  } sino {\n" +
        										   "    final.siguiente := nodo;\n" +
        										   "  }\n" +
        										   "  final := nodo;\n" +
        										   "  cantidad_nodos := cantidad_nodos + 1;\n" +
        										   "}";

        @Override
        public String getCode() {
            return ENQUEUE_CODE;
        }
    },
    dequeue() {

        private static final String DEQUEUE_CODE = "función desencolar() {\n" +
        										   "  si (cantidad_nodos = 0) entonces {\n" +
        										   "    error \"cola vacía\";\n" +
        										   "  } sino {\n" +
        										   "    nodo := frente;\n" +
        										   "    frente := nodo.siguiente;\n" +
        										   "    cantidad_nodos := cantidad_nodos - 1;\n" +
        										   "    destruirNodo(nodo);\n" +
        										   "  }\n" +
        										   "}";

        @Override
        public String getCode() {
            return DEQUEUE_CODE;
        }
    };

    public abstract String getCode();
}
