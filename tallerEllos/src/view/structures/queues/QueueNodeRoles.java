/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.queues;

/**
 *
 * @author pgorin
 */
public enum QueueNodeRoles {

    head() {

        private static final String HEAD_ROLE = "head";

        @Override
        public String getRoleName() {
            return HEAD_ROLE;
        }
    },
    tail() {

        private static final String TAIL_ROLE = "tail";

        @Override
        public String getRoleName() {
            return TAIL_ROLE;
        }
    };

    @Override
    public String toString() {
        return this.getRoleName();
    }

    public abstract String getRoleName();
}
