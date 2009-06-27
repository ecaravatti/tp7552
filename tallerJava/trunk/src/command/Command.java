package command;

public abstract class Command {
	public Integer id;
	public String type;
	
	//TODO Constructor dummy (eliminar cuando todos los comandos
	// tengan su type definido.
	public Command(Integer id){
		this(id, null);
	}
	
	public Command(Integer id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public Integer getId() {
		return id;
	}
	
	public abstract String execute();
	public abstract String undo();
}
