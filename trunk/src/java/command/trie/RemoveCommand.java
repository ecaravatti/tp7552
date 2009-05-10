package command.trie;

import command.Command;

public class RemoveCommand extends Command {

	private Integer parentId;
	private Boolean hasKey;

	public RemoveCommand(Integer id, String content, Integer parenId,
			Boolean hasKey) {

		super(id, content);
		this.parentId = parenId;
		this.hasKey = hasKey;
	}

	@Override
	public String execute() {
		return "Remove child node with id '" + getId() + "' and content '"
		+ getContent() + "' from node with id '" + parentId + "'.";
	}

	@Override
	public String undo() {
		return "Add child node with id '" + getId() + "' and content '"
		+ getContent() + "' to node with id '" + parentId 
		+ hasKeyMessage();
	}
	
	private String hasKeyMessage() {
		return Boolean.TRUE.equals(hasKey) 
			? "'. This node has a key."
			: "'.";
	}

}
