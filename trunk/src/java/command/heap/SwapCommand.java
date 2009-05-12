package command.heap;

import command.Command;

public class SwapCommand extends Command {
	private Integer idParent;
	private Integer idChild; 
	
	/**
	 *  Si true idChild es el id del elemento descendente y idParent, del descendente.
	 *  Si false, determina el caso opuesto.
	 */
	private boolean isSwapDown;
	
	private Integer childData;
	private Integer parentData;

	public SwapCommand(Integer idChild, Integer idParent, Integer childData, Integer parentData, boolean isSwapDown) {
		super(idChild);
		
		this.idChild = idChild;
		this.idParent = idParent;
		this.childData = childData;
		this.parentData = parentData;
		this.isSwapDown = isSwapDown;
	}

	@Override
	public String execute() {
		return "Promueve el elemento " + (isSwapDown ? idChild : idParent) + " con clave " + 
			(isSwapDown ? childData : parentData) + " y desciende el elemento " + (isSwapDown ? idParent: idChild) +
			" con clave " + (isSwapDown ? parentData: childData);
	}

	@Override
	public String undo() {
		return "Desciende el elemento " + (isSwapDown ? idParent : idChild) + " con clave " + 
		(isSwapDown ? parentData : childData) + " y promueve el elemento " + (isSwapDown ? idChild : idParent) +
		" con clave " + (isSwapDown ? childData : parentData);
	}
	
	public Integer getIdChild() {
		return idChild;
	}
	
	public Integer getIdParent() {
		return idParent;
	}
	
	public boolean isSwapDown() {
		return isSwapDown;
	}
}
