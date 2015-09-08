package com.omegapoint.notification.handlers.gcm;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class GcmReceiver extends AbstractVerticle {



  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    //eb.consumer("gcm-feed", message -> System.out.println("Received GCM-message: " + message.body()));
     eb.consumer("gcm-feed", message ->
             message.reply("Hello")
     );


    System.out.println("GCM Verticle Ready!");
  }
}
