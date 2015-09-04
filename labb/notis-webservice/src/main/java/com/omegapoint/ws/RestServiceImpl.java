package com.omegapoint.ws;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RestServiceImpl extends AbstractVerticle implements RestService {

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/push/gcm/:message").handler(new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext routingContext) {
                RestServiceImpl.this.sendPushToGCM(routingContext);
            }
        });
        router.get("/push/apns/:message").handler(new Handler<RoutingContext>() {
            @Override
            public void handle(RoutingContext routingContext) {
                RestServiceImpl.this.sendPushToGCM(routingContext);
            }
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

    @Override
    public void sendPushToGCM(RoutingContext routingContext) {
        String message = routingContext.request().getParam("message");
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/html").end("Cooool");

        System.out.println("Message: "+message);
    }

    @Override
    public void sendPushToAPNS(RoutingContext routingContext) {
        String message = routingContext.request().getParam("message");
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/html").end("Cooool");

        System.out.println("Message: "+message);
    }
}
