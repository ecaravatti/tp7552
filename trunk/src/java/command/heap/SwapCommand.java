package command.heap;

import command.Command;
import common.Element;

public class SwapCommand<T extends Comparable<T>> extends Command {
	/**
	 * Id interno del elemento primario a ser intercambiado.
	 */
	private Integer primaryId;

	/**
	 * Id interno del elemento secundario a ser intercambiado.
	 */
	private Integer secondaryId;

	/**
	 * Si true idPrimary es el id del elemento que desciende. Si false,
	 * idPrimary es el id del elemento que es promovido.
	 */
	private boolean isSwapDown;

	/**
	 * Valor del elemento primario a ser intercambiado.
	 */
	private T primaryData;

	/**
	 * Valor del elemento secundario a ser intercambiado.
	 */
	private T secondaryData;

	/**
	 * Construye un comando de intercambio de elementos.
	 * 
	 * @param primaryId
	 *            Id específico del elemento primario a ser intercambiado.
	 * @param primaryData
	 *            Valor del elemento primario a ser intercambiado.
	 * @param secondaryId
	 *            Id específico del elemento secundario a ser intercambiado.
	 * @param secondaryData
	 *            TODO
	 * @param isSwapDown
	 *            Si true <code>primaryId</code> es el id del elemento que
	 *            desciende. Si false, <code>primaryId</code> es el id del
	 *            elemento que es promovido.
	 */
	public SwapCommand(Integer primaryId, T primaryData, Integer secondaryId,
			T secondaryData, boolean isSwapDown) {
		super(primaryId);

		this.primaryId = primaryId;
		this.secondaryId = secondaryId;
		this.primaryData = primaryData;
		this.secondaryData = secondaryData;
		this.isSwapDown = isSwapDown;
	}

	/**
	 * Construye un comando de intercambio de elementos.
	 * 
	 * @param primary
	 *            Elemento primario a ser intercambiado.
	 * @param secondary
	 *            Elemento secundario a ser intercambiado.
	 * @param isSwapDown
	 *            Si true idPrimary es el id del elemento que desciende. Si
	 *            false, idPrimary es el id del elemento que es promovido.
	 */
	public SwapCommand(Element<T> primary, Element<T> secondary, boolean isSwapDown) {
		this(primary.getId(), primary.getValue(), secondary.getId(), secondary
				.getValue(), isSwapDown);
	}

	@Override
	public String execute() {
		return "Promueve el elemento con clave " + (isSwapDown ? secondaryData : primaryData)
				+ " (id=" + (isSwapDown ? secondaryId : primaryId) + ")"
				+ " y desciende el elemento con clave " + (isSwapDown ? primaryData : secondaryData)
				+ " (id=" + (isSwapDown ? primaryId : secondaryId) + ")";
	}

	@Override
	public String undo() {
		return "Desciende el elemento con clave " + (isSwapDown ? secondaryData : primaryData)
				+ "(id=" + (isSwapDown ? secondaryId : primaryId) + ") " 
				+ " y promueve el elemento con clave " + (isSwapDown ? primaryData : secondaryData)
				+ "(id=" + (isSwapDown ? primaryId : secondaryId) + ")";
	}

	public Integer getIdChild() {
		return primaryId;
	}

	public Integer getIdParent() {
		return secondaryId;
	}

	public boolean isSwapDown() {
		return isSwapDown;
	}
}
