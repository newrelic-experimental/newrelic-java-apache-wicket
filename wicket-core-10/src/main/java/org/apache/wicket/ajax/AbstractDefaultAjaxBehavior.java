package org.apache.wicket.ajax;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class AbstractDefaultAjaxBehavior {

	@Trace
	protected void onBind() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "AbstractDefaultAjaxBehavior",
				getClass().getSimpleName(), "onBind");
		Weaver.callOriginal();
	}

	@Trace
	public void onRequest() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "AbstractDefaultAjaxBehavior",
				getClass().getSimpleName(), "onRequest");
		Weaver.callOriginal();
	}
}
