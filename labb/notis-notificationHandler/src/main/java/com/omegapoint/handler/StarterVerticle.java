package com.omegapoint.handler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;

/**
 * Created by danlin on 2015-09-08.
 */
public class StarterVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(ApnsReceiver.class.getName());
        vertx.deployVerticle(GcmReceiver.class.getName());
        //vertx.deployVerticle(Sender.class.getName());

        MetricsService metricsService = MetricsService.create(vertx);


        long timerID = vertx.setPeriodic(5000, id -> {
            JsonObject metrics = metricsService.getMetricsSnapshot(vertx);
            System.out.println(metrics.getJsonObject("vertx.eventbus.messages.received"));
        });


    }
}
