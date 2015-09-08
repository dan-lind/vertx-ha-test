package com.omegapoint.bid;

import java.util.function.Consumer;


import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.omegapoint.notification.handlers.apns.ApnsReceiver;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.apache.log4j.Logger;

public class Main {

    final static Logger logger = LoggerUtil.getLogger();

    public static void main(String[] args) {
        Consumer<Vertx> runner = vertx -> {
            try {
                DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 8080));
                vertx.deployVerticle(ApnsReceiver.class.getName(), options, new Handler<AsyncResult<String>>() {
                    @Override
                    public void handle(AsyncResult<String> stringAsyncResult) {
                        logger.debug("Deploying RestService verticle!");
                    }
                });
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };

        VertxOptions vertxOptions = buildVertexOptions();
        Vertx.clusteredVertx(vertxOptions, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                runner.accept(vertx);
            } else {
                throw new RuntimeException("Could not setup Vert.x cluster!");
            }
        });
    }

    public static VertxOptions buildVertexOptions() {
        Config config = new Config();
        NetworkConfig networkConfig = new NetworkConfig();
        InterfacesConfig interfacesConfig = new InterfacesConfig();
        interfacesConfig.addInterface("0.0.0.0");
        networkConfig.setInterfaces(interfacesConfig);
        config.setNetworkConfig(networkConfig);
        ClusterManager mgr = new HazelcastClusterManager(config);
        VertxOptions vertxOptions = new VertxOptions().setClusterManager(mgr);
        return vertxOptions;
    }
}
