package org.apache.wicket.markup.html.panel;

import org.apache.wicket.markup.IMarkupFragment;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Panel {

	@Trace
	public IMarkupFragment getRegionMarkup() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Panel", getClass().getSimpleName(),
				"getRegionMarkup");
		return Weaver.callOriginal();
	}
}