package org.apache.wicket;

import com.newrelic.api.agent.weaver.MatchType;
import org.apache.wicket.event.IEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "org.apache.wicket.Component",type = MatchType.BaseClass)
public abstract class Component_Instrumentation {

	@Trace
    public final void render() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Component", getClass().getSimpleName(),
				"render");

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