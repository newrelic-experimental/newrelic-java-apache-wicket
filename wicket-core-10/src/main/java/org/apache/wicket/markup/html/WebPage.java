package org.apache.wicket.markup.html;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class WebPage {

	@Trace
	protected void onRender() {
		try {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "WebPage",
					getClass().getSimpleName(), "onRender");
			Weaver.callOriginal();
		} catch (Exception e) {
			NewRelic.noticeError(e);
			throw e;
		}
	}

	@Trace
	protected void onAfterRender() {
		try {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "WebPage",
					getClass().getSimpleName(), "onAfterRender");
			Weaver.callOriginal();
		} catch (Exception e) {
			NewRelic.noticeError(e);
			throw e;
		}
	}
}