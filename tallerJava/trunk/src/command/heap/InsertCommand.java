package command.heap;

import command.Command;
import common.Element;

public class InsertCommand<T extends Comparable<T>> extends Command {

	/**
	 * Id interno del elemento a insertar.
	 */
	private Integer insertedId;

	/**
	 * Valor del elemento a insertar.
	 */
	private T insertedData;

	/**
	 * Id interno del elemento padre al elemento a insertar. Si null, el
	 * elemento a insertar es la raíz del Heap.
	 */
	private Integer parentId;

	/**
	 * Valor del elemento padre al elemento a insertar.
	 */
	private T parentData;

	/**
	 * true si el elemento a insertar es hijo izquierdo de su padre. Si
	 * <code>parentId == null</code>, se ignora.
	 */
	private boolean isLeftChild;

	/**
	 * Construye un comando de inserción.
	 * 
	 * @param insertedId
	 *            Id específico del elemento a insertar.
	 * @param insertedData
	 *            Valor del elemento a insertar
	 * @param parentId
	 *            Id específico del elemento padre al elemento a insertar.
	 * @param parentData
	 *            Valor del elemento padre al elemento a insertar.
	 * @param isLeftChild
	 *            true si el elemento a insertar es hijo izquierdo de su padre.
	 *            Si <code>parentId == null</code>, se ignora.
	 */
	public InsertCommand(Integer insertedId, T insertedData, Integer parentId,
			T parentData, boolean isLeftChild) {
		super(insertedId);

		this.insertedId = insertedId;
		this.insertedData = insertedData;
		this.parentId = parentId;
		this.parentData = parentData;
		this.isLeftChild = isLeftChild;
	}

	/**
	 * Construye un comando de inserción.
	 * 
	 * @param inserted
	 *            Elemento a ser insertado.
	 * @param parent
	 *            Elemento padre al elemento a ser insertado. Si es
	 *            <code>null</code>, inserted representa al elemento raíz del
	 *            Heap.
	 * @param isLeftChild
	 *            true si el elemento a insertar es hijo izquierdo de su padre.
	 *            Si <code>parentId == null</code>, se ignora.
	 */
	public InsertCommand(Element<T> inserted, Element<T> parent,
			boolean isLeftChild) {
		this(inserted.getId(), inserted.getValue(), parent == null ? null
				: parent.getId(), parent == null ? null : parent.getValue(),
				isLeftChild);
	}

	@Override
	public String execute() {
		return (parentId == null) ? "Inserta como raíz el elemento con clave "
				+ insertedData + " (id=" + insertedId + ")"
				: "Inserta el elemento con clave " + insertedData + " (id="
						+ insertedId + ") como hijo "
						+ (isLeftChild ? "izquierdo" : "derecho")
						+ " del elemento con clave " + parentData + " (id="
						+ parentId + ")";
	}

	@Override
	public String undo() {
		return (parentId == null) ? "Remueve el elemento raíz con clave "
				+ insertedData + " (id=" + insertedId + ")"
				: "Remueve el elemento con clave" + insertedData + "(id="
						+ insertedId + ") como hijo "
						+ (isLeftChild ? "izquierdo" : "derecho")
						+ " del elemento con clave " + parentData + " (id="
						+ parentId + ")";
	}

	public Integer getInsertedId() {
		return insertedId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public boolean isLeftChild() {
		return isLeftChild;
	}

}