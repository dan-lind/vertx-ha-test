package com.omegapoint.bid;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.omegapoint.handler.ApnsReceiver;
import com.omegapoint.handler.GcmReceiver;
import com.omegapoint.handler.Sender;
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
        Consumer<Vertx> runner = new Consumer<Vertx>() {
            @Override
            public void accept(Vertx vertx) {
                try {
                    vertx.deployVerticle(Sender.class.getName());
                    vertx.deployVerticle(ApnsReceiver.class.getName());
                    vertx.deployVerticle(GcmReceiver.class.getName());
                    DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 8080));
                    vertx.deployVerticle(RestServiceImpl.class.getName(), options, new Handler<AsyncResult<String>>() {
                        @Override
                        public void handle(AsyncResult<String> stringAsyncResult) {
                            logger.debug("Deploying RestService verticle!");
                        }
                    });
                } catch (Throwable t) {
                    t.printStackTrace();
                }
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
