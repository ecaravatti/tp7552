/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.tree;

/**
 *
 * 
 */
public enum BSTWeightBalancedPrimitives {

    insert() {
        private static final String INSERT_CODE = "funcion insertar(clave, subarbol t) {\n" +
        										  "  si (t es nulo) entonces {\n" +
        										  "    t := nuevo_nodo;\n" +
        										  "    t.izq := nulo;\n" +
        										  "    t.der := nulo;\n" +
        										  "    t.peso := 2;\n" +
        										  "  } sino si (t.clave = clave) entonces {\n" +
        										  "    error \"clave encontrada\";\n" +
        										  "  } sino si (t.clave < clave) entonces {\n" +
        										  "    i := insertar(clave, t.der);\n" +
        										  "  } sino {\n" +
        										  "    i := insertar(clave, t.izq);\n" +
        										  "  }\n" +
        										  "  t.peso := peso(t.izq) + peso(t.der);\n" +
        										  "  chequear_rotaciones(t);\n" +
        										  "}";

        @Override
        public String getCode() {
            return INSERT_CODE;
        }
    },
    delete() {
        private static final String DELETE_CODE = "funcion borrar(clave, subarbol t) {\n" +
        										  "  si (t es nulo) entonces {\n" +
        										  "    error \"clave no encontrada\";\n" +
        										  "  } sino {\n" +
        										  "    // Busco la clave a ser borrada\n" +
        										  "    si (t.clave < clave) entonces {\n" +
        										  "      borrar(clave, t.der);\n" +
        										  "    } sino si (t.clave > clave) entonces {\n" +
        										  "      borrar(clave, t.izq);\n" +
        										  "      // Clave encontrada, borrarla si no hay descendientes\n" +
        										  "    } sino si (t.izq es nulo) entonces {\n" +
        										  "      t := t.der;\n" +
        										  "    } sino si (t.der es nulo) entonces {\n" +
        										  "      t := t.izq;\n" +
        										  "      // Descendientes no nulos, rotar del lado más pesado\n" +
        										  "    } sino si (peso(t.izq) > peso(t.der)) entonces {\n" +
        										  "      rotar_derecha(t);\n" +
        										  "      borrar(clave, t, der);\n" +
        										  "    } sino {\n" +
        										  "      rotar_izquierda(t);\n" +
        										  "      borrar(clave, t.izq);\n" +
        										  "      si (t no es nulo) entonces {\n" +
        										  "        t.peso := peso(t.izq) + peso(t.der);\n" +
        										  "        chequear_rotaciones(t);\n" +
        										  "      }\n" +
        										  "    }\n" +
        										  "  }\n" +
        										  "}";

        @Override
        public String getCode() {
            return DELETE_CODE;
        }
    };

    public abstract String getCode();
}
