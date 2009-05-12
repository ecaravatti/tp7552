package command.heap;

import command.Command;

public class SwapCommand extends Command {
	private Integer idSecondary;
	private Integer idPrimary; 
	
	/**
	 *  Si true idPrimary es el id del elemento que desciende.
	 *  Si false, idPrimary es el id del elemento que es promovido.
	 */
	private boolean isSwapDown;
	
	private Integer primaryData;
	private Integer secondaryData;

	public SwapCommand(Integer idPrimary, Integer idSecondary, Integer primaryData, Integer secondaryData, boolean isSwapDown) {
		super(idPrimary);
		
		this.idPrimary = idPrimary;
		this.idSecondary = idSecondary;
		this.primaryData = primaryData;
		this.secondaryData = secondaryData;
		this.isSwapDown = isSwapDown;
	}

	@Override
	public String execute() {
		return "Promueve el elemento " + (isSwapDown ? idSecondary : idPrimary) + " con clave " + 
			(isSwapDown ? secondaryData : primaryData) + " y desciende el elemento " + (isSwapDown ? idPrimary : idSecondary) +
			" con clave " + (isSwapDown ? primaryData : secondaryData);
	}

	@Override
	public String undo() {
		return "Desciende el elemento " + (isSwapDown ? idSecondary : idPrimary) + " con clave " + 
		(isSwapDown ? secondaryData : primaryData) + " y promueve el elemento " + (isSwapDown ? idPrimary : idSecondary) +
		" con clave " + (isSwapDown ? primaryData : secondaryData);
	}
	
	public Integer getIdChild() {
		return idPrimary;
	}
	
	public Integer getIdParent() {
		return idSecondary;
	}
	
	public boolean isSwapDown() {
		return isSwapDown;
	}
}
