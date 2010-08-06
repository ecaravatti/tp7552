/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command.queue;

import view.command.common.Command;
import view.element.common.LinkableMobile;

/**
 *
 */
public class LinkMobilesCommand implements Command {

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
