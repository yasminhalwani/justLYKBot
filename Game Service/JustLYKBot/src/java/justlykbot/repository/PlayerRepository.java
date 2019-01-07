package justlykbot.repository;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.xml.bind.annotation.XmlType;
import justlykbot.entity.ChallengeRequest;
import justlykbot.entity.Player;
import justlykbot.entity.FriendRequest;
import justlykbot.entity.Game;
import justlykbot.entity.Trophy;
import org.springframework.http.MediaType;

@Stateless
@LocalBean
public class PlayerRepository {

    @PersistenceContext
    EntityManager em;

    public List<Player> getPlayers() {
        Query q = em.createNamedQuery("Player.getAllPlayers");
        return q.getResultList();
    }

    public List<FriendRequest> getFriendRequests(String playerUri) {
        Query q = em.createNamedQuery("FriendRequest.getFriendRequests");
        return q.getResultList();
    }

    public List<ChallengeRequest> getChallengeRequests(String playerUri) {
        Query q = em.createNamedQuery("ChallengeRequest.getChallengeRequests");
        return q.getResultList();
    }

    public List<Player> getPlayerFriends(String playerUri) {
        Query q = em.createNamedQuery("Player.getPlayerFriends");
        return q.getResultList();
    }
    
    public void addFriend(String playerName, String friendName) {
        Query query = em.createQuery("insert into playerfriends values (:friendName , :playerName)");
        query.setParameter("playerName", playerName).setParameter("friendName", friendName);
        query.executeUpdate();
    }

    public void addGameByPlayer(String playerName, String gameName) {
        Query query = em.createQuery("insert into PlayerGames values (:gameName , :playerName)");
        query.setParameter("playerName", playerName).setParameter("gameName", gameName);
        query.executeUpdate();

    }

    public Player getPlayer(String name, String password) {
        Query query = em.createNamedQuery("Player.getPlayerByNameAndPassword");
        query.setParameter("playerAppName", name);
        query.setParameter("pass", password);
        try {
            return (Player) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    //needed
    public void responseToChallengeRequest(int requestId, String response) {
        Query query = em.createQuery("insert into challengeRequest values (:response) where requestId = :requestId");
        query.setParameter("status", response);
        query.executeUpdate();
    }
    
    //!!!!!!!!!!!!!!
    public void responseToFriendRequest(int requestId, String response) {
        Query query = em.createQuery("insert into friendrequest values (:response) where requestId = :requestId");
        query.setParameter("status", response);
        query.executeUpdate();

        if (response.equals("accept")) {
            Query query1 = em.createQuery("insert into PlayerFriends values ()");
            query1.setParameter(response, query1);
            query1.executeUpdate();
        }
    }

    public void deleteFriend(String friendName, String playerName) {
        Query query = em.createQuery("delete from PlayerFriends where friend_name = :friendName AND player_name = :playerName");
        query.setParameter("friend_name", friendName).setParameter("player_name", playerName);
        query.executeUpdate();
    }

    public void insertTestdata() {
        System.out.println("Creating 2 test Players\n");

        Player player1 = new Player();

        player1.setplayerAppName("player1");
        player1.setId(1);
        player1.setSipID("player1_sip");
        player1.setXmppID("player1_xmpp");
        player1.setEmail("player1@game.co");
        player1.setPass("player1");
        player1.setRoboId(9045);

        Player player2 = new Player();
        player2.setplayerAppName("player2");
        player2.setEmail("player2@game.co");
        player2.setPass("player2");
        player2.setRoboId(1234);

        em.persist(player1);
        em.persist(player2);

    }
}