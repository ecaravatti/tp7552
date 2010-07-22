package view.structures.trie.commands;

import view.commons.commands.Command;
import java.util.Hashtable;

import view.structures.trie.TrieView;
import view.structures.trie.elements.AbstractTrieNodeView;
import view.structures.trie.elements.TrieNodeView;


public class PreAddTrieNodeCommand implements Command {
  private AddTrieNode addTrieNode;
  private TrieNodeView parent;
  private AbstractTrieNodeView node;
  private TrieView trieView;
  private Hashtable<AddTrieNode, Command> table;

  public enum AddTrieNode {
    AddChild, AddData, AddSibling, AddRoot
  };

  public PreAddTrieNodeCommand(TrieView trieView, AddTrieNode addTrieNode,
      TrieNodeView parent, AbstractTrieNodeView node) {
    super();
    this.trieView = trieView;
    this.addTrieNode = addTrieNode;
    this.parent = parent;
    this.node = node;
    table = new Hashtable<AddTrieNode, Command>();
    table.put(AddTrieNode.AddChild, new AddChildCommand());
    table.put(AddTrieNode.AddSibling, new AddSiblingCommand());
    table.put(AddTrieNode.AddData, new AddDataNodeCommand());
    table.put(AddTrieNode.AddRoot, new AddRootCommand());
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    table.get(addTrieNode).execute();
  }

  private class AddChildCommand implements Command {

    @Override
    public boolean canExecute() {
      return true;
    }

    @Override
    public void execute() {
      parent.addChild(node);
      node.getParent().stopFlashing();
      node.setVisible(false);
      node.setInvisible(false);
    }
  }

  private class AddSiblingCommand implements Command {

    @Override
    public boolean canExecute() {
      return true;
    }

    @Override
    public void execute() {
      parent.addSibling(node);
      node.getParent().stopFlashing();
      node.setVisible(false);
      node.setInvisible(false);
    }
  }

  private class AddDataNodeCommand implements Command {

    @Override
    public boolean canExecute() {
      return true;
    }

    @Override
    public void execute() {
      parent.setEndWord(true);
      parent.addDataNode(node);
      node.getParent().stopFlashing();
      node.setVisible(false);
      node.setInvisible(false);
    }
  }

  private class AddRootCommand implements Command {

    @Override
    public boolean canExecute() {
      return true;
    }

    @Override
    public void execute() {
      trieView.setRoot( parent );
      if (parent.hasSibling())
          parent.getSibling().setParent( parent.getSiblingPointerView() );
      trieView.getWord().setVisible(0, true);
    }
  }
}
