package command.tree;

import command.Command;

public class SwapCommand extends Command {

	private int higherElementId;
	private int lowerElementId;

	public SwapCommand(int higherElementId, int lowerElementId) {
		super(higherElementId);
		this.higherElementId = higherElementId;
		this.lowerElementId = lowerElementId;
	}

	@Override
	public String execute() {
		return "Intercambia el " + higherElementId + " con el "
				+ lowerElementId;
	}

	@Override
	public String undo() {
		return "Intercambia el " + lowerElementId + " con el "
				+ higherElementId;
	}

	public int getHigherElementId() {
		return higherElementId;
	}

	public int getLowerElementId() {
		return lowerElementId;
	}

}
