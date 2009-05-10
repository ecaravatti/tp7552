package command;

public abstract class Command {
	private Integer id;
	private String content;
	
	public Command(Integer id, String content) {
		this.id = id;
		this.content = content;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public abstract String execute();
	public abstract String undo();
}
