package com.omegapoint.handler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ApnsReceiver extends AbstractVerticle {



  @Override
  public void start() throws Exception {
    EventBus eb = vertx.eventBus();

    eb.consumer("apns-feed", message -> System.out.println("Received APNs-message: " + message.body()));

    System.out.println("Ready!");
  }
}
