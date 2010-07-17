package model.structures.commons;

import java.util.NoSuchElementException;

import junit.framework.Assert;

import model.structures.commons.StructureIterator;
import model.structures.commons.StructureNode;

import org.junit.Test;

public class StructureIteratorTest {

    private static final Integer FIRST_ELEMENT = 1;
    private static final Integer SECOND_ELEMENT = 2;

    @Test
    public void testHasNextUsingTrueCondition() {
        StructureNode<Integer> currentNode = new StructureNode<Integer>();
        StructureIterator<Integer> stackIterator = new StructureIterator<Integer>(currentNode);

        Assert.assertTrue(stackIterator.hasNext());
    }

    @Test
    public void testHasNextUsingFalseCondition() {
        StructureIterator<Integer> stackIterator = new StructureIterator<Integer>();

        Assert.assertFalse(stackIterator.hasNext());
    }


    @Test
    public void testRemove() {
        StructureIterator<Integer> stackIterator = new StructureIterator<Integer>();

        try {
            stackIterator.remove();
            Assert.fail();
        } catch (UnsupportedOperationException e) {
        } catch (Exception e) {
            Assert.fail(e.getClass().getName());
        }
    }

    @Test
    public void testNextUsingNullNextNode() {
        StructureIterator<Integer> stackIterator = new StructureIterator<Integer>();

        try {
            stackIterator.next();
            Assert.fail();
        } catch (NoSuchElementException e) {
        } catch (Exception e) {
            Assert.fail(e.getClass().getName());
        }
    }

    @Test
    public void testNext() {
        StructureNode<Integer> firstNode = new StructureNode<Integer>();
        firstNode.setItem(FIRST_ELEMENT);

        StructureNode<Integer> secondNode = new StructureNode<Integer>();
        secondNode.setItem(SECOND_ELEMENT);

        firstNode.setNextNode(secondNode);

        StructureIterator<Integer> stackIterator = new StructureIterator<Integer>(firstNode);

        Assert.assertEquals(FIRST_ELEMENT, stackIterator.next());
        Assert.assertEquals(SECOND_ELEMENT, stackIterator.next());
    }

}
