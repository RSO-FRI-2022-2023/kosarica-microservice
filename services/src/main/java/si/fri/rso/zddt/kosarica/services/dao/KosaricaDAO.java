package si.fri.rso.zddt.kosarica.services.dao;

import lombok.extern.slf4j.Slf4j;
import si.fri.rso.zddt.common.models.Izdelek;
import si.fri.rso.zddt.common.models.Kosarica;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@ApplicationScoped
public class KosaricaDAO {

    // TODO: dodaj error handling

    @PersistenceContext(unitName = "kosarica-jpa")
    private EntityManager em;

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
