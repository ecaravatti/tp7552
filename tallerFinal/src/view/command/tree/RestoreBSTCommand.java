package view.command.tree;

import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;

public class RestoreBSTCommand implements Command {
	private BinarySearchTreeView bstView;

	public RestoreBSTCommand(BinarySearchTreeView bstView) {
		super();
		this.bstView = bstView;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		bstView.restoreFromLastState();
	}

}
