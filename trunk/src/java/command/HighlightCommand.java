package command;

public class HighlightCommand extends Command {

	private String content;
	
	public HighlightCommand(Integer id, String content) {
		super(id);
		this.content = content;
	}

	public String getContent() {
		return content;
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
