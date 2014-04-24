package pete.metrics.adaptability;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import pete.metrics.adaptability.elements.AdaptableElement;
import pete.metrics.adaptability.elements.AdaptableElements;

class BinaryAdaptabilityMetric implements AdaptabilityMetric {

	private Map<String, AdaptableElement> elements;

	private int referenceScore;

	private AtomicInteger sum;

	private AtomicInteger maxDegree;

	public BinaryAdaptabilityMetric() {
		elements = new AdaptableElements().getElementsByName();
		referenceScore = elements.values().parallelStream().filter(element -> !element.isForDetectionOnly())
				.mapToInt(element -> element.getAdaptabilityScore()).max()
				.getAsInt();
	}

	@Override
	public double computeAdaptability(Map<String, AtomicInteger> processElements) {
		sum = new AtomicInteger(0);
		maxDegree = new AtomicInteger(0);
		processElements.keySet().forEach(
				elementName -> addElementAdaptability(processElements,
						elementName));
		if (maxDegree.get() == 0) {
			return 0;
		} else {
			return sum.doubleValue() / maxDegree.doubleValue();
		}
	}

	private void addElementAdaptability(
			Map<String, AtomicInteger> processElements, String elementName) {
		AdaptableElement element = elements.get(elementName);
		if(element.isForDetectionOnly()){
			return;
		}

		int number = processElements.get(elementName).get();
		int elementScore = element.getAdaptabilityScore();

		if(elementScore >= (referenceScore * 0.4)){
			sum.addAndGet(number);
		}

		maxDegree.addAndGet(number);
	}

}
