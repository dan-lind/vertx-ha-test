package com.omegapoint.bid;

import com.omegapoint.handler.ApnsReceiver;
import com.omegapoint.ws.RestServiceImpl;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

public class Main {

    final static Logger logger = LoggerUtil.getLogger();

    public static void main(String[] args) {
        Consumer<Vertx> runner = vertx -> {
            try {
                vertx.deployVerticle(RestServiceImpl.class.getName());
                vertx.deployVerticle(ApnsReceiver.class.getName());
                DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 8080));
                vertx.deployVerticle(RestServiceImpl.class.getName(), options, stringAsyncResult -> {
                    logger.debug("Deploying verticle!");
                });
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };

        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions vertxOptions = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(vertxOptions, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                runner.accept(vertx);
            } else {
                throw new RuntimeException("Could not setup Vert.x cluster!");
            }
        });
    }
}
