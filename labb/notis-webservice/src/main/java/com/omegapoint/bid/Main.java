package com.omegapoint.bid;

import com.omegapoint.handler.ApnsReceiver;
import com.omegapoint.ws.RestServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

public class Main {

    final static Logger logger = LoggerUtil.getLogger();

    public static void main(String[] args) {
        Consumer<Vertx> runner = vertx -> {
            try {
                vertx.deployVerticle(RestServiceImpl.class.getName());
                vertx.deployVerticle(ApnsReceiver.class.getName());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };

        Vertx vertx = Vertx.vertx(new VertxOptions().setClustered(false));
        runner.accept(vertx);
    }
}
