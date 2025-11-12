package org.apache.wicket.request.cycle;

import java.util.Map;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.wicket.Utils;
import com.newrelic.instrumentation.labs.wicket.WicketRequestHeaders;

@Weave
public abstract class RequestCycle {

	public abstract Request getRequest();

	public abstract Response getResponse();

	@Trace(dispatcher = true)
	protected void onBeginRequest() {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		Request request = getRequest();
		if (request instanceof WebRequest) {
			WicketRequestHeaders headers = new WicketRequestHeaders((WebRequest) request);
			transaction.acceptDistributedTraceHeaders(TransportType.HTTP, headers);

			// Utils.setTransactionName((WebRequest) request);
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "RequestCycle",
				getClass().getSimpleName(), "onBeginRequest");
		Weaver.callOriginal();
	}

	@Trace
	protected void onEndRequest() {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		Response response = getResponse();
		if (response instanceof WebResponse) {
			Map<String, Object> attributes = Utils.getAttributes(response);
			if (attributes != null) {
				NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
			}
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "RequestCycle",
				getClass().getSimpleName(), "onEndRequest");
		Weaver.callOriginal();
	}

}