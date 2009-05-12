package command.tree;

import command.Command;

public class NodeHighlightCommand extends Command {
	
	private Integer highlightElementId;
	private Integer unhighlightElementId;
	
	public NodeHighlightCommand(Integer highlightElementId, Integer unhighlightElementId) {
		super(highlightElementId);
		this.highlightElementId = highlightElementId;
		this.unhighlightElementId = unhighlightElementId;
	}
	
	@Override
	public String execute() {
		return "Resalto elemento " + highlightElementId + " y dejo de resaltar el elemento " + unhighlightElementId;
	}

	@Override
	public String undo() {
		return "Resalto elemento " + unhighlightElementId + " y dejo de resaltar el elemento " + highlightElementId;
	}

	public Integer getHighlightElementId() {
		return highlightElementId;
	}

	public Integer getUnhighlightElementId() {
		return unhighlightElementId;
	}

}
