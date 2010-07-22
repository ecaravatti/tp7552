/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.tree;

/**
 *
 * 
 */
public enum BSTTraversePrimitives {

    inorder() {

        private static final String TRAVERSE_CODE = "funcion recorrer_inorder(subarbol t)\n  si t no es nulo\n    recorrer_inorder(t.izq)\n    // procesar t\n    recorrer_inorder(t.der)\n  fin si\nfin funcion";

        @Override
        public String getCode() {
            return TRAVERSE_CODE;
        }
    },
    preorder() {

        private static final String TRAVERSE_CODE = "funcion recorrer_preorder(subarbol t)\n  si t no es nulo\n    // procesar t\n    recorrer_preorder(t.izq)\n    recorrer_preorder(t.der)\n  fin si\nfin funcion";

        @Override
        public String getCode() {
            return TRAVERSE_CODE;
        }
    },
    postorder() {

        private static final String TRAVERSE_CODE = "funcion recorrer_postorder(subarbol t)\n  si t no es nulo\n    recorrer_postorder(t.izq)\n    recorrer_postorder(t.der)\n    // procesar t\n  fin si\nfin funcion";

        @Override
        public String getCode() {
            return TRAVERSE_CODE;
        }
    };

    public abstract String getCode();
}
