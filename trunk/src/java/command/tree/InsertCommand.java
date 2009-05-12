package command.tree;

import command.Command;

public class InsertCommand extends Command {
	
	private int insertedData;
	private boolean isLeftChild;
	private Integer parentId; //Puede ser null si es la raíz.
	private int balance;
	

	public InsertCommand(int insertedData, boolean isLeftChild, Integer parentData, int balance) {
		super(insertedData);
		this.insertedData = insertedData;
		this.isLeftChild = isLeftChild;
		this.parentId = parentData;
		this.balance = balance;
	}

	@Override
	public String execute() {
		return "Inserta el elemento " + insertedData + " como hijo " + (isLeftChild ? "izquierdo" : "derecho") + " de " + parentId;
	}

	@Override
	public String undo() {
		return "Remueve el elemento " + insertedData + " como hijo " + (isLeftChild ? "izquierdo" : "derecho") + " de " + parentId;
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
