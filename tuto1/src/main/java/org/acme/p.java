package org.acme;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;


@ApplicationScoped
class ApplicationLifeCycle {
    private static final Logger LOGGER = Logger.getLogger(ApplicationLifeCycle.class);
    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("l'application est demarer "+ProfileManager.getActiveProfile());
    }
    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("l'application est arreté...");
    }
}