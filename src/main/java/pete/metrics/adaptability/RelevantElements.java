package pete.metrics.adaptability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RelevantElements {

	private final List<AdaptableElement> elements;

	public RelevantElements() {
		elements = new ArrayList<>();
		elements.add(new AdaptableElement("activity"));
		elements.add(new AdaptableElement("adHocSubProcess"));
		elements.add(new AdaptableElement("boundaryEvent"));
		elements.add(new AdaptableElement("businessRuleTask"));
		elements.add(new AdaptableElement("callActivity"));
		elements.add(new AdaptableElement("cancelEventDefinition"));
		elements.add(new AdaptableElement("catchEvent"));
		elements.add(new AdaptableElement("compensateEventDefinition"));
		elements.add(new AdaptableElement("complexGateway"));
		elements.add(new AdaptableElement("conditionalEventDefinition"));
		elements.add(new AdaptableElement("dataAssociation"));
		elements.add(new AdaptableElement("dataInput"));
		elements.add(new AdaptableElement("dataInputAssociation"));
		elements.add(new AdaptableElement("dataObject"));
		elements.add(new AdaptableElement("dataOutput"));
		elements.add(new AdaptableElement("dataOutputAssociation"));
		elements.add(new AdaptableElement("dataStore"));
		buildNoneEndEvent();
		elements.add(new AdaptableElement("endEvent"));
		elements.add(new AdaptableElement("error"));
		buildErrorBoundaryEvent();
		elements.add(new AdaptableElement("errorEventDefinition"));
		elements.add(new AdaptableElement("escalation"));
		elements.add(new AdaptableElement("escalationEventDefinition"));
		elements.add(new AdaptableElement("event"));
		elements.add(new AdaptableElement("eventBasedGateway"));
		elements.add(new AdaptableElement("extension"));
		elements.add(new AdaptableElement("extensionElements"));
		elements.add(new AdaptableElement("formalExpression"));
		elements.add(new AdaptableElement("gateway"));
		elements.add(new AdaptableElement("globalBusinessRuleTask"));
		elements.add(new AdaptableElement("globalManualTask"));
		elements.add(new AdaptableElement("globalScriptTask"));
		elements.add(new AdaptableElement("globalUserTask"));
		elements.add(new AdaptableElement("globalTask"));
		elements.add(new AdaptableElement("implicitThrowEvent"));
		elements.add(new AdaptableElement("inclusiveGateway"));
		elements.add(new AdaptableElement("inputSet"));
		elements.add(new AdaptableElement("intermediateCatchEvent"));
		elements.add(new AdaptableElement("intermediateThrowEvent"));
		elements.add(new AdaptableElement("lane"));
		elements.add(new AdaptableElement("manualTask"));
		elements.add(new AdaptableElement("message"));
		elements.add(new AdaptableElement("messageEventDefinition"));
		elements.add(new AdaptableElement("messageFlow"));
		elements.add(new AdaptableElement("messageFlowAssociation"));
		elements.add(new AdaptableElement("loopCharacteristics"));
		elements.add(new AdaptableElement("multiInstanceLoopCharacteristics"));
		elements.add(new AdaptableElement("process"));
		elements.add(new AdaptableElement("receiveTask"));
		buildScriptTask();
		elements.add(new AdaptableElement("script"));
		buildSendTask();
		elements.add(new AdaptableElement("sequenceFlow"));
		buildServiceTask();
		elements.add(new AdaptableElement("signal"));
		elements.add(new AdaptableElement("signalEventDefinition"));
		elements.add(new AdaptableElement("standardLoopCharacteristics"));
		buildLoopTask();
		buildNoneStartEvent();
		elements.add(new AdaptableElement("subProcess"));
		buildTask();
		elements.add(new AdaptableElement("terminateEventDefinition"));
		elements.add(new AdaptableElement("throwEvent"));
		elements.add(new AdaptableElement("timerEventDefinition"));
		elements.add(new AdaptableElement("transaction"));
		buildUserTask();
	}

	private void buildScriptTask() {
		AdaptableElement scriptTask = new AdaptableElement("scriptTask");
		scriptTask.setLocatorExpression("//*[local-name() = 'scriptTask']");
		// all tasks below can in principle be used to trigger the sending of a
		// message. A receiveTask is not suitable as it is passively waiting
		// and a businessRuleTask is too specific
		scriptTask.addAdaption("serviceTask");
		scriptTask.addAdaption("sendTask");
		scriptTask.addAdaption("userTask");
		scriptTask.addAdaption("manualTask");
		elements.add(scriptTask);
	}

	private void buildSendTask() {
		AdaptableElement sendTask = new AdaptableElement("sendTask");
		sendTask.setLocatorExpression("//*[local-name() = 'sendTask']");
		// all tasks below can in principle be used to trigger the sending of a
		// message. A receiveTask is not suitable as it is passively waiting
		// and a businessRuleTask is too specific
		sendTask.addAdaption("serviceTask");
		sendTask.addAdaption("scriptTask");
		sendTask.addAdaption("userTask");
		sendTask.addAdaption("manualTask");

		elements.add(sendTask);
	}

	private void buildServiceTask() {
		AdaptableElement serviceTask = new AdaptableElement("serviceTask");
		serviceTask.setLocatorExpression("//*[local-name() = 'serviceTask']");
		// all tasks below can in principle be used to trigger service
		// execution. A receiveTask is not suitable as it is passively waiting
		// and a businessRuleTask is too specific
		serviceTask.addAdaption("scriptTask");
		serviceTask.addAdaption("manualTask");
		serviceTask.addAdaption("userTask");
		serviceTask.addAdaption("sendTask");

		elements.add(serviceTask);
	}

	private void buildUserTask() {
		AdaptableElement userTask = new AdaptableElement("userTask");
		userTask.setLocatorExpression("//*[local-name() = 'userTask']");
		// the tasks below can in principle be programmed to ask for user input,
		// an hence are an adaption for userTask
		userTask.addAdaption("manualTask");
		userTask.addAdaption("scriptTask");
		userTask.addAdaption("serviceTask");
		userTask.addAdaption("sendTask");
		elements.add(userTask);
	}

	private void buildLoopTask() {
		AdaptableElement loopTask = new AdaptableElement("loopTask");
		loopTask.setLocatorExpression("//*[(local-name() = 'sendTask' or local-name() = 'receiveTask' or local-name() = 'serviceTask' or local-name() = 'manualTask' or local-name() = 'businessRuleTask' or local-name() = 'userTask' or local-name() = 'sendTask') and (child::*[local-name() = 'standardLoopCharacteristics'])]");
		loopTask.addAdaption("exclusiveGatewaysAndSequenceFlows");
		loopTask.addAdaption("loopSubProcess");
		loopTask.addAdaption("adHocSubProcess");
		loopTask.addAdaption("eventSubProcess");
		elements.add(loopTask);
	}

	private void buildErrorBoundaryEvent() {
		AdaptableElement errorBoundaryEvent = new AdaptableElement(
				"errorBoundaryEvent");
		errorBoundaryEvent
				.setLocatorExpression("//*[local-name() = 'boundaryEvent' and (child::*[local-name() = 'errorEventDefinition'])]");
		errorBoundaryEvent.addAdaption("messageBoundaryEvent");
		errorBoundaryEvent.addAdaption("escalationBoundaryEvent");
		errorBoundaryEvent.addAdaption("conditionalBoundaryEvent");
		errorBoundaryEvent.addAdaption("signalBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleParallelBoundaryEvent");
		elements.add(errorBoundaryEvent);
	}

	private void buildTask() {
		AdaptableElement task = new AdaptableElement("task");
		task.setLocatorExpression("//*[local-name() = 'task']");
		task.addAdaption("sendTask");
		task.addAdaption("serviceTask");
		task.addAdaption("manualTask");
		task.addAdaption("businessRuleTask");
		task.addAdaption("userTask");
		task.addAdaption("scriptTask");
		task.addAdaption("receiveTask");
		elements.add(task);
	}

	private void buildNoneEndEvent() {
		AdaptableElement noneEndEvent = new AdaptableElement("noneEndEvent");
		noneEndEvent
				.setLocatorExpression("/*[local-name() = 'endEvent' and not(child::*[contains(local-name(),'EventDefinition')])]");
		noneEndEvent.addAdaption("messageEndEvent");
		noneEndEvent.addAdaption("signalEndEvent");
		noneEndEvent.addAdaption("terminateEndEvent");
		noneEndEvent.addAdaption("multipleEndEvent");
		elements.add(noneEndEvent);
	}

	private void buildNoneStartEvent() {
		AdaptableElement noneStartEvent = new AdaptableElement("noneStartEvent");
		noneStartEvent
				.setLocatorExpression("/*[local-name() = 'startEvent' and not(/*[contains(local-name(),'EventDefinition')])]");
		noneStartEvent.addAdaption("messageStartEvent");
		noneStartEvent.addAdaption("conditionalStartEvent");
		noneStartEvent.addAdaption("signalStartEvent");
		noneStartEvent.addAdaption("multipleStartEvent");
		noneStartEvent.addAdaption("parallelMultipleStartEvent");
		elements.add(noneStartEvent);
	}

	public List<String> getElementNames() {
		List<String> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element.getName()));
		return result;
	}

	public List<AdaptableElement> getElements() {
		List<AdaptableElement> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element));
		return result;
	}

	public Map<String, AdaptableElement> getElementsByName() {
		HashMap<String, AdaptableElement> result = new HashMap<>();
		elements.forEach(element -> result.put(element.getName(), element));
		return result;
	}
}
