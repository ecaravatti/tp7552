package command.tree;

import command.Command;

public class ChangeWeightCommand extends Command {

	private int nodeValue;
	private int oldWeight;
	private int newWeight;

	public ChangeWeightCommand(Integer nodeId, int nodeValue, int oldWeight, int newWeight) {
		super(nodeId);
		this.nodeValue = nodeValue;
		this.oldWeight = oldWeight;
		this.newWeight = newWeight;
	}

	@Override
	public String execute() {
		return "Cambia el peso del elemento " + nodeValue + " de " + oldWeight + " a " + newWeight;
	}

	@Override
	public String undo() {
		return "Cambia el peso del elemento " + nodeValue + " de " + newWeight + " a " + oldWeight;
	}

	public int getNewWeight() {
		return newWeight;
	}

	public int getOldWeight() {
		return oldWeight;
	}

	public int getNodeValue() {
		return nodeValue;
	}

}
