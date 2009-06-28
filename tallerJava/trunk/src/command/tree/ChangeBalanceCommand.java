package command.tree;

import command.Command;

public class ChangeBalanceCommand extends Command {

	public int nodeValue;
	public int oldBalance;
	public int newBalance;
	
	public static final String CHANGE_BALANCE_COMMAND = "treeChangeBalanceCommand";

	public ChangeBalanceCommand(Integer nodeId, int nodeValue, int oldBalance, int newBalance) {
		super(nodeId, CHANGE_BALANCE_COMMAND);
		this.nodeValue = nodeValue;
		this.oldBalance = oldBalance;
		this.newBalance = newBalance;
	}

	@Override
	public String execute() {
		return "Cambia el balance del elemento " + nodeValue + " de " + oldBalance + " a " + newBalance;
	}

	@Override
	public String undo() {
		return "Cambia el balance del elemento " + nodeValue + " de " + newBalance + " a " + oldBalance;
	}

	public int getNewBalance() {
		return newBalance;
	}

	public int getOldBalance() {
		return oldBalance;
	}

	public int getNodeValue() {
		return nodeValue;
	}

}
