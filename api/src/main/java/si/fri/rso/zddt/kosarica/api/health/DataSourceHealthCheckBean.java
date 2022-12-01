package si.fri.rso.zddt.kosarica.api.health;

import com.kumuluz.ee.logs.cdi.Log;
import lombok.extern.log4j.Log4j2;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Readiness
@ApplicationScoped
@Log4j2
@Log
public class DataSourceHealthCheckBean implements HealthCheck {

    public HealthCheckResponse call() {
        Connection conn = null;

        boolean success = false;

        try {
            final Context ctx = new InitialContext();
            final DataSource ds = (DataSource) ctx.lookup("jdbc/KosaricaDS");
            conn = ds.getConnection();
            success = true;
        } catch (NamingException | SQLException e) {
            log.error(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    success = false;
                    log.error(e);
                }
            }
        }

        if (success) {
            return HealthCheckResponse.up(DataSourceHealthCheckBean.class.getSimpleName());
        } else {
            return HealthCheckResponse.down(DataSourceHealthCheckBean.class.getSimpleName());
        }
    }
}
