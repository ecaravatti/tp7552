package model.structures.commons;

import junit.framework.Assert;

import model.structures.commons.StructureNode;

import org.junit.Test;

public class StructureNodeTest {

    private static final Integer NODE_ITEM = 1;

    @Test
    public void testGetItem() {
        StructureNode<Integer> node = new StructureNode<Integer>();

        node.setItem(NODE_ITEM);
        Assert.assertEquals(NODE_ITEM, node.getItem());
    }

    @Test
    public void testGetNextNode() {
        StructureNode<Integer> node = new StructureNode<Integer>();
        StructureNode<Integer> nextNode = new StructureNode<Integer>();;

        node.setNextNode(nextNode);
        Assert.assertEquals(nextNode, node.getNextNode());
    }

}
