package com.omegapoint.ws;

import com.google.protobuf.InvalidProtocolBufferException;
import com.omegapoint.bid.LoggerUtil;
import com.omegapoint.protobuf.NotisProtos;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.log4j.Logger;

public class RestServiceImpl extends AbstractVerticle implements RestService {

    private static Logger logger = LoggerUtil.getLogger();
    private final int PORT = 8080;
    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/push/gcm/:message").handler(routingContext -> RestServiceImpl.this.sendPushToGCM(routingContext));
        router.put("/push/apns/:message").handler(routingContext -> RestServiceImpl.this.sendPushToAPNS(routingContext));


        logger.info("Listening on " + PORT);
        vertx.createHttpServer().requestHandler(router::accept).listen(PORT);
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

    @Override
    public void sendPushToGCM(RoutingContext routingContext) {
        String message = routingContext.request().getParam("message");
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/html").end("You wrote: " + message);

        logger.debug("Message on GCM: " + message);
    }

    @Override
    public void sendPushToAPNS(RoutingContext routingContext) {

        HttpServerRequest request = routingContext.request();
        String contentType = request.headers().get("Content-Type");
        logger.debug("Content-Type = " + contentType);

        Buffer data = routingContext.getBody();
        byte[] contents = data.getBytes();
        try {
            NotisProtos.Notis notis = NotisProtos.Notis.parseFrom(contents);

            logger.debug("Protobuf message length = " + data.length() + "\nProtobuf message: " + notis.getMsg());

            String message = routingContext.request().getParam("message");
            HttpServerResponse response = routingContext.response();
            response.putHeader("Content-Length", Integer.toString(("You wrote: ".getBytes().length + message.getBytes().length)));
            response.putHeader("Content-Type", "text/html");
            response.end("You wrote: " + message);

            logger.debug("Message on APNS: " + message);

            EventBus eb = vertx.eventBus();

            //eb.publish("news.uk.sport", "Yay! Someone kicked a ball");
            eb.send("apns-feed", "Yay! Someone kicked a ball");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
