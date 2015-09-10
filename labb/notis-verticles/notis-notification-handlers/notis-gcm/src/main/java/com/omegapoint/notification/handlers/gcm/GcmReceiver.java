package com.omegapoint.notification.handlers.gcm;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class GcmReceiver extends AbstractVerticle {



  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    MongoClient mongoClient = initDbClient();

    //eb.consumer("gcm-feed", message -> System.out.println("Received GCM-message: " + message.body()));
     eb.consumer("gcm-feed", message ->
             mongoClient.insert("notification",  new JsonObject().put("title", message.body()), res -> {
                 if (res.succeeded()) {
                    GcmSender.sendMessage(message.body().toString());
                     System.out.println("Saved to DB: " + message);

                 } else {

                     System.out.println("Failed to save to DB: " + message);
                 }
             })
      );

    System.out.println("Ready!");
  }

    private MongoClient initDbClient() {
        JsonObject config = Vertx.currentContext().config();

        String uri = config.getString("mongo_uri");
        if (uri == null) {
            uri = "mongodb://localhost:27017";
        }
        String db = config.getString("mongo_db");
        if (db == null) {
            db = "test";
        }

        JsonObject mongoconfig = new JsonObject()
                .put("connection_string", uri)
                .put("db_name", db);


        return MongoClient.createShared(vertx, mongoconfig);
    }
}
