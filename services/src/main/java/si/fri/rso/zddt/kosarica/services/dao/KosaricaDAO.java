package si.fri.rso.zddt.kosarica.services.dao;

import lombok.extern.log4j.Log4j2;
import si.fri.rso.zddt.common.models.Izdelek;
import si.fri.rso.zddt.common.models.Kosarica;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@ApplicationScoped
public class KosaricaDAO {

    // TODO: dodaj error handling

    @PersistenceContext(unitName = "kosarica-jpa")
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    public List<Kosarica> getAll() {
        try {
            log.info("getAll");

            TypedQuery<Kosarica> q = em.createNamedQuery("Kosarica.getAll", Kosarica.class);
            return q.getResultList();
        } catch (Exception e) {
            log.error("Napaka pri pridobivanju košaric", e);

            throw e;
        }
    }

    public Kosarica getById(Integer kosaricaId) {
        try {
            return em.find(Kosarica.class, kosaricaId);
        } catch (Exception e) {
            log.error("Napaka pri pridobivanju košarice glede na ID", e);
        }

        return null;
    }

    @Transactional
    public Kosarica getByUserId(Integer userId) {
        try {
            TypedQuery<Kosarica> kosarica = em.createNamedQuery("Kosarica.getByUserId", Kosarica.class);
            kosarica.setParameter("userId", userId);

            List<Kosarica> resultList = kosarica.getResultList();

            Kosarica k = resultList.isEmpty() ? null : resultList.get(0);

            if (k == null) {
                k = new Kosarica();
                k.setUporabnik(userDAO.getUserById(userId));

                em.persist(k);
            }

            return k;
        } catch (Exception e) {
            log.error("Napaka pri pridobivanju košarice glede na uporabnika", e);
        }

        return null;
    }

    @Transactional
    public Kosarica add(Kosarica kosarica) {
        try {
            if (kosarica != null) {
                em.persist(kosarica);
            }

            return kosarica;
        } catch (Exception e) {
            log.error("Napaka pri dodajanju košarice", e);
        }

        return null;
    }

    @Transactional
    public Boolean delete(Integer kosaricaId) {
        try {
            Kosarica kosarica = getById(kosaricaId);

            if (kosarica != null) {
                em.remove(kosarica);

                return true;
            }
        } catch (Exception e) {
            log.error("Napaka pri dodajanju košarice", e);
        }

        return false;
    }

    @Transactional
    public Kosarica addIzdelek(Integer id, Izdelek i) {
        try {
            Kosarica kosarica = getById(id);

            if (kosarica != null) {
                TypedQuery<Izdelek> izdelek = em.createNamedQuery("Izdelek.getById", Izdelek.class);
                izdelek.setParameter("idIzdelka", i.getId());

                kosarica.getIzdelki().add(izdelek.getSingleResult());

                em.merge(kosarica);

                return kosarica;
            }
        } catch (Exception e) {
            log.error("Napaka pri dodajanju izdelka", e);
        }

        return null;
    }

    @Transactional
    public Boolean deleteIzdelek(Integer id, Integer izdelekId) {
        try {
            Kosarica kosarica = getById(id);

            if (kosarica != null) {
                TypedQuery<Izdelek> izdelekQ = em.createNamedQuery("Izdelek.getById", Izdelek.class);
                izdelekQ.setParameter("idIzdelka", izdelekId);

                kosarica.getIzdelki().remove(izdelekQ.getSingleResult());

                em.merge(kosarica);

                return true;
            }
        } catch (Exception e) {
            log.error("Napaka pri dodajanju izdelka", e);
        }

        return false;
    }
}
