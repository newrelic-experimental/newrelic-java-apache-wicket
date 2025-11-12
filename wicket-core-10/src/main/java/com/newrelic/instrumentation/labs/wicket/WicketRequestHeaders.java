package com.newrelic.instrumentation.labs.wicket;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.request.http.WebRequest;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class WicketRequestHeaders implements Headers {

	private final WebRequest request;

	public WicketRequestHeaders(WebRequest req) {
		this.request = req;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		return request.getHeader(name);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> headers = request.getHeaders(name);
		return headers != null ? headers : Collections.emptyList();
	}

	@Override
	public void setHeader(String name, String value) {
		// Wicket's WebRequest does not support setting headers directly
	}

	@Override
	public void addHeader(String name, String value) {
		// Wicket's WebRequest does not support adding headers directly
	}

	@Override
	public Collection<String> getHeaderNames() {
		// Wicket's WebRequest does not support adding headers directly }
		return null;
	}

	@Override
	public boolean containsHeader(String name) {
		return request.getHeader(name) != null;
	}
}