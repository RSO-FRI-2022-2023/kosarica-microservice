package si.fri.rso.zddt.kosarica.services.dao;

import lombok.extern.slf4j.Slf4j;
import si.fri.rso.zddt.kosarica.models.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@ApplicationScoped
public class UserDAO {

    @PersistenceContext(unitName = "kosarica-jpa")
    private EntityManager em;

    public List<Uporabnik> getAllUsers() {
        try {
            TypedQuery<Uporabnik> q = em.createNamedQuery("Uporabnik.getAll", Uporabnik.class);
            return q.getResultList();
        } catch (Exception e) {
            log.error("Napaka pri pridobivanju uporabnikov", e);
        }

        return Collections.emptyList();
    }

    public Uporabnik getUserById(Integer userId) {
        try {
            return em.find(Uporabnik.class, userId);
        } catch (Exception e) {
            log.error("Napaka pri pridobivanju uporabnika", e);
        }

        return null;
    }

    @Transactional
    public boolean deleteUser(Integer userId) {
        try {
            Uporabnik uporabnik = getUserById(userId);

            if (uporabnik != null) {
                em.remove(uporabnik);

                return true;
            }
        } catch (Exception e) {
            log.error("Napaka pri odstranjevanju uporabnika", e);
        }

        return false;
    }

    @Transactional
    public Uporabnik updateUser(Uporabnik uporabnik) {
        try {
            Uporabnik u1 = getUserById(uporabnik.getId());

            if (u1 != null) {
                if (!Objects.equals(u1.getFirstname(), uporabnik.getFirstname())) {
                    u1.setFirstname(uporabnik.getFirstname());
                }

                if (!Objects.equals(u1.getLastname(), uporabnik.getLastname())) {
                    u1.setLastname(uporabnik.getLastname());
                }

                em.merge(u1);

                return u1;
            }
        } catch (Exception e) {
            log.error("Napaka pri posodabljanju uporabnika", e);
        }

        return null;
    }

    @Transactional
    public Uporabnik addUser(Uporabnik uporabnik) {
        try {
            if (uporabnik != null) {
                em.persist(uporabnik);
            }

            return uporabnik;
        } catch (Exception e) {
            log.error("Napaka pri dodajanju uporabnika", e);
        }

        return null;
    }
}
