package arbol.altura;

//BTData class is a wrapper for the actual data
//It must provide a method for comparing values
//Ideally, it should implement Comparable interface
public class BTData {

	   public static final int LT = BTNode.LEFT;
	   public static final int EQ = BTNode.EQUAL;
	   public static final int GT = BTNode.RIGHT;

	   String key;

	// null pointers eliminated for convenience
	   public BTData()
	   {
	     key = "";
	   }
	   public BTData(String key)
	   {
	     this.key = key;
	   }
	   public String getKey()
	   {
	     return (key != null) ? key : "";
	   }
	   public void setKey(String key)
	   {
	     this.key = key;
	   }
	   public int compareTo(BTData data)
	   {
	     int cmp = key.compareTo(data.key);
	     if(cmp < 0)
	       cmp = LT;
	     else if(cmp > 0)
	       cmp = GT;
	     else if(cmp == 0)
	       cmp = EQ;
	     return cmp;
	   }
	
}
