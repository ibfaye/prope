package prope.metrics.adaptability;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import prope.executables.AnalysisException;
import prope.executables.FileAnalyzer;
import prope.metrics.adaptability.nodecounters.NodeCounter;
import prope.metrics.adaptability.nodecounters.XPathNodeCounter;
import prope.reporting.ReportEntry;

public class AdaptabilityAnalyzer implements FileAnalyzer {

	private static final String RAW_DATA_FILE = "raw.csv";

	private NodeCounter nodeCounter;

	private BpmnInspector inspector;

	private List<AdaptabilityMetric> testMetrics;

	public AdaptabilityAnalyzer() {
		nodeCounter = new XPathNodeCounter();
		inspector = new BpmnInspector();

		testMetrics = new ArrayList<>(5);
		testMetrics.add(new BinaryAdaptabilityMetric(0.2));
		testMetrics.add(new BinaryAdaptabilityMetric(0.4));
		testMetrics.add(new BinaryAdaptabilityMetric(0.6));
		testMetrics.add(new BinaryAdaptabilityMetric(0.8));
		testMetrics.add(new WeightedAdaptabilityMetric());
	}

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {
		System.out.println("Analyzing " + filePath + " for adaptability");

		ReportEntry entry = new ReportEntry(filePath.toString());
		try {
			populateReportEntry(entry);
		} catch (AnalysisException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>(0);
		}
		List<ReportEntry> entries = new ArrayList<>(1);
		entries.add(entry);

		getGroupStringIfPossible(filePath.toAbsolutePath().toString(), entry);

		return entries;
	}

	private void getGroupStringIfPossible(String pathName, ReportEntry entry) {
		String groupKey = "adaptability-data-";
		if (pathName.contains(groupKey)) {
			int indexOfGroupKey = pathName.indexOf(groupKey);
			String group = pathName.substring(
					indexOfGroupKey + groupKey.length(), indexOfGroupKey
							+ groupKey.length() + 10);
			entry.addVariable("group", group);
		}
	}

	private void populateReportEntry(ReportEntry entry) {

		Document dom = getDom(entry.getFileName());

		entry.addVariable("isCorrectNamespace",
				inspector.isCorrectNamespace(dom));
		Node process = inspector.getProcess(dom);
		entry.addVariable("isExecutable", inspector.isExecutable(process));
		entry.addVariable("elements", inspector.getNumberOfChildren(process)
				+ "");
		entry.addVariable("referencesIssuesFound",
				inspector.hasReferenceIssues(entry.getFileName()));

		Map<String, AtomicInteger> documentElements = nodeCounter
				.addToCounts(dom);
		testMetrics.forEach(metric -> addAdaptability(entry, documentElements,
				metric));
	}

	private void addAdaptability(ReportEntry entry,
			Map<String, AtomicInteger> documentElements,
			AdaptabilityMetric metric) {
		double adaptabilityDegree = metric
				.computeAdaptability(documentElements);
		if (adaptabilityDegree == -1) {
			entry.addVariable(metric.getIdentifier(), "NA");
			// throw new AnalysisException("No elements found");
		} else {
			entry.addVariable(metric.getIdentifier(), adaptabilityDegree + "");
		}
	}

	protected Document getDom(String file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db;
		Document dom = null;

		try (FileInputStream fis = new FileInputStream(new File(file))) {
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(new ErrorHandler() {

				@Override
				public void error(SAXParseException arg0) throws SAXException {
					throw arg0;
				}

				@Override
				public void fatalError(SAXParseException arg0)
						throws SAXException {
					throw arg0;
				}

				@Override
				public void warning(SAXParseException arg0) throws SAXException {
				}
			});

			dom = db.parse(fis);

		} catch (ParserConfigurationException | SAXException | IOException e) {

		}
		return dom;
	}

	@Override
	public void traversalCompleted() {
		nodeCounter.writeToCsv(Paths.get(RAW_DATA_FILE));
	}
}
