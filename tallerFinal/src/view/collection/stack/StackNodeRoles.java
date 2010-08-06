/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.stack;

/**
 *
 * 
 */
public enum StackNodeRoles {

    top() {

        private static final String TOP_ROLE = "tope";

        @Override
        public String getRoleName() {
            return TOP_ROLE;
        }
    },
    bottom() {

        private static final String BOTTOM_ROLE = "final";

        @Override
        public String getRoleName() {
            return BOTTOM_ROLE;
        }
    };

    @Override
    public String toString() {
        return this.getRoleName();
    }

    public abstract String getRoleName();
}
