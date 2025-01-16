package com.newrelic.instrumentation.labs.wicket;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;

public class Utils {

	public static void addRequest(Map<String, Object> attributes, WebRequest request) {
		if (request != null) {
			// addAttribute(attributes, "Request-Method", request.; // Method not available
			addAttribute(attributes, "Request-Path", request.getUrl().toString());
			addAttribute(attributes, "Request-RemoteHost", request.getClientUrl().getHost());
			int port = request.getClientUrl().getPort();
			addAttribute(attributes, "Request-RemotePort", port != -1 ? port : "default");
		}
	}

	public static void addResponse(Map<String, Object> attributes, WebResponse response) {

		if (response != null) {
			// Add other response attributes if necessary
			// Since WebResponse does not have a getStatus method, we will not add status
			// code here

		}
	}

	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if (attributes != null && key != null && !key.isEmpty() && value != null) {
			attributes.put(key, value);
		}
	}

	public static void _setTransactionName(WebRequest request) {
		String fullUrl = request.getUrl().toString();
		if (fullUrl != null && fullUrl.length() > 0) {
			fullUrl = request.getClientUrl().toString();
		}

		try {
			// Use java.net.URL to parse the full URL
			URL url = new URL(fullUrl);
			// Extract the path without the query string
			String path = url.getPath();

			if (path != null) {
				if (path.equals("") || path.equals("/")) {
					path = "Root";
				}
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false,
						"Wicket", "Request", path);
			}
		} catch (MalformedURLException e) {
			// Handle the exception if the URL is malformed
			NewRelic.noticeError(e);
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false,
					"Wicket", "Request", "InvalidURL");
		}
	}

	public static Map<String, Object> getAttributes(Object obj) {
		HashMap<String, Object> attributes = new HashMap<>();
		if (obj instanceof WebRequest) {
			addRequest(attributes, (WebRequest) obj);
		}
		if (obj instanceof WebResponse) {
			addResponse(attributes, (WebResponse) obj);
		}
		return attributes.isEmpty() ? null : attributes;
	}
}