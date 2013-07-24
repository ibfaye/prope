package test.pete.executables;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import pete.executables.DirectoryAnalyzer;
import pete.metrics.installability.deployability.DeploymentPackageAnalyzer;
import pete.metrics.installability.server.AverageInstallationTimeCalculator;
import pete.reporting.Report;

public class DirectoryAnalyzerTests {

	private DirectoryAnalyzer sut;

	@Before
	public void setUp() {
	}

	@Test
	public void testDeployability() {
		sut = new DirectoryAnalyzer(new DeploymentPackageAnalyzer());
		Report report = sut.analyzeDirectory(Paths
				.get("src/test/resources/installability/deployment"));
		assertEquals(265, report.getSummedVariable("deploymentEffort"));
	}

	@Test
	public void testServerInstallability() {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths
				.get("src/test/resources/installability/server/"));
		assertEquals("2,88", report.getEntries().get(0).getVariableValue("AIT"));
		assertEquals("1,00", report.getEntries().get(0).getVariableValue("ESR"));
	}

}
