package justlykbot.repository;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import justlykbot.entity.Game;

@Stateless
@LocalBean
public class GameRepository {

    @PersistenceContext
    EntityManager em;

    public List<Game> getGames() {
        Query q = em.createNamedQuery("Game.getAllGames");
        return q.getResultList();
    }

    public Game getGame(String name) {
        return em.find(Game.class, name);
    }

    public void deleteGame(String gameName, String playerName) {
      Query query =  em.createNamedQuery("delete from PlayerGames where game_name = :gameName AND player_name = :playerName");
      query.executeUpdate();
    }

    public void addGameByPlayer(String playerName, String gameName) {
        Query query =  em.createNamedQuery("insert into PlayerGames values (:gameName , :playerName)");
        query.executeUpdate();
    }

    public void updateRank(String playerName, int gameId, int score) {

        String rank;

        if (score <= 0 && score <= 20) {
            rank = "Irresponsible Driver";
        } else if (score >= 21 && score <= 40) {
            rank = "Lazy Driver";
        } else if (score >= 41 && score <= 60) {
            rank = "Taxi Driver";
        } else if (score >= 61 && score <= 80) {
            rank = "Emergency Driver";
        } else if (score >= 81 && score <= 100) {
            rank = "Superman";
        }
    }
    
    /*public List<game> getGamesByPlayer(String playerName){
        
    }*/
}
