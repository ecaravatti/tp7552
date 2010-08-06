package view.command.common;

/**
 * Representa a un comando
 * 
 * 
 */
public interface Command {

	/**
	 * Determina si el comando puede ejecutarse
	 * 
	 * @return true si se puede ejecutar, false en caso contrario.
	 */
	public boolean canExecute();

	/**
	 * Executa el comando
	 */
	public void execute();

}
