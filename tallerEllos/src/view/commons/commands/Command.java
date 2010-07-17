package view.commons.commands;

/**
 * Representa a un comando
 * 
 * @author Agustina Freije
 */
public interface Command {

  /**
   * Determina si el comando puede ejecutarse
   * @return true si se puede ejecutar, false en caso contrario.
   */
  public boolean canExecute();

  /**
   * Executa el comando
   */
  public void execute();

}
