package command;

public class HighlightCommand extends Command {

	public String content;
	
	public HighlightCommand(Integer id, String content) {
		super(id);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public String execute() {
		return "Resalto el nodo con id '" + getId() + "' y cuyo contenido es'"
				+ getContent() + "'";
	}

	@Override
	public String undo() {
		return execute();
	}

}
