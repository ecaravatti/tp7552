/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.stacks.commands;

import view.commons.commands.Command;
import view.commons.elements.LinkableMobile;
import view.commons.elements.Mobile;

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
