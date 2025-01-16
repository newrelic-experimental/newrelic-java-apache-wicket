package org.apache.wicket.markup.html.form;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Form<T> {

	@Trace
	protected void onSubmit() {
		try {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Form", getClass().getSimpleName(),
					"onSubmit");
			Weaver.callOriginal();
		} catch (Exception e) {
			NewRelic.noticeError(e);
			throw e;
		}
	}
}