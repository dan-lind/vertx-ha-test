import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import org.apache.log4j.Logger;


/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class GcmReceiver extends AbstractVerticle {

    private static Logger logger = LoggerUtil.getLogger();

  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

      eb.consumer("gcm-feed", message -> {
            logger.error("Test");
          System.out.println("Received message: " + message.body());
          // Now send back reply
          message.reply("pong!");
      });

      logger.info("Log from Log4j");
    System.out.println("GCM Ready!");
  }

}
