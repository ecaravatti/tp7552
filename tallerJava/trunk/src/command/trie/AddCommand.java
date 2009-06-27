package command.trie;

import command.Command;

public class AddCommand extends Command {

	private String content;
	private Integer parentId;
	private Boolean hasKey;

	public AddCommand(Integer id, String content, Integer parenId,
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
		return "Add child node with id '" + getId() + "' and content '"
				+ getContent() + "' to node with id '" + parentId 
				+ hasKeyMessage();
	}

	@Override
	public String undo() {
		return "Remove child node with id '" + getId() + "' and content '"
		+ getContent() + "' from node with id '" + parentId + "'.";
	}
	
	private String hasKeyMessage() {
		return Boolean.TRUE.equals(hasKey) 
			? "'. This node has a key."
			: "'.";
	}

}
