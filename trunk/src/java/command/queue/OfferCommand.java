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
		return "Encola el elemento '" + getId() + "' cuyo valor es '"
				+ getContent() + "'";
	}

	@Override
	public String undo() {
		return "Desencola el elemento '" + getId() + "' cuyo valor es '"
		+ getContent() + "'";
	}

}
