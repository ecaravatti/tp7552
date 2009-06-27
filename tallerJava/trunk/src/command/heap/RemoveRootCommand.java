package command.heap;

import command.Command;
import common.Element;

public class RemoveRootCommand<T extends Comparable<T>> extends Command {

	/**
	 * Id interno del elemento raíz.
	 */
	private Integer rootId;

	/**
	 * Valor del elemento raíz a remover.
	 */
	private T rootData;

	/**
	 * Id interno del elemento a ocupar la posición de la raíz.
	 */
	private Integer newRootId;

	/**
	 * Valor del elemento a ocupar la posición de la raíz.
	 */
	private T newRootData;

	/**
	 * Id del elemento padre al elemento a ocupar la posición de la raíz.
	 */
	private Integer parentId;

	/**
	 * Valor del elemento padre al elemento a ocupar la posición de la raíz.
	 */
	private T parentData;

	/**
	 * true si el elemento a ocupar la posición de la raíz es hijo izquierdo de
	 * su padre.
	 */
	private boolean isLeftChild;
	
	public static final String REMOVE_ROOT_COMMAND = "removeRoot";

	/**
	 * Construye un comando de eliminación de la raíz.
	 * 
	 * @param rootId
	 *            Id específico del elemento raíz a remover.
	 * @param rootData
	 *            Valor del elemento raíz a remover.
	 * @param newRootId
	 *            Id específico del elemento a ser promovido como raíz.
	 * @param newRootData
	 *            Valor del elemento a ser promovido como raíz.
	 * @param parentId
	 *            Id específico del elemento padre actual al elemento a ser
	 *            promovido como raíz.
	 * @param parentData
	 *            Valor del elemento padre al elemento a ser promovido como
	 *            raíz.
	 * @param isLeftChild
	 *            true si el elemento a ser promovido como raíz es, actualmente,
	 *            hijo izquierdo de su padre.
	 */
	public RemoveRootCommand(Integer rootId, T rootData, Integer newRootId,
			T newRootData, Integer parentId, T parentData, boolean isLeftChild) {
		super(rootId, REMOVE_ROOT_COMMAND);

		this.rootId = rootId;
		this.rootData = rootData;
		this.newRootId = newRootId;
		this.newRootData = newRootData;
		this.parentId = parentId;
		this.parentData = parentData;
		this.isLeftChild = isLeftChild;
	}

	/**
	 * Construye un comando de eliminación de la raíz.
	 * 
	 * @param root
	 *            Elemento raíz a ser removido.
	 * @param newRoot
	 *            Elemento a ser promovido como raíz del Heap.
	 * @param newRootParent
	 *            Elemento padre a <code>newRoot</code>.
	 * @param isLeftChild
	 *            true si el elemento a ser promovido como raíz es, actualmente,
	 *            hijo izquierdo de su padre.
	 */
	public RemoveRootCommand(Element<T> root, Element<T> newRoot,
			Element<T> newRootParent, boolean isLeftChild) {

		this(root.getId(), root.getValue(), newRoot.getId(),
				newRoot.getValue(), newRootParent.getId(), newRootParent
						.getValue(), isLeftChild);

	}

	@Override
	public String execute() {
		return "Remueve la raíz con clave " + rootData + " (id=" + rootId
				+ ") y es reemplazada por el elemento con clave "
				+ newRootData + " (id=" + newRootId + "), hijo "
				+ (isLeftChild ? "izquierdo" : "derecho")
				+ " del elememento con clave " + parentData + " (id="
				+ parentId + ")";
	}

	@Override
	public String undo() {
		return "Retorna el elemento con clave " + newRootData + " (id="
				+ newRootId + ") " + " como hijo "
				+ (isLeftChild ? "izquierdo" : "derecho")
				+ " del elememento con clave " + parentData + " (id="
				+ parentId + ")" + "y es creada la raíz con clave " + rootData
				+ " (id=" + rootId + ")";
	}

	public Integer getRootId() {
		return rootId;
	}

	public Integer getNewRootId() {
		return newRootId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public boolean isLeftChild() {
		return isLeftChild;
	}

}
