package tree.height;

// class BTNode defines the tree node data structure
// and provides a collection of get/set methods protecting private data
// all balancing methods are defined in BSTree class
public class BTNode {

	   public static final int LEFT  =  -1;
	   public static final int RIGHT =  +1;
	   public static final int NONE  =   0;
	   public static final int EQUAL =   0;
	   public static final int ERROR = 666;

	   BTNode parent;
	   BTNode lchild;
	   BTNode rchild;
	   int    balance;
	   int    color;
	   BTData data;

	   public BTNode()
	   {
	     parent  = null;
	     lchild  = null;
	     rchild  = null;
	     balance = 0;
	     color   = 0;
	     data    = null;
	   }
	   //este metodo lo agregue yo, manu
	   public BTNode(BTData data)
	   {
	     parent  = null;
	     lchild  = null;
	     rchild  = null;
	     balance = 0;
	     color   = 0;
	     this.data    = data;
	   }
	   public int getBalance()
	   {
	     return balance;
	   }
	   public void setBalance(int balance)
	   {
	     this.balance = balance;
	   }
	   public int getColor()
	   {
	     return color;
	   }
	   public void setColor(int color)
	   {
	     this.color = color;
	   }
	   public BTData getData()
	   {
	     return data;
	   }
	   public void setData(BTData data)
	   {
	     this.data = data;
	   }
	   public BTNode getParent()
	   {
	     return parent;
	   }
	   public void setParent(BTNode parent)
	   {
	     this.parent = parent;
	   }
	   public BTNode getChild(int side)
	   {
	     if(side == LEFT)
	       return lchild;
	     else
	     if(side == RIGHT)
	       return rchild;
	     else
	     if(side == EQUAL)
	       return this;       // match
	     return null; //Lo agregué yo, manu, para que compile
	   }
	   public BTNode getChild()
	   {
	     return (lchild != null) ? lchild : rchild;
	   }
	   public void setChild(int side, BTNode node)
	   {
	     if(side == LEFT)
	       lchild = node;
	     else
	     if(side == RIGHT)
	       rchild = node;
	   }
	   public int getSide() // on which side of the parent am I?
	   {
	     if(parent == null)
	       return NONE;       // this is root
	     else
	     if(this == parent.lchild)
	       return LEFT;
	     else
	     if(this == parent.rchild)
	      return RIGHT;
	     return ERROR;
	   }

	   public BTNode prevInO()
	   {
	     BTNode temp = this;
	     BTNode node = null;
	     if(temp.lchild != null)
	       node = temp.lchild.lastInO();
	     else
	       for(; (node = temp.parent) != null && temp == node.lchild; temp = node)
	         ;
	     return node;
	   }

	   public BTNode nextInO()
	   {
	     BTNode temp = this;
	     BTNode node = null;
	     if(temp.rchild != null)
	       node = temp.rchild.firstInO();
	     else
	       for(; (node = temp.parent) != null && temp == node.rchild; temp = node)
	         ;
	     return node;
	   }

	   public BTNode firstInO()
	   {
	     BTNode node;
	     for(node = this; node.lchild != null; node = node.lchild)
	       ;
	     return node;
	   }

	   public BTNode lastInO()
	   {
	     BTNode node;
	     for(node = this; node.rchild != null; node = node.rchild);
	     return node;
	   }
	 

	// wrappers

	   public int compareTo(BTData data)
	   {
	     int side = data.compareTo(this.data);
	     return side;
	   }

	   public boolean hasTwoChildren()
	   {
	     return (lchild != null && rchild != null);
	   }
	   public boolean isLeaf()
	   {
	     return (lchild == null && rchild == null);
	   }
	  

	
}
