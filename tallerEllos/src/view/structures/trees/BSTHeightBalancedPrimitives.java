/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.trees;

/**
 *
 * @author Pato
 */
public enum BSTHeightBalancedPrimitives {

    insert() {

        private static final String INSERT_CODE = "funcion insertar(clave, subarbol t)\n  i = 0\n  si t es nulo\n    t = nuevo_nodo\n    t.izq = nulo\n    t.der = nulo\n    t.balance = 0\n  sino si t.clave = clave\n    // error: clave encontrada\n  sino\n    si t.clave < clave\n      i = insertar(clave, t.der)\n    sino\n      i = insertar(clave, t.izq)\n    fin si\n    t.balance += i\n    si (i no es 0 y t.balance no es cero)\n      si t.balance < -MAX_ALTURA\n        si t.izq.balance <= 0\n          rotar_derecha(t)\n        sino\n          rotar_izquierda(t.izq)\n          rotar_derecha(t)\n        fin si\n      si t.balance > MAX_ALTURA\n        si t.der.balance >= 0\n          rotar_izquierda(t)\n        sino\n          rotar_derecha(t.der)\n          rotar_izquierda(t)\n        fin si\n      sino\n        i = 1\n      fin si\n    fin si\n  fin si\n  devolver i\nfin funcion";

        @Override
        public String getCode() {
            return INSERT_CODE;
        }
    },
    delete() {

        private static final String DELETE_CODE = "funcion borrar(clave, subarbol t)\n  si t es nulo\n    // error: clave no encontrada\n  sino\n    // Busco la clave a ser borrada\n    si t.clave < clave\n      borrar(clave, t.der)\n    sino si t.clave > clave\n      borrar(clave, t.izq)\n    // Clave encontrada, borrarla si no hay descendientes\n    sino si t.izq es nulo\n      t = t.der\n    sino si t.der es nulo\n      t = t.izq\n    // Descendientes no nulos, rotar del lado m�s pesado\n    sino si altura(t.izq) > altura(t.der)\n      rotar_derecha(t)\n      borrar(clave, t,der)\n    sino\n      rotar_izquierda(t)\n      borrar(clave, t.izq)\n    si t no es nulo\n      t.balance = altura(t.der) - altura(t.izq)\n      chequear_rotaciones(t)\n    fin si\n  fin si\nfin funcion\n";

        @Override
        public String getCode() {
            return DELETE_CODE;
        }
    };

    public abstract String getCode();
}
