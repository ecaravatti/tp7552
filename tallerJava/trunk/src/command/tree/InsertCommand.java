package command.tree;

import command.Command;

public class InsertCommand extends Command {

	public int insertedData;
	public boolean isLeftChild;
	public Integer parentId; // Puede ser null si es la raíz.
	public int balance;

	public static final String INSERT_COMMAND = "treeInsertCommand";
	
	public InsertCommand(int insertedData, boolean isLeftChild,
			Integer parentId, int balance) {
		super(insertedData, INSERT_COMMAND);
		this.insertedData = insertedData;
		this.isLeftChild = isLeftChild;
		this.parentId = parentId;
		this.balance = balance;
	}

	@Override
	public String execute() {
		if (parentId == null) {
			return "Inserta el elemento " + insertedData + " como raíz";
		} else {
			return "Inserta el elemento " + insertedData + " como hijo "
					+ (isLeftChild ? "izquierdo" : "derecho") + " de "
					+ parentId;
		}
	}

	@Override
	public String undo() {
		if (parentId == null) {
			return "Remueve la raíz";
		} else {
			return "Remueve el elemento " + insertedData + " como hijo "
					+ (isLeftChild ? "izquierdo" : "derecho") + " de "
					+ parentId;
		}
	}

	public int getInsertedData() {
		return insertedData;
	}

	public Integer getParentId() {
		return parentId;
	}

	public int getBalance() {
		return balance;
	}

	public boolean isLeftChild() {
		return isLeftChild;
	}

}
