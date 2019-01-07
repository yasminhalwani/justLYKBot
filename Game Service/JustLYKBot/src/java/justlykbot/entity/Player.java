package justlykbot.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@NamedQueries({
    @NamedQuery(name = "Player.getAllPlayers",
            query = "select p.playerAppName, p.sipID, p.sipPass, p.xmppPass, p.xmppID from Player as p "),
   // @NamedQuery(name = "Player.getPlayerFriends", query = "select p.playerAppName, p.DP, p.sipID, p.xmppID"
           // + " from Player as p, players_friends as pf where pf.Playerid = :playerUri "),
    @NamedQuery(name = "Player.getPlayerByNameAndPassword",
            query = "select p FROM Player p WHERE p.playerAppName LIKE :name and p.pass LIKE :password")})
@Entity
@XmlRootElement(name = "player", namespace = "justlykbot.model")
@XmlType(propOrder = {"id", "playerAppName", "DP", "roboId", "email", "games", "trophies", "sipID",
    "xmppID", "challengeRequest", "friendRequests", "players_friends", "gameSessions"})
public class Player implements Serializable {

    int id;
    @Id
    private String playerAppName;
    @NotNull
    private String pass;
    private int DP;
    @NotNull
    private int roboId;
    @NotNull
    private String email;
    private String sipID;
    private String sipPass;
    private String xmppID;
    private String xmppPass;
    @ManyToMany(mappedBy = "players_trophies", cascade= {CascadeType.ALL})
    Collection<Trophy> trophies;
    @ManyToMany(mappedBy = "players_games", cascade= {CascadeType.ALL})
    Collection<Game> games;
    //@ManyToMany(mappedBy = "players_friends")
    //Collection<Player> freinds;
    @ManyToMany (cascade= {CascadeType.ALL})
    @JoinTable(name = "PlayerFriends", joinColumns =
            @JoinColumn(name = "player_name", referencedColumnName = "playerAppName"), inverseJoinColumns =
            @JoinColumn(name = "friend_name", referencedColumnName = "playerAppName"))
    Collection<Player> players_friends;
    @OneToMany(mappedBy = "player", cascade= {CascadeType.ALL})
    List<GameSession> gameSessions;
    //@OneToOne(mappedBy = "sender")
    //@OneToOne(mappedBy = "receiver")
    @OneToMany (cascade= {CascadeType.ALL})
    List<ChallengeRequest> challengeRequest;
    //@OneToMany(mappedBy = "sender")
    @OneToMany(cascade= {CascadeType.ALL})
    List<FriendRequest> friendRequests;

    public Player() {
    }

    public Collection<Trophy> getTrophies() {
        return trophies;
    }

    public void setTrophies(Collection<Trophy> trophies) {
        this.trophies = trophies;
    }

    public Collection<Game> getGames() {
        return games;
    }

    public void setGames(Collection<Game> games) {
        this.games = games;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }

    public String getplayerAppName() {
        return playerAppName;
    }

    public void setplayerAppName(String playerAppName) {
        this.playerAppName = playerAppName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getDP() {
        return DP;
    }

    public void setDP(int DP) {
        this.DP = DP;
    }

    public int getRoboId() {
        return roboId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setRoboId(int roboId) {
        this.roboId = roboId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSipID() {
        return sipID;
    }

    public void setSipID(String sipID) {
        this.sipID = sipID;
    }

    public String getSipPass() {
        return sipPass;
    }

    public void setSipPass(String sipPass) {
        this.sipPass = sipPass;
    }

    public String getXmppID() {
        return xmppID;
    }

    public void setXmppID(String xmppID) {
        this.xmppID = xmppID;
    }

    public String getXmppPass() {
        return xmppPass;
    }

    public void setXmppPass(String xmppPass) {
        this.xmppPass = xmppPass;
    }

    /*public Collection<Player> getFreinds() {
        return freinds;
    }

    public void setFreinds(Collection<Player> freinds) {
        this.freinds = freinds;
    }*/

    public List<ChallengeRequest> getChallengeRequest() {
        return challengeRequest;
    }

    public void setChallengeRequest(List<ChallengeRequest> challengeRequest) {
        this.challengeRequest = challengeRequest;
    }

    public List<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
