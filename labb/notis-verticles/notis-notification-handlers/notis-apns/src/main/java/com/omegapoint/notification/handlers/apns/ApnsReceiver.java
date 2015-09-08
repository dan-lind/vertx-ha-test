package com.omegapoint.notification.handlers.apns;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class ApnsReceiver extends AbstractVerticle {



  @Override
  public void start() throws Exception {
    EventBus eb = vertx.eventBus();

    eb.consumer("apns-feed", message -> System.out.println("Received APNs-message: " + message.body()));

    System.out.println("Apns Verticle Ready!");
  }
}
