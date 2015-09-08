package com.omegapoint.notification.handlers.apns;

import com.omegapoint.notification.handlers.NotificationHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class GcmReceiver extends AbstractVerticle implements NotificationHandler {



  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    //eb.consumer("gcm-feed", message -> System.out.println("Received GCM-message: " + message.body()));
     eb.consumer("gcm-feed", message ->
             message.reply("Hello")
     );


    System.out.println("Ready!");
  }
}
