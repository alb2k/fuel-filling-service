package hackathon.microstream.service.system.health;

import hackathon.microstream.storage.DBManager;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

/**
 * Checks if the app is ready (for traffic)
 */
@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

    private static final Logger LOG = LoggerFactory.getLogger(ReadinessHealthCheck.class);

    @Override
    public HealthCheckResponse call() {

        boolean up = DBManager.getInstance().isReady();

        var builder =  HealthCheckResponse.builder()
                .name("storage")
                .withData("demoMode", DBManager.getInstance().isDemoMode())
                .state(up);

        if(up) {
            var storageManagerDetails = DBManager.getInstance().getStorageManagerDetails();
            builder.withData("storageManager.name", storageManagerDetails.getName());
            builder.withData("storageManager.active", storageManagerDetails.isActive());
            builder.withData("storageManager.initializationTime", storageManagerDetails.getInitializationTime());
            builder.withData("storageManager.initializationDuration", storageManagerDetails.getInitializationDuration());
            builder.withData("storageManager.operationModeTime", storageManagerDetails.getOperationModeTime());
        }

        return builder.build();
    }
}
