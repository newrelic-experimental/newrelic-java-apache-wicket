package org.apache.wicket.protocol.http;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class WebApplication {

	@Trace
	protected void init() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "WebApplication",
				getClass().getSimpleName(), "init");
		Weaver.callOriginal();
	}
}