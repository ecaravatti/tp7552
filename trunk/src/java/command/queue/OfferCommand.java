package command.queue;

import command.Command;

public class OfferCommand extends Command {

	public OfferCommand(Integer id, String content) {
		super(id, content);
	}

	@Override
	public String execute() {
		return "Offer node with id '" + getId() + "' and content '"
				+ getContent() + "'";
	}

	@Override
	public String undo() {
		return "Poll node with id '" + getId() + "' and content '"
		+ getContent() + "'";
	}

}
