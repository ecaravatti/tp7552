/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command.queue;

import view.command.common.Command;
import view.element.common.LinkableMobile;
import view.element.common.Mobile;

/**
 *
 * @author pgorin
 */
public class LinkMobilesCommand<T> implements Command {

    private LinkableMobile mobileToLink;
    private boolean isLinked;

    public LinkMobilesCommand(LinkableMobile mobileToLink, boolean isLinked) {
        this.mobileToLink = mobileToLink;
        this.isLinked = isLinked;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        if (isLinked) {
            this.mobileToLink.markAsLinked();
        } else {
            this.mobileToLink.markAsUnlinked();
        }
    }
}
