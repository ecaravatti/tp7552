package command.queue;

import command.Command;

public class PollCommand extends Command {

	private String content;
	
	public PollCommand(Integer id, String content) {
		super(id);
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	@Override
	public String execute() {
		return "Desencola el elemento '" + getId() + "' cuyo valor es '"
		+ getContent() + "'";
	}

	@Override
	public String undo() {
		return "Encola el elemento'" + getId() + "' cuyo valor es '"
		+ getContent() + "'";
	}

}
