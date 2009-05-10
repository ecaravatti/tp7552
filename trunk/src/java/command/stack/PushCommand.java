package command.stack;

import command.Command;

public class PushCommand extends Command {

	private String content;
	
	public PushCommand(Integer id, String content) {
		super(id);
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	@Override
	public String execute() {
		return "Push node with id '" + getId() + "' and content '" + getContent() + "'";
	}

	@Override
	public String undo() {
		return "Pop node with id '" + getId() + "' and content '" + getContent() + "'";
	}

}
