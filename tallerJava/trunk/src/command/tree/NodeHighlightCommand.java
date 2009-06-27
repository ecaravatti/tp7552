package command.tree;

import command.Command;

public class NodeHighlightCommand extends Command {

	private Integer highlightElementId;
	private Integer unhighlightElementId;

	public NodeHighlightCommand(Integer highlightElementId,
			Integer unhighlightElementId) {
		super(highlightElementId);
		this.highlightElementId = highlightElementId;
		this.unhighlightElementId = unhighlightElementId;
	}

	@Override
	public String execute() {
		StringBuffer buffer = new StringBuffer();
		if (highlightElementId != null) {
			buffer.append("Resalto elemento " + highlightElementId);
		}
		if (unhighlightElementId != null) {
			if (highlightElementId != null)
				buffer.append(". ");
			buffer.append("Dejo de resaltar el elemento "
					+ unhighlightElementId);
		}
		return buffer.toString();
	}

	@Override
	public String undo() {
		StringBuffer buffer = new StringBuffer();
		if (unhighlightElementId != null) {
			buffer.append("Resalto elemento " + unhighlightElementId);
		}
		if (highlightElementId != null) {
			if (unhighlightElementId != null)
				buffer.append(". ");
			buffer.append("Dejo de resaltar el elemento " + highlightElementId);
		}
		return buffer.toString();
	}

	public Integer getHighlightElementId() {
		return highlightElementId;
	}

	public Integer getUnhighlightElementId() {
		return unhighlightElementId;
	}

}
