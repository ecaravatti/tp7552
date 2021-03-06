package command.trie;

import command.Command;

public class RemoveCommand extends Command {

	public String content;
	public Integer parentId;
	public Boolean hasKey;

	public RemoveCommand(Integer id, String content, Integer parenId,
			Boolean hasKey) {

		super(id);
		this.content = content;
		this.parentId = parenId;
		this.hasKey = hasKey;
	}

	public String getContent() {
		return content;
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
