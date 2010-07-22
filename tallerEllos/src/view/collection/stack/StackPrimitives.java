/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.stack;

/**
 *
 * @author Pato
 */
public enum StackPrimitives {

    push() {

        private static final String PUSH_CODE = "funcion insertar(dato)\n  nuevoNodo = crearNodo(dato);\n  topeAnterior = tope;\n  tope = nuevoNodo;\n  tope.siguiente = topeAnterior;\n  cantidad_nodos = cantidad_nodos + 1;\nfin funcion";

        @Override
        public String getCode() {
            return PUSH_CODE;
        }
    },
    pop() {

        private static final String POP_CODE = "funcion eliminar()\n  si cantidadNodos == 0 entonces\n    error \"cola vacía\";\n  sino\n    nodo = tope;\n    tope = nodo.siguiente;\n    cantidad_nodos = cantidad_nodos - 1;\n    destruirNodo(nodo);\n  fin si\nfin funcion";

        @Override
        public String getCode() {
            return POP_CODE;
        }
    };

    public abstract String getCode();
}
