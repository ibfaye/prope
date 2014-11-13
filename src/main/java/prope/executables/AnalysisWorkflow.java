package prope.executables;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import prope.metrics.adaptability.AdaptabilityAnalyzer;
import prope.metrics.installability.deployability.DeploymentPackageAnalyzer;
import prope.metrics.installability.server.AverageInstallationTimeCalculator;
import prope.metrics.portability.PortabilityAnalyzer;
import prope.reporting.Report;
import prope.reporting.ReportWriter;

public final class AnalysisWorkflow {

	private final Path root;

	private final DirectoryAnalyzer dirAnalyzer;

	private FileAnalyzer fileAnalyzer;

	private Report report;

	private long startTime;

	public AnalysisWorkflow(Path root, AnalysisType type) {
		this.root = root;

		if (!Files.exists(root)) {
			throw new IllegalArgumentException("path " + root.toString()
					+ " does not exist");
		}

		if (AnalysisType.DEPLOYABILITY.equals(type)) {
			fileAnalyzer = new DeploymentPackageAnalyzer();
		} else if (AnalysisType.INSTALLABILITY.equals(type)) {
			fileAnalyzer = new AverageInstallationTimeCalculator();
		} else if (AnalysisType.PORTABILITY.equals(type)) {
			fileAnalyzer = new PortabilityAnalyzer();
		} else if (AnalysisType.ADAPTABILITY.equals(type)) {
			fileAnalyzer = new AdaptabilityAnalyzer();
		} else if (AnalysisType.UNKNOWN.equals(type)) {
			throw new AnalysisException("no valid AnalysisType found");
		}
		dirAnalyzer = new DirectoryAnalyzer(fileAnalyzer);
	}

	public void start() {
		startWatch();

		if (!Files.isDirectory(root)) {
			parseFile(root);
		} else {
			parseDirectory(root);
		}

		stopWatch();

		writeResults();

		purgeTempDir();
	}

	private void stopWatch() {
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("=======================================");
		System.out.println("Analysis finished; Duration: " + duration
				+ " millisec; writing results");
	}

	private void startWatch() {
		startTime = System.currentTimeMillis();
	}

	private void parseFile(Path file) {
		report = new Report();
		fileAnalyzer.analyzeFile(file).forEach(
				reportEntry -> report.addEntry(reportEntry));
	}

	private void parseDirectory(Path root) {
		report = dirAnalyzer.analyzeDirectory(root);
	}

	private void writeResults() {
		fileAnalyzer.traversalCompleted();
		ReportWriter writer = new ReportWriter(report);
		writer.writeToExcelFile("results.csv");
	}

	private void purgeTempDir() {
		try {
			FileUtils.deleteDirectory(new File("tmp"));
		} catch (IOException ignore) {
			// Not critical -> ignore
		}
	}
}
