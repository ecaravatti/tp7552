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
		return "Inserta el elemento '" + getId() + "' cuyo valor es '" + getContent() + " en el tope de la pila'";
	}

	@Override
	public String undo() {
		return "Quita el elemento '" + getId() + "' cuyo valor es '" + getContent() + " del tope de la pila";
	}

}
