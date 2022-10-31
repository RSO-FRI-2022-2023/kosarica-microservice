package si.fri.rso.zddt.kosarica.services.dao;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@ApplicationScoped
public class KosaricaDAO {

    @PersistenceContext(unitName = "kosarica-jpa")
    private EntityManager em;


}
