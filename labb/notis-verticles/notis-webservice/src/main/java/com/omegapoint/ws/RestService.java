package com.omegapoint.ws;

import io.vertx.ext.web.RoutingContext;

public interface RestService {
    public void sendPushToGCM(RoutingContext routingContext);
    public void sendPushToAPNS(RoutingContext routingContext);
}
