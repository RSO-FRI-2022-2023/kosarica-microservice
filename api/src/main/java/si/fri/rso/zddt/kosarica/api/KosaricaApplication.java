package si.fri.rso.zddt.kosarica.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Product catalog API", version = "v1",
        contact = @Contact(email = "dt1193@student.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing products."))
@ApplicationPath("/v1")
public class KosaricaApplication extends Application {
}
