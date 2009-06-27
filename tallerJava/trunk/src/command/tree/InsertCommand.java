package command.tree;

import command.Command;

public class InsertCommand extends Command {

	private int insertedData;
	private boolean isLeftChild;
	private Integer parentId; // Puede ser null si es la raíz.
	private int balance;

	public InsertCommand(int insertedData, boolean isLeftChild,
			Integer parentId, int balance) {
		super(insertedData);
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
