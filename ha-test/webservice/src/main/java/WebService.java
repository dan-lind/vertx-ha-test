import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.log4j.Logger;

;


public class WebService extends AbstractVerticle {

    private static Logger logger = LoggerUtil.getLogger();

    private final int PORT = 8080;
    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/push/gcm/:message").handler(routingContext -> WebService.this.sendPushToGCM(routingContext));


        vertx.createHttpServer().requestHandler(router::accept).listen(PORT);
        System.out.println("WebService ready");
    }

    public void sendPushToGCM(RoutingContext routingContext) {
        String message = routingContext.request().getParam("message");
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/html").end("You wrote: " + message);
        vertx.eventBus().send("gcm-feed",message);

        logger.info("Log3h");
        System.out.println("Message sent to GCM Receiver");

    }

}
