package model.structures.stacks;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class LinkedListStackImplTest {

    private static final Integer STACK_ITEM_1 = 1;
    private static final Integer STACK_ITEM_2 = 2;

    @Test
    public void testIsEmptyUsingTrueCondition() {
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void testIsEmptyUsingFalseCondition() {
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);

        Assert.assertFalse(stack.isEmpty());
    }

    @Test
    public void testGetSize() {
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);

        Assert.assertEquals(1, stack.getSize());
    }

    @Test
    public void testPeekUsingEmptyStack() {
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();

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
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);

        Assert.assertEquals(STACK_ITEM_1, stack.peek());
        Assert.assertEquals(1, stack.getSize());
    }

    @Test
    public void testPush() {
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();

        stack.push(STACK_ITEM_1);

        Assert.assertEquals(STACK_ITEM_1, stack.peek());
        Assert.assertEquals(1, stack.getSize());

        stack.push(STACK_ITEM_2);

        Assert.assertEquals(STACK_ITEM_2, stack.peek());
        Assert.assertEquals(2, stack.getSize());
    }

    @Test
    public void testPop() {
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();
        stack.push(STACK_ITEM_1);
        stack.push(STACK_ITEM_2);

        Assert.assertEquals(STACK_ITEM_2, stack.pop());
        Assert.assertEquals(1, stack.getSize());
        Assert.assertEquals(STACK_ITEM_1, stack.pop());
        Assert.assertEquals(0, stack.getSize());
    }

    @Test
    public void testIterator() {
        LinkedListStackImpl<Integer> stack = new LinkedListStackImpl<Integer>();

        Iterator<Integer> iterator = stack.iterator();

        Assert.assertNotNull(iterator);
    }

}
