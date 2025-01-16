package org.apache.wicket.behavior;

import org.apache.wicket.Component;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Behavior {

    @Trace
    public void beforeRender(Component component) {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Behavior", "beforeRender");
        Weaver.callOriginal();
    }

    @Trace
    public void afterRender(Component component) {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Behavior", "afterRender");
        Weaver.callOriginal();
    }
}package org.apache.wicket;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Component {

    @Trace
    protected void onInitialize() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Component", "onInitialize");
        Weaver.callOriginal();
    }

    @Trace
    protected void onBeforeRender() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Component", "onBeforeRender");
        Weaver.callOriginal();
    }

    @Trace
    protected void onAfterRender() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Component", "onAfterRender");
        Weaver.callOriginal();
    }
}package org.apache.wicket.protocol.http;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class WebApplication {

    @Trace(dispatcher = true)
    protected void init() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "WebApplication", "init");
        Weaver.callOriginal();
    }
}package org.apache.wicket.markup.html.form;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Form<T> {

    @Trace
    protected void onSubmit() {
        try {
            NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Form", "onSubmit");
            Weaver.callOriginal();
        } catch (Exception e) {
            NewRelic.noticeError(e);
            throw e;
        }
    }
}package org.apache.wicket.markup.html.panel;

import org.apache.wicket.markup.IMarkupFragment;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Panel {

	@Trace
    public IMarkupFragment getRegionMarkup() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Panel", "getRegionMarkup");
        return Weaver.callOriginal();
    }
}package org.apache.wicket.markup.html;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class WebPage {


    @Trace
    protected void onRender(){
        try {
            NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "WebPage", "onRender");
            Weaver.callOriginal();
        } catch (Exception e) {
            NewRelic.noticeError(e);
            throw e;
        }
    }

    @Trace
    protected void onAfterRender() {
        try {
            NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "WebPage", "onAfterRender");
            Weaver.callOriginal();
        } catch (Exception e) {
            NewRelic.noticeError(e);
            throw e;
        }
    }
}package org.apache.wicket.request.cycle;

import java.util.Map;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.wicket.Utils;
import com.newrelic.instrumentation.labs.wicket.WicketRequestHeaders;

@Weave
public abstract class RequestCycle {

    public abstract Request getRequest();

    public  abstract Response getResponse();
    
	@Trace(dispatcher = true)
	protected void onBeginRequest() {
        Transaction transaction = NewRelic.getAgent().getTransaction();
        Request request = getRequest();
        if (request instanceof WebRequest) {
            WicketRequestHeaders headers = new WicketRequestHeaders((WebRequest) request);
            transaction.insertDistributedTraceHeaders(headers);
            Utils.setTransactionName((WebRequest) request);
        }
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "RequestCycle", "onBeginRequest");
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
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "RequestCycle", "onEndRequest");
        Weaver.callOriginal();
    }


}package org.apache.wicket.request.cycle;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class IRequestCycleListener {

    @Trace
    public void onBeginRequest(RequestCycle cycle) {
    	
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "IRequestCycleListener", getClass().getSimpleName(),"onBeginRequest");
    	Weaver.callOriginal();
    }

    @Trace
    public void onEndRequest(RequestCycle cycle) {
    	NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "IRequestCycleListener", getClass().getSimpleName(),"onEndRequest");
    	Weaver.callOriginal();
    }
}package org.apache.wicket;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Session {

    @Trace
    public void bind() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Session", "bind");
        Weaver.callOriginal();
    }

    @Trace
    public void endRequest() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Session", "endRequest");
        Weaver.callOriginal();
    }

    @Trace
    public void invalidate() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "Session", "invalidate");
        Weaver.callOriginal();
    }
}package com.newrelic.instrumentation.labs.wicket;

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
}package com.newrelic.instrumentation.labs.wicket;

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
                // Since WebResponse does not have a getStatus method, we will not add status code here
     
        }
    }

    public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
        if (attributes != null && key != null && !key.isEmpty() && value != null) {
            attributes.put(key, value);
        }
    }

    public static void setTransactionName(WebRequest request) {
        String path = request.getUrl().toString();
        if (path != null) {
            if (path.equals("") || path.equals("/")) {
                path = "Root";
            }
            NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false,
                    "Wicket", "Request", path);
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