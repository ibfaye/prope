package prope.metrics.portability;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import prope.reporting.ReportEntry;
import bpp.domain.PortabilityLevel;
import bpp.domain.metrics.ActivityPortabilityMetric;
import bpp.domain.metrics.BasicPortabilityMetric;
import bpp.domain.metrics.ServiceCommunicationPortabilityMetric;
import bpp.domain.metrics.WeightedElementsPortabilityMetric;

public class PortabilityAnalyzerTests {

	private PortabilityAnalyzer sut;

	@Before
	public void setUp() {
		sut = new PortabilityAnalyzer();
	}

	@Test
	public void testBasicFile() {
		ReportEntry result = sut.analyzeFile(
				Paths.get("src/test/resources/portability/Invoke-Empty.bpel"))
				.get(0);

		assertClassification(result, PortabilityLevel.LIMITED_PORTABILITY);
		assertNumberOfElements(result, 17);
		assertBasicPortability(result, 0.941);
		assertWeightedPortability(result, 0.967);
		assertActivityPortability(result, 0.888);
		assertServicePortability(result, 0.814);
	}
	
	@Test
	public void checkWarnings() {
		sut.analyzeFile(
				Paths.get("src/test/resources/portability/Invoke-Empty.bpel"))
				.get(0);
		assertEquals(1, sut.getWarningsFromLastAnalysis().size());
	}

	private void assertNumberOfElements(ReportEntry entry, int numberOfElements) {
		assertEquals(Integer.parseInt(entry.getVariableValue("N")),
				numberOfElements);
	}

	private void assertClassification(ReportEntry entry,
			PortabilityLevel expected) {
		assertEquals(expected.toString(), entry.getVariableValue("class"));
	}

	private void assertBasicPortability(ReportEntry entry, double expected) {
		assertEquals(expected, Double.parseDouble(entry
				.getVariableValue(BasicPortabilityMetric.getLabel())), 0.001);
	}

	private void assertWeightedPortability(ReportEntry entry, double expected) {
		assertEquals(expected,
				Double.parseDouble(entry
						.getVariableValue(WeightedElementsPortabilityMetric
								.getLabel())), 0.001);
	}

	private void assertActivityPortability(ReportEntry entry, double expected) {
		assertEquals(expected, Double.parseDouble(entry
				.getVariableValue(ActivityPortabilityMetric.getLabel())), 0.001);
	}

	private void assertServicePortability(ReportEntry entry, double expected) {
		assertEquals(expected, Double.parseDouble(entry
				.getVariableValue(ServiceCommunicationPortabilityMetric
						.getLabel())), 0.001);
	}
}
