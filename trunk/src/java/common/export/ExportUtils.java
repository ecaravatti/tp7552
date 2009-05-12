package common.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import collection.queue.Queue;
import collection.stack.Stack;
import collection.tree.binary.BTNode;
import collection.tree.binary.BTree;
import collection.trie.Node;
import collection.trie.Trie;


import command.queue.PollCommand;
import command.stack.PopCommand;

public class ExportUtils {

	private final static String PATH = "export/";
	private final static String ROOT = "Root";
	private final static String KEY = "key";
	private final static String CONTENT = "content";
	private final static String CHILD = "Child";
	private final static String STACK = "Stack";
	private final static String ELEMENT = "Element";
	private final static String TOP = "Top";
	private final static String QUEUE = "Queue";
	private final static String HEAD = "Head";
	private final static String BACK = "Back";
	private final static String LEFT_CHILD = "Left Child";
	private final static String RIGHT_CHILD = "Right Child";

	public static void exportToXML(Queue queue, String path) {

		Document document = DocumentHelper.createDocument();
		Element XMLroot = document.addElement(QUEUE);

		Queue clone = null;
		try {
			clone = queue.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		Element headElement = XMLroot.addElement(ELEMENT);
		headElement.addAttribute(HEAD, Boolean.TRUE.toString());
		addElementAttributes(headElement, (PollCommand) clone.poll().get(0));

		int cloneSize = clone.size();
		for (int i = 0; i < cloneSize - 1; i++) {
			addElementAttributes(XMLroot.addElement(ELEMENT),
					(PollCommand) clone.poll().get(0));
		}
		Element backElement = XMLroot.addElement(ELEMENT);
		backElement.addAttribute(BACK, Boolean.TRUE.toString());
		addElementAttributes(backElement, (PollCommand) clone.poll().get(0));

		exportToFile(path, document);
		printToOutput(document);
	}

	private static void addElementAttributes(Element element,
			PollCommand pollCommand) {
		element.addAttribute(CONTENT, pollCommand.getContent());
		element.addAttribute(KEY, pollCommand.getId().toString());
	}

	public static void exportToXML(Stack stack, String path) {

		Document document = DocumentHelper.createDocument();
		Element XMLroot = document.addElement(STACK);

		Stack clone = null;
		try {
			clone = stack.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		Element topElement = XMLroot.addElement(ELEMENT);
		topElement.addAttribute(TOP, Boolean.TRUE.toString());
		addElementAttributes(topElement, (PopCommand) clone.pop().get(0));

		int cloneSize = clone.size();
		for (int i = 0; i < cloneSize; i++) {
			addElementAttributes(XMLroot.addElement(ELEMENT),
					(PopCommand) clone.pop().get(0));
		}

		exportToFile(path, document);
		printToOutput(document);
	}

	private static void addElementAttributes(Element element,
			PopCommand popCommand) {
		element.addAttribute(CONTENT, popCommand.getContent());
		element.addAttribute(KEY, popCommand.getId().toString());
	}
	
	public static void exportToXML(BTree tree, String path) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(ROOT);

		BTNode node = tree.getRoot();
		addNode(root, node);

		exportToFile(path, document);
		printToOutput(document);
	}
	
	private static void addNode(Element element, BTNode node) {
		element.addAttribute("balance", String.valueOf(node.getBalance()));
		element.addAttribute(KEY, String.valueOf(node.getElement().getValue()));

		if (node.getChild(BTNode.LEFT) != null) {
				addNode(element.addElement(LEFT_CHILD), node.getChild(BTNode.LEFT));
		}

		if (node.getChild(BTNode.RIGHT) != null) {
			addNode(element.addElement(RIGHT_CHILD), node.getChild(BTNode.RIGHT));
		}
	}

	public static void exportToXML(Trie trie, String path) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(ROOT);

		Node node = trie.getRoot();
		addNode(root, node);

		exportToFile(path, document);

		printToOutput(document);
	}

	private static void printToOutput(Document document) {
		XMLWriter writer;
		// Pretty print the document to System.out
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			writer = new XMLWriter(System.out, format);
			writer.write(document);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void exportToFile(String path, Document document) {
		// Export to a file
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter(PATH + path));
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addNode(Element element, Node node) {
		element.addAttribute(KEY, node.getContent());

		if (node.hasChildren()) {
			for (Character nodeLetter : node.getChildren().keySet()) {
				Element child = element.addElement(CHILD);
				child.addAttribute(CONTENT, nodeLetter.toString());
				addNode(child, node.getChildNode(nodeLetter));
			}
		}
	}
}
