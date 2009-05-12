package command.heap;

import command.Command;

public class InsertCommand extends Command {
	
	private Integer insertedId;
	private Integer insertedData;
	private boolean isLeftChild;
	private Integer parentId; // null si es el primer elemento a insertar (raíz del Heap).
	
	public InsertCommand(Integer insertedId, Integer insertedData, boolean isLeftChild, Integer parentId) {
		super(insertedId);
		
		this.insertedId = insertedId;
		this.insertedData = insertedData;
		this.isLeftChild = isLeftChild;
		this.parentId = parentId;
	}

	@Override
	public String execute() {
		return (parentId == null) ? "Inserta como raíz el elemento " + insertedId + " con clave " + insertedData : 
			"Inserta el elemento " + insertedId + " con clave " + insertedData + " como hijo " + (isLeftChild ? "izquierdo" : "derecho") + " de " + parentId;
	}

	@Override
	public String undo() {
		return (parentId == null) ? "Remueve el elemento " + insertedId + ", raíz con clave " + insertedData :
			"Remueve el elemento " + insertedId + " con clave " + insertedData + " como hijo " + (isLeftChild ? "izquierdo" : "derecho") + " de " + parentId;
	}

	public Integer getInsertedId() {
		return insertedId;
	}
	
	public Integer getInsertedData() {
		return insertedData;
	}
	
	public boolean isLeftChild() {
		return isLeftChild;
	}
	
	public Integer getParentId() {
		return parentId;
	}
}