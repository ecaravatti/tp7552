package command.tree;

import command.Command;

public class RightRotationCommand extends Command {
	
	private Integer parentId;
	private int nodeId;
	private int childId;
	private Integer grandId;
		
	private boolean isLeftOfParent; //Rotacion sucede del lado izquierdo del padre.
	
	private Integer parentInitialChild;
	private Integer nodeInitialLeftChild;
	private Integer childInitialRightChild;
	
	private Integer parentFinalChild;
	private Integer nodeFinalLeftChild;
	private Integer childFinalRightChild; 
	
	

	public RightRotationCommand(Integer parentId, int nodeId, int childId,
			Integer grandId, boolean isLeftOfParent, Integer parentFinalChild,
			Integer nodeFinalLeftChild, Integer childFinalRightChild) {
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

	public Integer getParentFinalChild() {
		return parentFinalChild;
	}

	public Integer getNodeFinalLeftChild() {
		return nodeFinalLeftChild;
	}

	public Integer getChildFinalRightChild() {
		return childFinalRightChild;
	}


}
