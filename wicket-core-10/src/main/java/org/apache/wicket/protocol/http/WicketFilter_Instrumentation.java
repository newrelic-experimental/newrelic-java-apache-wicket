package org.apache.wicket.protocol.http;

import java.io.IOException;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Weave (originalName = "org.apache.wicket.protocol.http.WicketFilter", type = MatchType.BaseClass)

public abstract class WicketFilter_Instrumentation {

    public abstract String getFilterPath();

	@Trace
    boolean processRequest(ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

		try {
			if (request instanceof HttpServletRequest) {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				String path = httpRequest.getRequestURI();
				String method = httpRequest.getMethod();
				String transactionName = createDynamicTransactionName(path);

				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false,
						"Wicket", method + " ", transactionName);
			}

			NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "WicketFilter",
					getClass().getSimpleName(), "processRequest");

            NewRelic.getAgent().getTracedMethod().addCustomAttribute("FilterPath", getFilterPath());
			return Weaver.callOriginal();
		} catch (Exception e) {
			NewRelic.noticeError(e);
			throw e;
		}
	}

	private String createDynamicTransactionName(String path) {
		// Split the path into tokens
		String[] tokens = path.split("/");
		StringBuilder transactionName = new StringBuilder();

		int tokenCount = 0;
		for (String token : tokens) {
			// Skip empty tokens and tokens containing dots
			if (!token.isEmpty() && !token.contains(".")) {
				if (transactionName.length() > 0) {
					transactionName.append("/");
				}
				transactionName.append(token);
				tokenCount++;
				// Stop after adding three tokens
				if (tokenCount >= 3) {
					break;
				}
			}
		}

		// Ensure the transaction name starts with a slash
		return transactionName.length() > 0 ? transactionName.toString() : "/";
	}
}
