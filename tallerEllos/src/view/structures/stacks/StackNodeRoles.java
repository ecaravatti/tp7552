/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.stacks;

/**
 *
 * @author pgorin
 */
public enum StackNodeRoles {

    top() {

        private static final String TOP_ROLE = "top";

        @Override
        public String getRoleName() {
            return TOP_ROLE;
        }
    },
    bottom() {

        private static final String BOTTOM_ROLE = "bottom";

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
