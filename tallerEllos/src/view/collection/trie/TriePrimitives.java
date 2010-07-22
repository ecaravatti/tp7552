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

        private static final String INSERT_CODE = "funcion insertar(clave, dato)\n  NodoTrie nodo;\n  Si ( raiz == null )\n    raiz = crearNodoTrie(clave[0]);\n    insertarRecursivo(raiz, clave, 1, dato)\n  fin si\n  sino\n    nodo = crearNodoTrie();\n    node.hijo = raiz;\n    insertarRecursivo(nodo, clave, 0, dato)\n    raiz = nodo.hijo;\n  fin sino\nfin funcion\n\nfuncion insertarRecursivo(nodo, clave, indice, dato)\n  Si (indice >= clave.longitud)\n    Si (!nodo.esDato)\n      nodo.dato = crearNodoDato();\n    fin si\n    Sino\n      error_la_clave_ya_existe_en_el_trie();\n    fin sino\n  fin Si\n  nodo = agregarHijo( nodo, clave[indice] )\n  InsertarRecursivo( nodo, clave, indice + 1, dato);\nfin funcion";

        @Override
        public String getCode() {
            return INSERT_CODE;
        }
    },
    delete() {

        private static final String DELETE_CODE = "funcion eliminar(clave)\n  Si (raiz == null) error_el_arbol_esta_vacio() fin si Sino nodo = crearNodoTrie(); nodo.hijo = raiz; eliminarRecursivo(clave, 0, nodo); raiz = nodo.hijo; fin sino\nfin funcion\n\nfuncion eliminarRecursivo(clave, indice, nodo)\n  encontrado = true;\n  Si (indice > clave.longitud) error_la_clave_no_existe_en_el_trie(); hijo = buscarHijo( nodo, clave[indice] ); Si (hijo == null) error_la_clave_no_existe_en_el_trie(); eliminarRecursivo(clave, indice + 1, hijo); Si (indice == clave.longitud)\n    Si (!hijo.esDato )\n      error_la_clave_no_existe_en_el_trie();\n    fin si\n    Sino\n      hijo.dato = null;\n    fin sino\n  fin si\n  Si ( !formaParteDeOtraClave( hijo ) )\n    eliminarHijo(nodo, hijo)\n  fin si\nfin funcion";

        @Override
        public String getCode() {
            return DELETE_CODE;
        }
    };

    public abstract String getCode();
}
