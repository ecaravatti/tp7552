package command.tree;

import command.Command;

public class DeleteCommand extends Command {

	public Integer parentId;
	public int deletedValue;
	public Integer childId;
	public boolean isLeftChild;
	
	public static final String DELETE_COMMAND = "treeDeleteCommand";

	public DeleteCommand(int deletedId, int deletedValue, Integer parentId,
			Integer childId, boolean isLeftChild) {
		super(deletedId, DELETE_COMMAND);
		this.deletedValue = deletedValue;
		this.parentId = parentId;
		this.childId = childId;
		this.isLeftChild = isLeftChild;
	}

	@Override
	public String execute() {
		if (parentId == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Borro la raíz (elemento " + deletedValue + ")");
			if (childId != null) {
				buffer.append(". El elemento " + childId + " es la nueva raíz");
			}
			return buffer.toString();
		} else {
			return "Borro el elemento " + deletedValue;
		}
	}

	@Override
	public String undo() {
		if (parentId == null) {
			return "Deshago el borrado de la raíz (elemento " + deletedValue
					+ ")";
		} else {
			return "Deshago el borrado del elemento " + deletedValue;
		}
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
