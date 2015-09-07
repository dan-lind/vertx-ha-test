package com.omegapoint.bid;

import com.omegapoint.handler.ApnsReceiver;
import com.omegapoint.handler.GcmReceiver;
import com.omegapoint.handler.Sender;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.function.Consumer;

public class Main {
   // final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        //logger.info("TEST");
        Consumer<Vertx> runner = new Consumer<Vertx>() {
            @Override
            public void accept(Vertx vertx) {
                try {
                    vertx.deployVerticle(Sender.class.getName());
                    vertx.deployVerticle(ApnsReceiver.class.getName());
                    vertx.deployVerticle(GcmReceiver.class.getName());
                } catch (Throwable t) {
                    t.printStackTrace();
                }


            }
        };


        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                runner.accept(vertx);
            } else {
                res.cause().printStackTrace();
            }
        });
    }
}
