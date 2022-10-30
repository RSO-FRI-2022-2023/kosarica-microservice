package si.fri.rso.zddt.kosarica.services.dao;

import lombok.extern.slf4j.Slf4j;
import si.fri.rso.zddt.kosarica.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
public class UserDAO {

    @PersistenceContext(unitName = "kosarica-jpa")
    private EntityManager em;

    public List<User> getAllUsers() {
        try {
            Query q = em.createNamedQuery("User.getAll", User.class);
            return (List<User>) q.getResultList();
        } catch (Exception e) {
            log.error("Napaka pri pridobivanju uporabnikov", e);
        }

        return Collections.emptyList();
    }

    public User getUserById(Integer userId) {
        try {
            Query q = em.createNamedQuery("User.getById", User.class);
            q.setParameter("userId", userId);

            return (User) q.getSingleResult();
        } catch (Exception e) {
            log.error("Napaka pri pridobivanju uporabnika", e);
        }

        return null;
    }

    @Transactional
    public boolean deleteUser(Integer userId) {
        try {
            User user = getUserById(userId);
            if (user != null) {
                em.remove(user);

                return true;
            }
        } catch (Exception e) {
            log.error("Napaka pri odstranjevanju uporabnika", e);
        }

        return false;
    }

    @Transactional
    public User updateUser(User user) {
        try {
            return em.merge(user);
        } catch (Exception e) {
            log.error("Napaka pri posodabljanju uporabnika", e);
        }

        return null;
    }

    @Transactional
    public User addUser(User user) {
        try {
            if (user != null) {
                em.persist(user);
            }

            return user;
        } catch (Exception e) {
            log.error("Napaka pri dodajanju uporabnika", e);
        }

        return null;
    }
}
