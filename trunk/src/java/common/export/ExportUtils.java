package common.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import trie.Node;
import trie.Trie;

public class ExportUtils {
	
	
	private final static String PATH = "export/";
	private final static String ROOT = "Root";
	private final static String KEY = "key";
	private final static String CONTENT = "content";
	private final static String CHILD = "Child";
	

	public static void exportToXML(Trie trie, String path) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(ROOT);

		Node node = trie.getRoot();
		addNode(root, node);

		// Export to a file
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter(PATH + path));
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
