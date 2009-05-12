package command.tree;

import command.Command;

public class ChangeBalanceCommand extends Command {
	
	private int newBalance;
	private int oldBalance;
	
	public ChangeBalanceCommand(Integer data, int newBalance, int oldBalance) {
		super(data);
		this.newBalance = newBalance;
		this.oldBalance = oldBalance;
	}
	
	@Override
	public String execute() {
		return "Cambia el balance de " + oldBalance + " a " + newBalance;
	}

	@Override
	public String undo() {
		return "Cambia el balance de " + newBalance + " a " + oldBalance;
	}

	public int getNewBalance() {
		return newBalance;
	}

	public int getOldBalance() {
		return oldBalance;
	}

}
