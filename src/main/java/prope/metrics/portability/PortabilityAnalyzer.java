package prope.metrics.portability;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import prope.executables.FileAnalyzer;
import prope.reporting.ReportEntry;

public final class PortabilityAnalyzer implements FileAnalyzer {

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {

		System.out.println("Analyzing " + filePath + " for portability");
		ReportEntry entry = populateEntry(filePath);

		List<ReportEntry> report = new ArrayList<ReportEntry>();
		report.add(entry);
		return report;
	}

	private ReportEntry populateEntry(Path filePath) {
		bpp.executables.FileAnalyzer bppAnalyzer = new bpp.executables.FileAnalyzer(
				filePath.toString(), false);
		bpp.domain.AnalysisResult analysisResult = bppAnalyzer.analyze();
		ReportEntry entry = new ReportEntry(analysisResult.getBpelFile()
				.toString());
		entry.addVariable("class", analysisResult.getClassification()
				.toString());
		entry.addVariable("N", analysisResult.getNumberOfElements() + "");
		entry.addVariable("Mb", analysisResult.getBasicPortabilityMetric() + "");
		entry.addVariable("Me", analysisResult.getWeightedElementsMetric() + "");
		entry.addVariable("Ma", analysisResult.getActivityPortabilityMetric()
				+ "");
		entry.addVariable("Ms",
				analysisResult.getServiceCommunicationPortabilityMetric() + "");
		return entry;
	}

	@Override
	public void traversalCompleted() {
		// Not needed here
	}

}
