package view.command.common;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 
 */
public class CommandQueue implements Runnable {
	private LinkedList<Command> queue;
	private Command defaultCommand;
	private int delay;
	private boolean stop;
	private boolean wait;
	private Thread thread;

	/**
	 * @param delay
	 *            retardo con que se ejecutan los comandos
	 * @param defautlCommand
	 *            tarea que se ejecuta por defecto (si no hay otras tareas).
	 */
	public CommandQueue(int delay, Command defautlCommand) {
		super();
		this.queue = new LinkedList<Command>();
		this.delay = delay;
		this.defaultCommand = defautlCommand;
		this.stop = false;
		this.wait = false;
		this.thread = null;
	}

	/**
	 * @param delay
	 *            retardo con que se ejecutan los comandos
	 */
	public CommandQueue(int delay) {
		super();
		this.queue = new LinkedList<Command>();
		this.delay = delay;
		this.defaultCommand = null;
		this.stop = false;
		this.thread = null;
	}

	/**
	 * Agrega un comando a ser ejecutado
	 * 
	 * @param command
	 *            nuevo comando a ser ejecutado
	 */
	public synchronized void addCommand(Command command) {
		this.queue.addFirst(command);
	}

	/**
	 * Agrega una lista de comandos a ser ejecutados
	 * 
	 * @param commands
	 *            comandos a ser ejecutados
	 */
	public synchronized void addCommands(List<Command> commands) {

		for (Command command : commands)
			this.queue.addFirst(command);
	}

	/**
	 * Determina que los comandos pasados como parametro deben ser ejecutados
	 * inmediatamente.
	 * 
	 * @param commands
	 *            comandos a ser ejecutados inmediatamente.
	 */
	public synchronized void executeImmediate(List<Command> commands) {
		this.queue.addAll(commands);
	}

	/**
	 * Determina que los comandos pasados como parametro deben ser ejecutados
	 * inmediatamente.
	 * 
	 * @param commands
	 *            comandos a ser ejecutados inmediatamente.
	 */
	public synchronized void executeImmediate(Command commands) {
		this.queue.add(commands);
	}

	/**
	 * Inicia la ejecucion del thread
	 */
	public synchronized void start() {

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		Command command;
		long time = System.currentTimeMillis();

		while (!isStopped()) {

			synchronized (this) {

				if (queue.size() == 0 || this.wait)
					command = defaultCommand;
				else {
					command = queue.getLast();

					if (!command.canExecute()) {
						Command firstCommand = command;
						moveToFront(command);

						while (!command.canExecute()) {
							if (command == firstCommand)
								break;
							command = moveToFront(command);
						}

						if (command.canExecute())
							queue.removeLast();
						else
							command = this.defaultCommand;
					} else
						queue.removeLast();
				}

			}

			if (command != null)
				command.execute();
			try {
				time += delay;
				Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Vacia la cola
	 */
	public synchronized void clear() {
		this.queue.clear();
	}

	/**
	 * Obtiene el comando por defecto que ejecuta el thread
	 * 
	 * @return el comando por defecto.
	 */
	public synchronized Command getDefaultCommand() {
		return defaultCommand;
	}

	/**
	 * Cambia el comando por defecto que ejecuta el thread
	 * 
	 * @param defaultCommand
	 *            nuevo comando que se ejecutara por defecto
	 */
	public synchronized void setDefaultCommand(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
	}

	/**
	 * Elimina el comando que se ejecuta por defecto
	 */
	public synchronized void removeDefaultCommand() {
		this.defaultCommand = null;
	}

	/**
	 * Obtiene el delay
	 * 
	 * @return el tiempo transcurrido entre la ejecucion de los comandos
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Cambia el retardo entre comandos.
	 * 
	 * @param delay
	 *            nuevo retardo
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * Determina si el thread esta detenido
	 * 
	 * @return true si el thread esta detenido, false en caso contrario.
	 */
	public synchronized boolean isStopped() {
		return stop;
	}

	/**
	 * Detiene el thread.
	 */
	public synchronized void stop() {
		this.stop = true;
	}

	/**
	 * Indica si los comandos en ejecucion deben esperar (o no)
	 * 
	 * @param w
	 *            true si los comandos deben esperar, false en caso contrario.
	 */
	public synchronized void wait(boolean w) {
		this.wait = w;
	}

	private Command moveToFront(Command command) {
		queue.removeLast();
		queue.addFirst(command);
		return queue.getLast();
	}
}
