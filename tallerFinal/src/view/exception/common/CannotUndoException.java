package view.exception.common;

/**
 * Esta excepcion debe arrojarse cuando no se puede deshacer una operacion.
 * 
 * 
 */
public class CannotUndoException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Construye una excepcion CannotUndoException
	 * 
	 * @param message
	 *            mensaje explicando la excepcion.
	 */
	public CannotUndoException(String message) {
		super(message);
	}

	/**
	 * Construye una excepcion CannotUndoException
	 */
	public CannotUndoException() {
		super();
	}

}
