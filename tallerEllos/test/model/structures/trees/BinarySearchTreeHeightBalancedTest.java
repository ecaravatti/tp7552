package model.structures.trees;

import model.structures.trees.exceptions.BSTKeyFoundException;

import model.structures.trees.exceptions.BSTKeyNotFoundException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeHeightBalancedTest {// extends TestCase {

	BSTHeightBalanced<Integer> treeAVL;
	Integer data1, data2;

	@Before
	public void setUp() {
		treeAVL = new BSTHeightBalanced<Integer>(1);
		data1 = new Integer((int) Math.round(1000*Math.random()));
		data2 = new Integer((int) Math.round(1000*Math.random()));
	}

    private void printAVL() {
        for (BSTIterator<Integer> it = treeAVL.traversePreOrder(); it.hasNext();) {
            BSTNode<Integer> node = it.next();
            System.out.println(node.getData() + " " + node.getBalance());
        }
        System.out.println();
    }

	@Test
	public void testInsert() throws BSTKeyFoundException {
        assertEquals(0, treeAVL.getSize());
		treeAVL.insert(data1);
        assertEquals(1, treeAVL.getSize());
		treeAVL.insert(data2);
        assertEquals(2, treeAVL.getSize());
	}

	@Test(expected=BSTKeyFoundException.class)
	public void testInsertDuplicate() throws BSTKeyFoundException {
		treeAVL.insert(data1);
		treeAVL.insert(data1);
        fail("Hay un elemento duplicado!!");
	}

	/*@Test
	public void testHeight() throws BSTKeyFoundException {
		treeAVL.insert(new Integer(5));
		treeAVL.insert(new Integer(10));
		treeAVL.insert(new Integer(3));
		treeAVL.insert(new Integer(8));
		treeAVL.insert(new Integer(12));
        assertEquals(5, treeAVL.traversePreOrder().next().getData().intValue());
        assertEquals(3, treeAVL.getHeight());

		treeAVL.insert(new Integer(6));
        assertEquals(3, treeAVL.getHeight());
		treeAVL.insert(new Integer(15));
        //assertEquals(10, treeAVL.traversePreOrder().next().getData().intValue());
        // Arbol completo
        assertEquals(3, treeAVL.getHeight());
        assertEquals(7, treeAVL.getSize());
	}*/

    @Test
    public void testAVLInsertCase1() throws BSTKeyFoundException {
        //http://cis.stvincent.edu/carlsond/swdesign/avltrees/avltrees.html

		treeAVL.insert(new Integer(40));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(20));
		treeAVL.insert(new Integer(50));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(70));
		treeAVL.insert(new Integer(45));
		treeAVL.insert(new Integer(30));
        assertEquals(3, treeAVL.getHeight());

        treeAVL.insert(new Integer(60));
        assertEquals(4, treeAVL.getHeight());
        assertEquals(40, treeAVL.getRootData().intValue());
    }

    @Test
    public void testAVLInsertCase3A() throws BSTKeyFoundException {
        //http://cis.stvincent.edu/carlsond/swdesign/avltrees/avltrees.html

		treeAVL.insert(new Integer(80));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(30));
		treeAVL.insert(new Integer(100));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(90));
		treeAVL.insert(new Integer(15));
		treeAVL.insert(new Integer(40));
        assertEquals(3, treeAVL.getHeight());
		treeAVL.insert(new Integer(10));
		treeAVL.insert(new Integer(20));
        assertEquals(4, treeAVL.getHeight());

        treeAVL.insert(new Integer(5));
        assertEquals(4, treeAVL.getHeight());
        assertEquals(80, treeAVL.getRootData().intValue());
    }

    @Test
    public void testAVLInsertCase3B() throws BSTKeyFoundException {
        //http://cis.stvincent.edu/carlsond/swdesign/avltrees/avltrees.html

		treeAVL.insert(new Integer(80));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(30));
		treeAVL.insert(new Integer(100));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(90));
		treeAVL.insert(new Integer(120));
		treeAVL.insert(new Integer(20));
		treeAVL.insert(new Integer(50));
        assertEquals(3, treeAVL.getHeight());
		treeAVL.insert(new Integer(10));
		treeAVL.insert(new Integer(40));
		treeAVL.insert(new Integer(60));
        assertEquals(4, treeAVL.getHeight());

        treeAVL.insert(new Integer(55));
        assertEquals(4, treeAVL.getHeight());
        assertEquals(50, treeAVL.getRootData().intValue());
    }

    @Test
    public void testAVLDelete1() throws BSTKeyFoundException, BSTKeyNotFoundException {
		treeAVL.insert(new Integer(5));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(3));
		treeAVL.insert(new Integer(10));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(12));
		treeAVL.insert(new Integer(8));
        assertEquals(3, treeAVL.getHeight());

        treeAVL.delete(new Integer(12));
        assertEquals(3, treeAVL.getHeight());
        assertEquals(5, treeAVL.getRootData().intValue());
    }

    @Test
    public void testAVLDelete2() throws BSTKeyFoundException, BSTKeyNotFoundException {
		treeAVL.insert(new Integer(5));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(3));
		treeAVL.insert(new Integer(10));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(12));
		treeAVL.insert(new Integer(8));
        assertEquals(3, treeAVL.getHeight());

        treeAVL.delete(new Integer(5));
        assertEquals(3, treeAVL.getHeight());
        assertEquals(10, treeAVL.getRootData().intValue());
    }

    @Test
    public void testAVLDelete3() throws BSTKeyFoundException, BSTKeyNotFoundException {
        // Borrado por rotacion simple

		treeAVL.insert(new Integer(9));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(5));
		treeAVL.insert(new Integer(12));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(3));
		treeAVL.insert(new Integer(11));
		treeAVL.insert(new Integer(8));
        assertEquals(3, treeAVL.getHeight());
		treeAVL.insert(new Integer(2));
		treeAVL.insert(new Integer(1));
        assertEquals(4, treeAVL.getHeight());

        treeAVL.delete(new Integer(12));
        assertEquals(3, treeAVL.getHeight());
        assertEquals(5, treeAVL.getRootData().intValue());
    }

    @Test
    public void testAVLDelete4() throws BSTKeyFoundException, BSTKeyNotFoundException {
        // Borrado por rotacion doble

		treeAVL.insert(new Integer(9));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(5));
		treeAVL.insert(new Integer(12));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(3));
		treeAVL.insert(new Integer(11));
		treeAVL.insert(new Integer(8));
        assertEquals(3, treeAVL.getHeight());
		treeAVL.insert(new Integer(6));
		treeAVL.insert(new Integer(1));
        assertEquals(4, treeAVL.getHeight());

        treeAVL.delete(new Integer(12));
        assertEquals(4, treeAVL.getHeight());
        assertEquals(8, treeAVL.getRootData().intValue());
