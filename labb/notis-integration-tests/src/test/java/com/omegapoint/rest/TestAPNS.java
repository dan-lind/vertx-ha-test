package com.omegapoint.rest;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.omegapoint.logging.LoggerUtil;
import com.omegapoint.protobuf.NotisProtos;
import com.omegapoint.ws.RestServiceImpl;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.function.Consumer;


@RunWith(VertxUnitRunner.class)
public class TestAPNS {
    private Vertx vertx;
    private Logger logger = LoggerUtil.getLogger();
    private final Object LOCK = new Object();
    private volatile boolean isSetup = false;

    @Before
    public void setUp(TestContext context) throws IOException {
        Consumer<Vertx> runner = vertx -> {
            try {
                vertx.deployVerticle(RestServiceImpl.class.getName());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };

        Config config = new Config();
        NetworkConfig networkConfig = new NetworkConfig();
        InterfacesConfig interfacesConfig = new InterfacesConfig();
        interfacesConfig.addInterface("0.0.0.0");
        networkConfig.setInterfaces(interfacesConfig);
        config.setNetworkConfig(networkConfig);
        ClusterManager mgr = new HazelcastClusterManager(config);
        VertxOptions vertxOptions = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(vertxOptions, res -> {
            synchronized (LOCK) {
                if (res.succeeded()) {
                    vertx = res.result();
                    isSetup = true;
                    runner.accept(vertx);
                    // Notify other threads when the vert.x cluster is up
                    LOCK.notifyAll();
                } else {
                    throw new RuntimeException("Failed!");
                }
            }
        });
    }

    @Test
    public void testPushAPNS(TestContext context) throws Exception {
        while (!isSetup()) {}
        NotisProtos.Notis.Builder notis = NotisProtos.Notis.newBuilder();
        notis.setMsg("The message from protobuf!!!");
        byte[] contents = notis.build().toByteArray();
        Buffer buffer = Buffer.buffer(contents);

        final Async async = context.async();
        vertx.createHttpClient().put(8080, "localhost", "/push/apns/tjena", response -> response.handler(body -> {
            logger.debug("Buffer = " + body.toString());
            async.complete();
        })).putHeader("Content-Length", Integer.toString(buffer.length()))
                .putHeader("Content-Type", "application/protobuf-x").write(buffer);
    }

    @After
    public void tearDown(TestContext context) {
        while (!isSetup()) {}
        logger.debug("Now closing the vertx instance...");
        vertx.close(context.asyncAssertSuccess());
    }

    /**
     * Wait until the vert.x cluster is up
     * @return
     */
    private boolean isSetup() {
        while (!isSetup) {
            synchronized (LOCK) {
                try {
                    LOCK.wait();
                    isSetup = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSetup;
    }
}
