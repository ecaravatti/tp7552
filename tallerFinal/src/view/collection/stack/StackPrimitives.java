/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.stack;

/**
 *
 * 
 */
public enum StackPrimitives {

    push() {

        private static final String PUSH_CODE = "función apilar(dato) {\n" +
        										"  nuevoNodo := crearNodo(dato);\n" +
        										"  topeAnterior := tope;\n" +
        										"  tope := nuevoNodo;\n" +
        										"  tope.siguiente := topeAnterior;\n" +
        										"  cantidad_nodos := cantidad_nodos + 1;\n" +
        										"}";

        @Override
        public String getCode() {
            return PUSH_CODE;
        }
    },
    pop() {

        private static final String POP_CODE = "función desapilar() {\n" +
        										"  si (cantidadNodos = 0) entonces {\n" +
        										"    error \"pila vacía\";\n" +
        										"  } sino {\n" +
        										"    nodo := tope;\n" +
        										"    tope := nodo.siguiente;\n" +
        										"    cantidad_nodos := cantidad_nodos - 1;\n" +
        										"    destruirNodo(nodo);\n" +
        										"  }\n" +
        										"}";

        @Override
        public String getCode() {
            return POP_CODE;
        }
    };

    public abstract String getCode();
}
