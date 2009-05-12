package command.tree;

import command.Command;

public class LeftRotationCommand extends Command {

	private Integer parentId;
	private int nodeId;
	private int childId;
	private Integer grandId;

	//Rotacion sucede del lado izquierdo del padre.
	private boolean isLeftOfParent;

	private Integer parentInitialChild;
	private Integer nodeInitialRightChild;
	private Integer childInitialLeftChild;

	private Integer parentFinalChild;
	private Integer nodeFinalRightChild;
	private Integer childFinalLeftChild;

	public LeftRotationCommand(Integer parentId, int nodeId, int childId,
			Integer grandId, boolean isLeftOfParent, Integer parentFinalChild,
			Integer nodeFinalRightChild, Integer childFinalLeftChild) {
		super(nodeId);
		this.parentId = parentId;
		this.nodeId = nodeId;
		this.childId = childId;
		this.grandId = grandId;
		this.isLeftOfParent = isLeftOfParent;
		this.parentFinalChild = parentFinalChild;
		this.nodeFinalRightChild = nodeFinalRightChild;
		this.childFinalLeftChild = childFinalLeftChild;

		this.parentInitialChild = nodeId;
		this.nodeInitialRightChild = childId;
		this.childInitialLeftChild = grandId;
	}

	@Override
	public String execute() {
		return "Rota el nodo " + nodeId + " a izquierda";
	}

	@Override
	public String undo() {
		return "Deshace la rotacion a izquierda del nodo " + nodeId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public int getChildId() {
		return childId;
	}

	public Integer getGrandId() {
		return grandId;
	}

	public boolean isLeftOfParent() {
		return isLeftOfParent;
	}

	public Integer getParentInitialChild() {
		return parentInitialChild;
	}

	public Integer getNodeInitialRightChild() {
		return nodeInitialRightChild;
	}

	public Integer getChildInitialLeftChild() {
		return childInitialLeftChild;
	}

	public Integer getParentFinalChild() {
		return parentFinalChild;
	}

	public Integer getNodeFinalRightChild() {
		return nodeFinalRightChild;
	}

	public Integer getChildFinalLeftChild() {
		return childFinalLeftChild;
	}

}
