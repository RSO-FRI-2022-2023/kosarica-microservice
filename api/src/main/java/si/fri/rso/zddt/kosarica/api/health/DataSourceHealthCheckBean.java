package si.fri.rso.zddt.kosarica.api.health;

import com.kumuluz.ee.health.checks.DataSourceHealthCheck;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class DataSourceHealthCheckBean extends DataSourceHealthCheck {

}
