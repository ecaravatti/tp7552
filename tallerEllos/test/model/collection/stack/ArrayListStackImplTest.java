package model.collection.stack;

import java.util.EmptyStackException;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class ArrayListStackImplTest {

    private static final Integer STACK_ITEM_1 = 1;
    private static final Integer STACK_ITEM_2 = 2;

    @Test
    public void testIsEmptyUsingTrueCondition() {
        ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void testIsEmptyUsingFalseCondition() {
    	ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);

        Assert.assertFalse(stack.isEmpty());
    }

    @Test
    public void testGetSize() {
    	ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);

        Assert.assertEquals(1, stack.getSize());
    }

    @Test
    public void testPeekUsingEmptyStack() {
    	ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();

        try {
            stack.peek();
            Assert.fail();
        } catch (EmptyStackException e) {
        } catch (Exception e) {
            Assert.fail(e.getClass().getName());
        }
    }

    @Test
    public void testPeek() {
    	ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);

        Assert.assertEquals(STACK_ITEM_1, stack.peek());
        Assert.assertEquals(1, stack.getSize());
    }

    @Test
    public void testPush() {
    	ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();

        stack.push(STACK_ITEM_1);

        Assert.assertEquals(STACK_ITEM_1, stack.peek());
        Assert.assertEquals(1, stack.getSize());

        stack.push(STACK_ITEM_2);

        Assert.assertEquals(STACK_ITEM_2, stack.peek());
        Assert.assertEquals(2, stack.getSize());
    }

    @Test
    public void testPop() {
    	ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);
        stack.push(STACK_ITEM_2);

        Assert.assertEquals(STACK_ITEM_2, stack.pop());
        Assert.assertEquals(1, stack.getSize());
        Assert.assertEquals(STACK_ITEM_1, stack.pop());
        Assert.assertEquals(0, stack.getSize());
    }

    @Test
    public void testIterator() {
    	ArrayListStackImpl<Integer> stack = new ArrayListStackImpl<Integer>();

        Iterator<Integer> iterator = stack.iterator();

        Assert.assertNotNull(iterator);
    }

}
