package org.apache.wicket.request.cycle;

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
}