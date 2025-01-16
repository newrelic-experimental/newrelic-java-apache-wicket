package org.apache.wicket;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Session {

	@Trace
	public void bind() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Session", getClass().getSimpleName(),
				"bind");
		Weaver.callOriginal();
	}

	@Trace
	public void endRequest() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Session", getClass().getSimpleName(),
				"endRequest");
		Weaver.callOriginal();
	}

	@Trace
	public void invalidate() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Session", getClass().getSimpleName(),
				"invalidate");
		Weaver.callOriginal();
	}
}