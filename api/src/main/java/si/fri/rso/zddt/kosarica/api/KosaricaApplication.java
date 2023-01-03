package si.fri.rso.zddt.kosarica.api;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Kosarica API", version = "v1",
        contact = @Contact(email = "dt1193@student.uni-lj.si"),
        license = @License(name = "dev"), description = "API za pregled in urejanje košarice"),
        servers = {
                @Server(url = "https://www.songify.si/rso/kosarica/"),
                @Server(url = "http://localhost:8080/")
        }
)
@ApplicationPath("/v1")
public class KosaricaApplication extends Application {
}
