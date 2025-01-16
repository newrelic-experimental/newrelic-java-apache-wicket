package org.apache.wicket;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class IRequestListener {

    @Trace
    public void onRequest() {
        NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Wicket", "IRequestListener", getClass().getSimpleName(), "onRequest");
        Weaver.callOriginal();
    }
}