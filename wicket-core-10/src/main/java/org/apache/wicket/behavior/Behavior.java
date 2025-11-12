package org.apache.wicket.behavior;

import org.apache.wicket.Component;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Behavior {

	@Trace
	public void beforeRender(Component component) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Behavior", getClass().getSimpleName(),
				"beforeRender");
		Weaver.callOriginal();
	}

	@Trace
	public void afterRender(Component component) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Behavior", getClass().getSimpleName(),
				"afterRender");
		Weaver.callOriginal();
	}
}