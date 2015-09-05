package com.omegapoint.bid;

import com.omegapoint.ws.RestServiceImpl;
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
                    vertx.deployVerticle(RestServiceImpl.class.getName());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        };

        Vertx vertx = Vertx.vertx(new VertxOptions().setClustered(false));
        runner.accept(vertx);
    }
}
