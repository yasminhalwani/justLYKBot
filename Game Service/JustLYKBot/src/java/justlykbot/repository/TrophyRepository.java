package justlykbot.repository;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import justlykbot.entity.Trophy;

@Stateless
@LocalBean
public class TrophyRepository {

    @PersistenceContext
    EntityManager em;

    public List<Trophy> getTrophiesByGameName(String gameName) {
        Query q = em.createNamedQuery("Trophy.getTrophiesByGameName").setParameter("gameName", gameName);
        return q.getResultList();
    }

    public List<Trophy> getTrophiesByPlayerName(int playerId) {
        Query q = em.createNamedQuery("Trophy.getPlayersTrophies");
        return q.getResultList();
    }

    public Trophy getTrophy(int id) {
        return em.find(Trophy.class, id);
    }
}
