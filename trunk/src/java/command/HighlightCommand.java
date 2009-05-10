package command;

public class HighlightCommand extends Command {

	public HighlightCommand(Integer id, String content) {
		super(id, content);
	}

	@Override
	public String execute() {
		return "Highlight node with id '" + getId() + "' and content '"
				+ getContent() + "'";
	}

	@Override
	public String undo() {
		return execute();
	}

}
