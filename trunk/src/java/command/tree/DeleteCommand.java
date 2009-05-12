package command.tree;

import command.Command;

public class DeleteCommand extends Command {
	
	private Integer parentId;
	private int deletedId;
	private Integer childId;
	
	private boolean isLeftChild;
	
	public DeleteCommand(int deletedId, Integer parentId, Integer childId, boolean isLeftChild) {
		super(deletedId);
		this.deletedId = deletedId;
		this.parentId = parentId;
		this.childId = childId;
		this.isLeftChild = isLeftChild;
	}
	
	@Override
	public String execute() {
		return "Borro el elemento " + deletedId;
	}

	@Override
	public String undo() {
		return "Deshago el borrado del elemento " + deletedId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public int getDeletedId() {
		return deletedId;
	}

	public Integer getChildId() {
		return childId;
	}

	public boolean isLeftChild() {
		return isLeftChild;
	}

}
