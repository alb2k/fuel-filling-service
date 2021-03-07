package hackathon.microstream.service.system;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "Fuel Filling API",
                version = "1.0")
)
public class DummyAppForOpenApi extends Application {
}
