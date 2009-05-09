package tree.binary;


//BTData class is a wrapper for the actual data
//It must provide a method for comparing values
//Ideally, it should implement Comparable interface
public class BTData implements Comparable<BTData>{

	public static final int LT = BTNode.LEFT;
	public static final int EQ = BTNode.EQUAL;
	public static final int GT = BTNode.RIGHT;

	private int key;

	public BTData(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int compareTo(BTData data) {
		return new Integer(this.key).compareTo(data.key);
	}
	
	@Override
	public String toString() {
		return Integer.toString(key);
	}

}
