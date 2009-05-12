package command.heap;

import command.Command;

public class RemoveRootCommand extends Command {

	private Integer rootId;
	private Integer rootData;
	private Integer newRootId;
	private Integer newRootData;
	private Integer parentId;
	private Integer parentData;
	private boolean isLeftChild;
	
	public RemoveRootCommand(Integer rootId, Integer rootData, 
			Integer newRootId, Integer newRootData,
			Integer parentId, Integer parentData, boolean isLeftChild) {
		super(rootId);
		
		this.rootId = rootId;
		this.rootData = rootData;
		this.newRootId = newRootId;
		this.newRootData = newRootData;
		this.parentId = parentId;
		this.parentData = parentData;
		this.isLeftChild = isLeftChild;
	}

	@Override
	public String execute() {
		return "Remueve la raíz " + rootId + " con clave " + rootData + " y es reemplazada por " +
		"el elemento " + newRootId + " con clave " + newRootData + ", hijo " +  (isLeftChild? "izquierdo":"derecho") +
		" del elememento " + parentId + " con clave " + parentData;
	}

	@Override
	public String undo() {
		return "Retorna el elemento " + newRootId + " con clave " + newRootData + " como hijo " +
		(isLeftChild? "izquierdo":"derecho") + " del elememento " + parentId + " con clave " + parentData +
		" y es creada la raíz " + rootId + " con clave " + rootData;
	}

}
