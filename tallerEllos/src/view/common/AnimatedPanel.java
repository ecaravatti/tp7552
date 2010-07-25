package view.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

import view.animation.common.AnimationSteps;
import view.animation.common.UndoAnimationSteps;
import view.command.common.Command;
import view.command.common.CommandQueue;
import view.command.common.CreateAnimationCommand;
import view.command.common.PaintCommand;
import view.exception.common.CannotUndoException;
import controller.InteractiveController;

/**
 * Panel para la vista donde se realizan las animaciones
 *
 */
public abstract class AnimatedPanel extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;
	
	private static int MIN_DELAY = 50;
    private static int MAX_DELAY = 100;
    private static int DELAY = 60;
    private static double DEF_DELTA = 8;
    private static int DEF_DELAY = 3;
    private static int DEF_PAUSE = DEF_DELAY * 3;

    private CommandQueue commandQueue;
    protected InteractiveController controller;
    protected Stack<UndoAnimationSteps> stack;
    //private List<UndoAnimationSteps> trieNodeNotFoundAnimations;
    private boolean primitiveFinished;
    private boolean executingUndo;
    private boolean wait;
    private int currentAnimation;
    private int maxAnimations;
    private int countAnimations;

    private Graphics2D graphics;
    private BufferedImage image;
    protected boolean resized;

    public AnimatedPanel() {
        super(true);
        setDoubleBuffered(true);
        setBackground(Color.WHITE);
        
        addComponentListener(this);
        resized = false;
        primitiveFinished = true;
        wait = false;
        executingUndo = false;
        
        commandQueue = new CommandQueue(DELAY);//, new PaintCommand(this));
        this.graphics = null;
        this.stack = new Stack<UndoAnimationSteps>();
        this.controller = null;
    }

    /**
     * Actualiza el tamaño del doble buffer, cuando cambia de tamaño el Panel
     * @param e
     */
    @Override
    public void componentResized(ComponentEvent e) {
        resized = true;
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    /**
     * Agrega un controlador a la vista
     * @param controller controlador a agregar
     */
    public void addController(InteractiveController controller) {
        this.controller = controller;
    }

    public InteractiveController getController() {
        return controller;
    }

    public double getDelta(){
      return DEF_DELTA;
    }

    /**
     * Obtiene el tamano de la pausa.
     */
    private int getPause() {
        return DEF_PAUSE;
    }

    public int getDelayValue() {
        double value = (commandQueue.getDelay() - MIN_DELAY) / ((double)(MAX_DELAY - MIN_DELAY));
        return new Double((1 - value) * 100).intValue();
    }

    public List<Command> getPaintCommands() {
        List<Command> list = new ArrayList<Command>();

        for (int i = 1; i < getPause(); i++) {
            list.add(new PaintCommand(this));
        }

        return list;
    }

    /**
     * Cambiar velocidad
     * @param speed nueva velocidad
     */
    public void changeSpeed(int speed) {
        double delay = ((100 - speed)/100.0f) * ((double)(MAX_DELAY - MIN_DELAY));
        delay += (double)MIN_DELAY;
        System.out.println(delay);
        commandQueue.setDelay(new Double(delay).intValue());
    }

    /**
     * Obtiene la pausa en el parpadeo para todos los elementos de esta vista.
     * @return pausa en el parpadeo
     */
    public int getFlashingDelay() {
        return DEF_DELAY;
    }

    /**
     * Indica que la animacion debe detenerse (o reanudarse)
     * @param w true para detener, false en caso contrario.
     */
    public void wait(boolean w) {
        this.commandQueue.wait(w);
    }

    /**
     * Permite que se ejecute la siguiente animacion.
     */
    public void nextStep() {
        this.wait(false);
        if (primitiveFinished) {
            controller.primitiveFinished();
        }
    }

    /**
     * Agrega una animacion que ya fue ejecutada
     * @param animation animacion a agregar
     */
    public void addExecutedAnimationSteps(UndoAnimationSteps animation) {
        this.stack.add(animation);
    }

    /**
     * Agrega comandos a ejecutar inmediatamente
     * @param list lista de comando a ejecutar
     */
    public void addCommands(List<Command> list) {
        this.commandQueue.addCommands(list);
    }

    /**
     * Agrega un Comando a ejecutar inmediatamente
     * @param command
     */
    public void addCommand(Command command) {
        commandQueue.addCommand(command);
    }

    /**
     * Determina si una animacion puede ejecutarse
     * @param numberAnimation numero de animacion.
     * @return true si puede ejecutarse, false en caso contrario.
     */
    public boolean canExecuteAnimation(int numberAnimation) {
        return (this.currentAnimation == numberAnimation && !wait);
    }

    /**
     * Avanza a la proxima animacion a ejecutar
     */
    public void nextAnimation() {
        this.currentAnimation++;
    }

    public void setPrimitiveFinished(boolean finished) {
        this.primitiveFinished = finished;
    }

    public boolean isPrimitiveFinished() {
        return primitiveFinished;
    }

    public int getMaxAnimations() {
        return this.maxAnimations;
    }

    /**
     * Inicia la animacion.
     * Debe ser invocado en el Main
     */
    public void start() {
        commandQueue.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (resized) {
            image = (BufferedImage) createImage(getSize().width, getSize().height);
            graphics = image.createGraphics();
            resized = false;
        }

        if (graphics == null) {
            return;
        }

        super.paintComponent(graphics);
        graphics.clearRect(0, 0, getSize().width, getSize().height);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintPanel(graphics);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, this);
    }

    /**
     * Pinta los elementos contenidos en el panel
     * @param g
     */
    protected abstract void paintPanel(Graphics2D g);

    /**
     * Ejecuta el ultimo paso que puede deshacerse
     * @throws CannotUndoException
     */
    public void undoLastStep() throws CannotUndoException {
        try {
            UndoAnimationSteps animation = stack.pop();
            this.primitiveFinished = false;
            this.currentAnimation--;
            this.executingUndo = true;
            List<Command> list = animation.getSteps();
            Collections.reverse(list);
            List<Command> listUndo = animation.getUndoSteps();
            Collections.reverse(listUndo);
            list.addAll(listUndo);
            this.commandQueue.executeImmediate(list);
            this.wait(false);
        } catch (java.util.EmptyStackException e) {
            controller.showLogMessage("No hay operaciones para deshacer...");
            throw new CannotUndoException();
        }

    }

    /**
     * Debe ser invocado por el controlador ANTES
     * de una accion en el modelo
     */
    public void prepareAnimation() {
        primitiveFinished = false;
        currentAnimation = 0;
        countAnimations = 0;
        stack.clear();
        this.wait(false);
    }

    /**
     * Agrega una Animacion a la cola de Comandos para ser procesado en orden
     * @param animation
     */
    public void addAnimationToQueue(AnimationSteps animation) {
        commandQueue.addCommand(new CreateAnimationCommand(this, animation, countAnimations));
        this.countAnimations++;
    }

    /**
     * Agrega un Comando a la cola de Comandos para ser procesado en orden
     * @param command
     */
    public void addCommandToQueue(Command command) {
        commandQueue.addCommand(new CreateAnimationCommand(this, command, countAnimations));
        this.countAnimations++;
    }
    
    protected void rerender() {
		this.revalidate();
		this.repaint();
	}
    
    public CommandQueue getCommandQueue() {
        return commandQueue;
    }

    public boolean isExecutingUndo() {
      return executingUndo;
    }

    public void setExecutingUndo(boolean executingUndo) {
      this.executingUndo = executingUndo;
    }

    protected int getCountAnimations() {
      return countAnimations;
    }

    protected void setCountAnimations(int countAnimations) {
      this.countAnimations = countAnimations;
    }

    protected int getCurrentAnimation() {
      return currentAnimation;
    }

    protected void setCurrentAnimation(int currentAnimation) {
      this.currentAnimation = currentAnimation;
    }

    protected void setMaxAnimations(int maxAnimations) {
      this.maxAnimations = maxAnimations;
    }

    protected void setWait(boolean value){
      this.wait = value;
    }
}
