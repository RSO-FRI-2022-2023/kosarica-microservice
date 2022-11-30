package si.fri.rso.zddt.kosarica.api.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class LivenessHealthCheckBean implements HealthCheck {

    public HealthCheckResponse call() {
        return HealthCheckResponse.up(LivenessHealthCheckBean.class.getSimpleName());
    }
}
