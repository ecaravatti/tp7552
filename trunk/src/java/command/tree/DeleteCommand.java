package command.tree;

import command.Command;

public class DeleteCommand extends Command {

	private Integer parentId;
	private int deletedValue;
	private Integer childId;

	private boolean isLeftChild;

	public DeleteCommand(int deletedId, int deletedValue, Integer parentId,
			Integer childId, boolean isLeftChild) {
		super(deletedId);
		this.deletedValue = deletedValue;
		this.parentId = parentId;
		this.childId = childId;
		this.isLeftChild = isLeftChild;
	}

	@Override
	public String execute() {
		return "Borro el elemento " + deletedValue;
	}

	@Override
	public String undo() {
		return "Deshago el borrado del elemento " + deletedValue;
	}

	public Integer getParentId() {
		return parentId;
	}

	public int getDeletedValue() {
		return deletedValue;
	}

	public Integer getChildId() {
		return childId;
	}

	public boolean isLeftChild() {
		return isLeftChild;
	}

}
