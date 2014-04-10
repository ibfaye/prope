package pete.metrics.adaptability.elements;


class EventElements extends ElementsCollection {

	public EventElements() {
		buildTopLevelEvents();
		buildEventSubProcessEvents();
	}

	private void buildTopLevelEvents() {
		buildNoneEndEvent();
		buildErrorBoundaryEvent();
		buildNoneStartEvent();
		buildMessageStartEvent();
		buildTimerStartEvent();
		buildConditionalStartEvent();
		buildSignalStartEvent();
		buildMultipleStartEvent();
		buildMultipleParallelStartEvent();
		buildSubProcessStartEvent();
	}

	private void buildEventSubProcessEvents() {
		buildEventSubProcessNonInterruptingMessageStartEvent();
		buildEventSubProcessInterruptingMessageStartEvent();
	}

	private void buildEventSubProcessNonInterruptingMessageStartEvent() {
		AdaptableElement nonInterruptingMessageStartEvent = new AdaptableElement(
				"nonInterruptingMesssageStartEvent");
		nonInterruptingMessageStartEvent
		.setLocatorExpression(buildEventSubProcessNonInterruptingStartEventXPathExpression("message"));
		nonInterruptingMessageStartEvent
		.setDocumentation("A non-interrupting message start event can be adapted to another non-interrupting start event that uses an active trigger");
		nonInterruptingMessageStartEvent.addAdaption("escalationStartEvent");
		nonInterruptingMessageStartEvent.addAdaption("signalStartEvent");
		nonInterruptingMessageStartEvent.addAdaption("conditionalStartEvent");
		nonInterruptingMessageStartEvent.addAdaption("multipleStartEvent");
		nonInterruptingMessageStartEvent
		.addAdaption("multipleParallelStartEvent");
		add(nonInterruptingMessageStartEvent);
	}

