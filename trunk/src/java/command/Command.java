package command;

public abstract class Command {
	private Integer id;
	
	public Command(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public abstract String execute();
	public abstract String undo();
}
