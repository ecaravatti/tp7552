package model.collection.tree;

import model.collection.tree.BSTIterator;
import model.collection.tree.BSTNode;
import model.collection.tree.BSTWeightBalanced;
import model.exception.tree.BSTKeyFoundException;
import model.exception.tree.BSTKeyNotFoundException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeWeightBalancedTest {

	BSTWeightBalanced<Integer> tree;
	Integer data1, data2;
	
	@Before
	public void setUp() {
		tree = new BSTWeightBalanced<Integer>(1);
		data1 = new Integer((int) Math.round(1000*Math.random()));
		data2 = new Integer((int) Math.round(1000*Math.random()));
	}

    private void printTree() {
        for (BSTIterator<Integer> it = tree.traversePreOrder(); it.hasNext();) {
            BSTNode<Integer> node = it.next();
            System.out.println(node.getData() + " " + node.getBalance());
        }
        System.out.println();
    }
	
	@Test
	public void testInsert() throws BSTKeyFoundException {
		tree.insert(data1);
		tree.insert(data2);
        assertEquals(2, tree.getSize());
	}

	@Test(expected=BSTKeyFoundException.class)
	public void testInsertDuplicate() throws BSTKeyFoundException {
		tree.insert(data1);
		tree.insert(data1);
        fail("Hay un elemento duplicado!!");
	}

	/*@Test
	public void testRotate() throws BSTKeyFoundException {
		tree = new BSTWeightBalanced<Integer>(1);
		tree.insert(new Integer(5));
		tree.insert(new Integer(10));
		tree.insert(new Integer(3));
		tree.insert(new Integer(8));
		tree.insert(new Integer(12));
        assertEquals(3, tree.getHeight());
	}*/

    @Test
    public void testInsertCase3B() throws BSTKeyFoundException {
        //http://cis.stvincent.edu/carlsond/swdesign/trees/trees.html

		tree.insert(new Integer(80));
        assertEquals(1, tree.getHeight());
		tree.insert(new Integer(30));
		tree.insert(new Integer(100));
        assertEquals(2, tree.getHeight());
		tree.insert(new Integer(90));
		tree.insert(new Integer(120));
		tree.insert(new Integer(20));
		tree.insert(new Integer(50));
        assertEquals(3, tree.getHeight());
		tree.insert(new Integer(10));
		tree.insert(new Integer(40));
		tree.insert(new Integer(60));
        assertEquals(4, tree.getHeight());
        //print();

        tree.insert(new Integer(55));
        assertEquals(4, tree.getHeight());
        assertEquals(50, tree.getRootData().intValue());
        //print();
    }

    @Test
    public void testDelete1() throws BSTKeyFoundException, BSTKeyNotFoundException {
		tree.insert(new Integer(5));
        assertEquals(1, tree.getHeight());
		tree.insert(new Integer(3));
		tree.insert(new Integer(10));
        assertEquals(2, tree.getHeight());
		tree.insert(new Integer(12));
		tree.insert(new Integer(8));
        assertEquals(3, tree.getHeight());
        //printTree();

        tree.delete(new Integer(12));
        assertEquals(3, tree.getHeight());
        assertEquals(5, tree.getRootData().intValue());
        //printTree();
    }

    @Test
    public void testDelete2() throws BSTKeyFoundException, BSTKeyNotFoundException {
		tree.insert(new Integer(9));
        assertEquals(1, tree.getHeight());
		tree.insert(new Integer(5));
		tree.insert(new Integer(12));
        assertEquals(2, tree.getHeight());
		tree.insert(new Integer(3));
		tree.insert(new Integer(11));
		tree.insert(new Integer(8));
        assertEquals(3, tree.getHeight());
        //print();
		tree.insert(new Integer(2));
		tree.insert(new Integer(1));
        assertEquals(4, tree.getHeight());
        //printTree();

        tree.delete(new Integer(12));
        assertEquals(3, tree.getHeight());
        assertEquals(5, tree.getRootData().intValue());
        //printTree();
    }

    @Test
    public void testDelete3() throws BSTKeyFoundException, BSTKeyNotFoundException {
		tree.insert(new Integer(5));
        assertEquals(1, tree.getHeight());
		tree.insert(new Integer(3));
		tree.insert(new Integer(10));
        assertEquals(2, tree.getHeight());
		tree.insert(new Integer(12));
		tree.insert(new Integer(8));
        assertEquals(3, tree.getHeight());
        //printTree();

        tree.delete(new Integer(5));
        assertEquals(3, tree.getHeight());
        assertEquals(10, tree.getRootData().intValue());
        //printTree();
    }

    @Test
    public void testDelete5() throws BSTKeyFoundException, BSTKeyNotFoundException {
        // Borrado por rotacion doble

		tree.insert(new Integer(20));
        assertEquals(1, tree.getHeight());
		tree.insert(new Integer(10));
		tree.insert(new Integer(35));
        assertEquals(2, tree.getHeight());
		tree.insert(new Integer(5));
		tree.insert(new Integer(15));
		tree.insert(new Integer(25));
        assertEquals(3, tree.getHeight());
		tree.insert(new Integer(40));
		tree.insert(new Integer(18));
		tree.insert(new Integer(30));
		tree.insert(new Integer(38));
		tree.insert(new Integer(45));
		tree.insert(new Integer(50));
        assertEquals(5, tree.getHeight());

        tree.delete(new Integer(5));
        assertEquals(4, tree.getHeight());
        assertEquals(35, tree.getRootData().intValue());
        //printTree();
    }

    @Test
    public void testAVLDelete6() throws BSTKeyFoundException, BSTKeyNotFoundException {
        // Borrado por rotacion doble

		tree.insert(new Integer(3));
        assertEquals(1, tree.getHeight());
		tree.insert(new Integer(7));
		tree.insert(new Integer(1));
        assertEquals(2, tree.getHeight());
		tree.insert(new Integer(2));
        assertEquals(3, tree.getHeight());

        tree.delete(new Integer(3));
        assertEquals(2, tree.getHeight());
        assertEquals(2, tree.getRootData().intValue());
        //printAVL();
    }

    @Test
    public void testAVLDelete7() throws BSTKeyFoundException, BSTKeyNotFoundException {
        // Borrado por rotacion doble

		tree.insert(new Integer(50));
        assertEquals(1, tree.getHeight());
		tree.insert(new Integer(60));
		tree.insert(new Integer(70));
        assertEquals(2, tree.getHeight());
		tree.insert(new Integer(80));
		tree.insert(new Integer(75));
		tree.insert(new Integer(40));
        assertEquals(3, tree.getHeight());
		tree.insert(new Integer(74));
		tree.insert(new Integer(73));
        assertEquals(4, tree.getHeight());

        //tree.delete(new Integer(3));
        assertEquals(4, tree.getHeight());
        assertEquals(73, tree.getRootData().intValue());
        //printAVL();
    }
}