	private void buildEventSubProcessInterruptingMessageStartEvent() {
		AdaptableElement interruptingMessageStartEvent = new AdaptableElement(
				"interruptingMesssageStartEvent");
		interruptingMessageStartEvent
		.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("message"));
		interruptingMessageStartEvent
		.setDocumentation("An interrupting message start event can be adapted to another interrupting start event that uses an active trigger");
		interruptingMessageStartEvent.addAdaption("escalationStartEvent");
		interruptingMessageStartEvent.addAdaption("errorStartEvent");
		interruptingMessageStartEvent.addAdaption("signalStartEvent");
		interruptingMessageStartEvent.addAdaption("conditionalStartEvent");
		interruptingMessageStartEvent.addAdaption("multipleStartEvent");
		interruptingMessageStartEvent.addAdaption("multipleParallelStartEvent");
		add(interruptingMessageStartEvent);
	}

	private void buildErrorBoundaryEvent() {
		AdaptableElement errorBoundaryEvent = new AdaptableElement(
				"errorBoundaryEvent");
		errorBoundaryEvent
		.setLocatorExpression(buildBoundaryEventXPathExpression("error"));
		errorBoundaryEvent.addAdaption("messageBoundaryEvent");
		errorBoundaryEvent.addAdaption("escalationBoundaryEvent");
		errorBoundaryEvent.addAdaption("conditionalBoundaryEvent");
		errorBoundaryEvent.addAdaption("signalBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleParallelBoundaryEvent");
		add(errorBoundaryEvent);
	}

	private void buildNoneEndEvent() {
		AdaptableElement noneEndEvent = new AdaptableElement("noneEndEvent");
		noneEndEvent
		.setLocatorExpression("/*[local-name() = 'process']/*[local-name() = 'endEvent' and not(child::*[contains(local-name(),'ventDefinition')])]");
		noneEndEvent.addAdaption("messageEndEvent");
		noneEndEvent.addAdaption("signalEndEvent");
		noneEndEvent.addAdaption("terminateEndEvent");
		noneEndEvent.addAdaption("multipleEndEvent");
		add(noneEndEvent);
	}

	private void buildNoneStartEvent() {
		AdaptableElement noneStartEvent = new AdaptableElement("noneStartEvent");
		noneStartEvent
		.setLocatorExpression("/*[local-name() = 'process']/*[local-name() = 'startEvent' and not(/*[contains(local-name(),'ventDefinition')])]");
		noneStartEvent.addAdaption("messageStartEvent");
		noneStartEvent.addAdaption("conditionalStartEvent");
		noneStartEvent.addAdaption("signalStartEvent");
		noneStartEvent.addAdaption("multipleStartEvent");
		noneStartEvent.addAdaption("parallelMultipleStartEvent");
		add(noneStartEvent);
	}

	private void buildMessageStartEvent() {
		AdaptableElement messageStartEvent = new AdaptableElement(
				"messageStartEvent");
		messageStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("message"));
		messageStartEvent
		.addAdaption("A messageStartEvent can be adapted to another startEvent that is triggered in some fashion.");
		messageStartEvent.addAdaption("conditionalStartEvent");
		messageStartEvent.addAdaption("signalStartEvent");
		messageStartEvent.addAdaption("multipleStartEvent");
		messageStartEvent.addAdaption("parallelMultipleStartEvent");
		add(messageStartEvent);
	}

	private void buildTimerStartEvent() {
		AdaptableElement timerStartEvent = new AdaptableElement(
				"timerStartEvent");
		timerStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("timer"));
		timerStartEvent
		.addAdaption("A timerStartEvent can be adapted to another startEvent that is triggered in some fashion, as it is possible to calculate the expiration of the time and trigger the event when it does");
		timerStartEvent.addAdaption("conditionalStartEvent");
		timerStartEvent.addAdaption("messageStartEvent");
		timerStartEvent.addAdaption("signalStartEvent");
		timerStartEvent.addAdaption("multipleStartEvent");
		timerStartEvent.addAdaption("parallelMultipleStartEvent");
		add(timerStartEvent);
	}

	private void buildConditionalStartEvent() {
		AdaptableElement conditionalStartEvent = new AdaptableElement(
				"conditionalStartEvent");
		conditionalStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("conditional"));
		conditionalStartEvent
		.addAdaption("A conditionalStartEvent can be adapted to another startEvent that is triggered in some fashion");
		conditionalStartEvent.addAdaption("messageStartEvent");
		conditionalStartEvent.addAdaption("signalStartEvent");
		conditionalStartEvent.addAdaption("multipleStartEvent");
		conditionalStartEvent.addAdaption("parallelMultipleStartEvent");
		add(conditionalStartEvent);
	}

	private void buildSignalStartEvent() {
		AdaptableElement signalStartEvent = new AdaptableElement(
				"signalStartEvent");
		signalStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("signal"));
		signalStartEvent
		.addAdaption("A signalStartEvent can be adapted to another startEvent that is triggered in some fashion");
		signalStartEvent.addAdaption("messageStartEvent");
		signalStartEvent.addAdaption("conditionalStartEvent");
		signalStartEvent.addAdaption("multipleStartEvent");
		signalStartEvent.addAdaption("parallelMultipleStartEvent");
		add(signalStartEvent);
	}

	private void buildMultipleStartEvent() {
		AdaptableElement multipleStartEvent = new AdaptableElement(
				"multipleStartEvent");
		multipleStartEvent
		.setLocatorExpression("//*[local-name() = 'startEvent' and not(@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleStartEvent
		.addAdaption("A multipleStartEvent can be adapted to multiple alternative start events");
		multipleStartEvent.addAdaption("messageStartEvent");
		multipleStartEvent.addAdaption("conditionalStartEvent");
		multipleStartEvent.addAdaption("signalStartEvent");
		multipleStartEvent.addAdaption("timerStartEvent");
		multipleStartEvent.addAdaption("noneStartEvent");
		add(multipleStartEvent);
	}

	private void buildMultipleParallelStartEvent() {
		AdaptableElement multipleParallelStartEvent = new AdaptableElement(
				"multipleParallelStartEvent");
		multipleParallelStartEvent
		.setLocatorExpression("//*[local-name() = 'startEvent' and @parallelMultiple = 'true' and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleParallelStartEvent
		.addAdaption("A multipleParallelStartEvent cannot be adapted since there is no other way to avoid the instantiation of a process unless multiple conditions are satisfied");
		add(multipleParallelStartEvent);
	}

	private void buildSubProcessStartEvent() {
		AdaptableElement subProcessStartEvent = new AdaptableElement(
				"subProcessStartEvent");
		subProcessStartEvent
		.setLocatorExpression("//*[local-name() = 'subProcess' and not(@triggeredByEvent = 'true')]/*[local-name() = 'startEvent']");
		subProcessStartEvent
		.addAdaption("A startEvent of an ordinary subProcess cannot be adapted since it is the only way of starting non-eventSubProcesses");
		add(subProcessStartEvent);
	}

	private String buildStartEventXPathExpression(String eventType) {
		return buildEventXPathExpression("start", eventType);
	}

	private String buildEndEventXPathExpression(String eventType) {
		return buildEventXPathExpression("end", eventType);
	}

	private String buildBoundaryEventXPathExpression(String eventType) {
		return buildEventXPathExpression("boundary", eventType);
	}

	private String buildEventXPathExpression(String event, String eventType) {
		return "//*[local-name() = '"
				+ event
				+ "Event' and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

	private String buildEventSubProcessNonInterruptingStartEventXPathExpression(
			String eventType) {
		return "//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

	private String buildEventSubProcessInterruptingStartEventXPathExpression(
			String eventType) {
		return "//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and @isInterrupting = 'true' and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

}
