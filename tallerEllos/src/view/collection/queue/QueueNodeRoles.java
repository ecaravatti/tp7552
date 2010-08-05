/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;

/**
 *
 * 
 */
public enum QueueNodeRoles {

    head() {

        private static final String HEAD_ROLE = "front";

        @Override
        public String getRoleName() {
            return HEAD_ROLE;
        }
    },
    tail() {

        private static final String TAIL_ROLE = "back";

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
