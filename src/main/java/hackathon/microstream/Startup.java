package hackathon.microstream;


import hackathon.microstream.storage.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

/**
 * Handles the startup and shutdown
 *
 * @seealso https://stackoverflow.com/a/11476587
 */
@ApplicationScoped
public class Startup {
    private final static Logger LOG = LoggerFactory.getLogger(Startup.class);

    public void onStart(@Observes @Initialized(ApplicationScoped.class) Object init) {
        LOG.info("The application is starting...");

        // Redirect helidon logs to slf4j
        LOG.info("Installing {}", SLF4JBridgeHandler.class.getName());
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LOG.info("Installed {} successfully", SLF4JBridgeHandler.class.getName());

        LOG.info("Initializing DB");
        DBManager.getInstance().init();
        LOG.info("Initialized DB");

        LOG.info("The application is started");
    }

    public void onStop(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        LOG.info("The application is stopping...");

        LOG.info("Shutting down DB");
        DBManager.getInstance().shutdown();
        LOG.info("Shut down DB");

        LOG.info("The application is stopped");
    }
}
