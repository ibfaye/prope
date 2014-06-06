package prope.metrics.adaptability;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

interface AdaptabilityMetric {

	public double computeAdaptability(Map<String, AtomicInteger> processElements);

	public String getIdentifier();

}
