package command.tree;

import command.Command;

public class RightRotationCommand extends Command {
	
	private Integer parentId;
	private int nodeId;
	private int childId;
	private Integer grandId;
		
	private boolean isLeftOfParent; //Rotacion sucede del lado izquierdo del padre.
	
	private int parentInitialChild;
	private Integer nodeInitialLeftChild;
	private int childInitialRightChild;
	
	private int parentFinalChild;
	private Integer nodeFinalLeftChild;
	private int childFinalRightChild; 
	
	

	public RightRotationCommand(Integer parentId, int nodeId, int childId,
			Integer grandId, boolean isLeftOfParent, int parentFinalChild,
			Integer nodeFinalLeftChild, int childFinalRightChild) {
		super(nodeId);
		this.parentId = parentId;
		this.nodeId = nodeId;
		this.childId = childId;
		this.grandId = grandId;
		this.isLeftOfParent = isLeftOfParent;
		this.parentFinalChild = parentFinalChild;
		this.nodeFinalLeftChild = nodeFinalLeftChild;
		this.childFinalRightChild = childFinalRightChild;
		
		this.parentInitialChild = nodeId;
		this.nodeInitialLeftChild = childId;
		this.childInitialRightChild = grandId;
	}

	@Override
	public String execute() {
		return "Rota el nodo " + nodeId + " a derecha";
	}

	@Override
	public String undo() {
		return "Deshace la rotacion a derecha del nodo " + nodeId;
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

	public Integer getNodeInitialLeftChild() {
		return nodeInitialLeftChild;
	}

	public Integer getChildInitialRightChild() {
		return childInitialRightChild;
	}

	public int getParentFinalChild() {
		return parentFinalChild;
	}

	public Integer getNodeFinalLeftChild() {
		return nodeFinalLeftChild;
	}

	public int getChildFinalRightChild() {
		return childFinalRightChild;
	}


}