//        printAVL();
    }

    @Test
    public void testAVLDelete5() throws BSTKeyFoundException, BSTKeyNotFoundException {
        // Borrado por rotacion doble

		treeAVL.insert(new Integer(20));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(10));
		treeAVL.insert(new Integer(35));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(5));
		treeAVL.insert(new Integer(15));
		treeAVL.insert(new Integer(25));
        assertEquals(3, treeAVL.getHeight());
		treeAVL.insert(new Integer(40));
		treeAVL.insert(new Integer(18));
		treeAVL.insert(new Integer(30));
		treeAVL.insert(new Integer(38));
		treeAVL.insert(new Integer(45));
		treeAVL.insert(new Integer(50));
        assertEquals(5, treeAVL.getHeight());

        treeAVL.delete(new Integer(5));
        assertEquals(4, treeAVL.getHeight());
        assertEquals(35, treeAVL.getRootData().intValue());
        //printAVL();
    }

    @Test
    public void testAVLDelete6() throws BSTKeyFoundException, BSTKeyNotFoundException {
        // Borrado por rotacion doble

		treeAVL.insert(new Integer(3));
        assertEquals(1, treeAVL.getHeight());
		treeAVL.insert(new Integer(7));
		treeAVL.insert(new Integer(1));
        assertEquals(2, treeAVL.getHeight());
		treeAVL.insert(new Integer(2));
        assertEquals(3, treeAVL.getHeight());

        treeAVL.delete(new Integer(3));
        assertEquals(2, treeAVL.getHeight());
        assertEquals(2, treeAVL.getRootData().intValue());
        //printAVL();
    }
}
