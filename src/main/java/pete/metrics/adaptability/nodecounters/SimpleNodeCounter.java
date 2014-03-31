package pete.metrics.adaptability.nodecounters;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pete.metrics.adaptability.RelevantElements;

/**
 * Counts occurences of XML elements in a BPMN process, sorted by the name of
 * the element. The list of relevant elements is determined by the class
 * <code>RelevantElements</code>. The class will become obsolete once elements
 * are determined by an XPath expression and not their name.
 * 
 */
public class SimpleNodeCounter implements NodeCounter {

	private HashMap<String, AtomicInteger> elements;

	private boolean isStrict;

	private List<String> relevantElements;

	public SimpleNodeCounter() {
		setUp(true);
	}

	public SimpleNodeCounter(boolean isStrict) {
		setUp(isStrict);
	}

	private void setUp(boolean isStrict) {
		elements = new HashMap<String, AtomicInteger>();
		this.isStrict = isStrict;
		relevantElements = new RelevantElements().getRelevantElements();
	}

	@Override
	public void addToCounts(Node node) {
		// ignore text nodes
		if (isTextNode(node)) {
			return;
		}

		addToMap(node);

		NodeList children = node.getChildNodes();
		if (children != null) {
			for (int i = 0; i < children.getLength(); i++) {
				addToCounts(children.item(i));
			}
		}
	}

	private boolean isTextNode(Node node) {
		return node.getNodeType() == Node.TEXT_NODE;
	}

	private void addToMap(Node node) {
		String nodeName = node.getLocalName();
		if (isStrict && !relevantElements.contains(nodeName)) {
			return;
		}

		if (elements.containsKey(nodeName)) {
			AtomicInteger count = elements.get(nodeName);
			count.incrementAndGet();
		} else {
			elements.put(nodeName, new AtomicInteger(1));
		}
	}

	@Override
	public void writeToCsv(Path file) {
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file,
				Charset.defaultCharset()))) {
			writer.println("element;number");
			for (String key : elements.keySet()) {
				AtomicInteger value = elements.get(key);
				writer.println(key + ";" + value);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Map<String, AtomicInteger> getElementNumbers() {
		return Collections.unmodifiableMap(elements);
	}
}
