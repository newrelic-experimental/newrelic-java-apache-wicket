package org.apache.wicket;

import org.apache.wicket.event.IEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Component {

	@Trace
	protected void onInitialize() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Component", getClass().getSimpleName(),
				"onInitialize");
		Weaver.callOriginal();
	}

	@Trace
	protected void onBeforeRender() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Component", getClass().getSimpleName(),
				"onBeforeRender");
		Weaver.callOriginal();
	}

	@Trace
	protected void onAfterRender() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Component", getClass().getSimpleName(),
				"onAfterRender");
		Weaver.callOriginal();
	}

	@Trace
	public void onEvent(IEvent<?> event) {
		String eventName = event.getPayload().getClass().getSimpleName();
		String componentName = this.getClass().getSimpleName();
		String metricName = "Custom/Wicket/Event/" + componentName + "/" + eventName;
		NewRelic.recordMetric(metricName, 1F);
		Weaver.callOriginal();
	}
}