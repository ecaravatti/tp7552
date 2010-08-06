/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.trie;

/**
 *
 * 
 */
public enum TriePrimitives {

    insert() {

        private static final String INSERT_CODE = "función insertar(clave, dato) {\n  NodoTrie nodo;\n  si ( raíz = null ) entonces {\n    raíz = crearNodoTrie(clave[0]);\n    insertarRecursivo(raíz, clave, 1, dato);\n  } sino {\n    nodo = crearNodoTrie();\n    node.hijo = raíz;\n    insertarRecursivo(nodo, clave, 0, dato);\n    raíz = nodo.hijo;\n  }\n}\n\nfunción insertarRecursivo(nodo, clave, índice, dato) {\n  si (índice >= clave.longitud) entonces {\n    si (!nodo.esDato) entonces {\n      nodo.dato = crearNodoDato();\n    } sino {\n      error \"la clave ya existe en el trie\";\n    }\n  }\n  nodo = agregarHijo( nodo, clave[índice] );\n  insertarRecursivo( nodo, clave, índice + 1, dato);\n}";

        @Override
        public String getCode() {
            return INSERT_CODE;
        }
    },
    delete() {

        private static final String DELETE_CODE = "función eliminar(clave) {\n  si (raíz = null) entonces {\n    error \"el trie está vacío\";\n  } sino {\n    nodo = crearNodoTrie();\n    nodo.hijo = raíz;\n    eliminarRecursivo(clave, 0, nodo);\n    raíz = nodo.hijo;\n  }\n}\n\nfunción eliminarRecursivo(clave, índice, nodo) {\n  encontrado = true;\n  si (índice > clave.longitud) entonces {\n    error \"la clave no existe en el trie\";\n  } sino {\n    hijo = buscarHijo( nodo, clave[índice] );\n    si (hijo = null) entonces {\n      error \"la clave no existe en el trie\";\n    } sino {\n      encontrado = encontrado && eliminarRecursivo(clave, índice + 1, hijo);\n      si (!encontrado || (índice = clave.longitud -1 && !hijo.esDato)) entonces {\n        error \"la clave no existe en el trie\";\n      } sino {\n        si (índice = clave.longitud -1) entonces {\n          hijo.dato = null;\n        }\n        si ( !formaParteDeOtraClave( hijo )) entonces {\n          eliminarHijo(nodo, hijo);\n        }\n      }\n    }\n  }\n}";

        @Override
        public String getCode() {
            return DELETE_CODE;
        }
    };

    public abstract String getCode();
}
