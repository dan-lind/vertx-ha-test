package com.omegapoint.protobuf;

import com.omegapoint.handler.ApnsReceiver;
import com.omegapoint.ws.RestServiceImpl;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


@RunWith(VertxUnitRunner.class)
public class TestPushToAPNS {
    private Vertx vertx;

    @Before
    public void setUp(TestContext context) throws IOException {
        vertx = Vertx.vertx(new VertxOptions().setClustered(false));

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.port", 8080)
                );
        vertx.deployVerticle(RestServiceImpl.class.getName(), options, context.asyncAssertSuccess());
        vertx.deployVerticle(ApnsReceiver.class.getName());
    }

    @Test
    public void testMsg(TestContext context) throws Exception {
        NotisProtos.Notis.Builder notis = NotisProtos.Notis.newBuilder();
        notis.setMsg("The message from protobuf!!!");
        byte[] contents = notis.build().toByteArray();
        Buffer buffer = Buffer.buffer(contents);

        final Async async = context.async();
        vertx.createHttpClient().put(8080, "localhost", "/push/apns/tjena", response -> response.handler(body -> {
            System.out.println("Buffer = " + body.toString());
            async.complete();
        })).putHeader("Content-Length", Integer.toString(buffer.length()))
           .putHeader("Content-Type", "application/protobuf-x").write(buffer);
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }
}
