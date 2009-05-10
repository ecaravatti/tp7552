package command.queue;

import command.Command;

public class OfferCommand extends Command {

	String content;
	
	public OfferCommand(Integer id, String content) {
		super(id);
		this.content = content;
	}
	
	public String getContent() {
		return content;
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
