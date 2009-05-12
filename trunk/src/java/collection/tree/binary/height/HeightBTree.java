package collection.tree.binary.height;

import collection.tree.binary.BTNode;
import collection.tree.binary.BTree;
import collection.tree.binary.KeyAlreadyExistsException;
import collection.tree.binary.KeyNotFoundException;
import command.tree.ChangeBalanceCommand;
import common.Element;

public class HeightBTree extends BTree {

	private int variacionMaxima;

	public HeightBTree(int variacionMaxima) {
		super();
		this.variacionMaxima = variacionMaxima;
	}

	public HeightBTree() {
		super();
		this.variacionMaxima = 1; // Valor por defecto
	}

	@Override
	public BTNode insert(int value) throws KeyAlreadyExistsException {
		initCommands();
		Element<Integer> element = new Element<Integer>(value, value);
		BTNode node;

		if (root == null) {
			return add(null, 0, element);
		}

		try {
			locate(element.getValue());
			throw new KeyAlreadyExistsException();
		} catch (KeyNotFoundException e) {
			node = add(lastNode, nextSide, element);
			rebalance(lastNode, nextSide, INSERT);
			return node;
		}

	}

	@Override
	public BTNode delete(int value) throws KeyNotFoundException {
		initCommands();
		Element<Integer> element = new Element<Integer>(value, value);
		int side;
		BTNode node;

		node = locate(element.getValue());
		if (node == null) {
			throw new KeyNotFoundException();
		}

		if (node.hasTwoChildren()) {
			node = swap(node, BTree.FIND_MAX);
		}

		side = node.getSide();
		node = remove(node);
		rebalance(node, side, DELETE);
		return node;
	}

	private void rebalance(BTNode node, int side, int in) {
		int newBalance;
		int balanceInicial;
		boolean noEmpeoraBalanceo;
		boolean huboRotacion = false;
		boolean cambioAltura = false;
		BTNode nodeAux;
		for (; node != null; node = node.getParent()) {
			huboRotacion = false;
			cambioAltura = false;
			balanceInicial = node.getBalance();
			newBalance = node.getBalance() + in * side;
			changeBalance(node, newBalance);
			if (Math.abs(newBalance) > variacionMaxima) {
				huboRotacion = true;

				if (newBalance < 0) {
					BTNode leftChild = node.getChild(BTNode.LEFT);
					if (leftChild.getBalance() > 0) {
						cambioAltura = true;
						nodeAux = rotateLeftAndSetBalance(leftChild);
						if (nodeAux.getBalance() >= 0) {
							// El subarbol decreció en un nivel.
							changeBalance(node, node.getBalance() - in * side);
						}
					}
					node = rotateRightAndSetBalance(node);
					if (!cambioAltura) {
						cambioAltura = node.getBalance() <= 0;
					}
				} else {
					BTNode rightChild = node.getChild(BTNode.RIGHT);
					if (rightChild.getBalance() < 0) {
						cambioAltura = true;
						nodeAux = rotateRightAndSetBalance(rightChild);
						if (nodeAux.getBalance() <= 0) {
							// El subarbol decreció en un nivel.
							changeBalance(node, node.getBalance() - in * side);
						}
					}
					node = rotateLeftAndSetBalance(node);
					if (!cambioAltura) {
						cambioAltura = node.getBalance() >= 0;
					}
				}

			}

			noEmpeoraBalanceo = Math.abs(node.getBalance())
					- Math.abs(balanceInicial) <= 0;

			if ((huboRotacion && !cambioAltura)
					|| (in == INSERT && noEmpeoraBalanceo)
					|| (in == DELETE && !noEmpeoraBalanceo)) {
				break;
			}
			side = node.getSide();
		}
	}

	private BTNode rotateRightAndSetBalance(BTNode node) {
		BTNode child = rotateRight(node);

		// Balances
		int balanceAux = node.getBalance();
		changeBalance(node, balanceAux + 1 + Math.max(-child.getBalance(), 0));
		changeBalance(child, -Math.min(-balanceAux - 2, Math.min(-balanceAux
				- child.getBalance() - 2, -child.getBalance() - 1)));

		return child;
	}

	private BTNode rotateLeftAndSetBalance(BTNode node) {
		BTNode child = rotateLeft(node);

		// Balances
		int balanceAux = node.getBalance();
		changeBalance(node, balanceAux - 1 - Math.max(child.getBalance(), 0));
		changeBalance(child, Math.min(balanceAux - 2, Math.min(balanceAux
				+ child.getBalance() - 2, child.getBalance() - 1)));

		return child;
	}

	private void changeBalance(BTNode node, int newBalance) {
		commands.add(new ChangeBalanceCommand(node.getElement().getId(), node
				.getElement().getValue(), node.getBalance(), newBalance));
		node.setBalance(newBalance);
	}

}
