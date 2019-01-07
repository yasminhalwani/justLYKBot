package justlykbot.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@NamedQueries({
    @NamedQuery(name = "Game.getAllGames",
    query = "select g.gameName, g.gameDp, g.gamePackage, g.downloadURL, g.appName "
    + "from Game as g "),
    /*@NamedQuery(name = "Game.getGameByPlayer", query = "select g.gameName, g.gameDp, g.gamePackage, g.downloadURL, g.appName from Game as g join g.players_games playerg where playerg.player_name = :playerName")*/})
@Entity
@XmlRootElement(name = "game", namespace = "justlykbot.model")
@XmlType(propOrder = {"gameName", "gameDp", "gamePackage", "downloadURL", "appName", "description"})
public class Game implements Serializable {

    @ManyToMany (cascade= {CascadeType.ALL})
    @JoinTable(name = "PlayerGames", joinColumns =
    @JoinColumn(name = "game_name", referencedColumnName = "gameName"), inverseJoinColumns =
    @JoinColumn(name = "player_name", referencedColumnName = "playerAppName"))
    Collection<Player> players_games;
    @OneToMany(mappedBy = "game", cascade= {CascadeType.ALL})
    List<GameSession> gameSessions;
    @OneToMany(mappedBy = "game", cascade= {CascadeType.ALL})
    List<ChallengeRequest> gameRequests;
    @Id
    private String gameName;
    private String gameDp;
    String gamePackage;
    String downloadURL;
    String appName;
    String description;
    String extraInstruction;

    public Game() {
    }

    public Game(Collection<Player> players_games, List<GameSession> gameSessions, List<ChallengeRequest> gameRequests, String gameName, String gameDp) {
        this.players_games = players_games;
        this.gameSessions = gameSessions;
        this.gameRequests = gameRequests;
        this.gameName = gameName;
        this.gameDp = gameDp;
    }

    public Collection<Player> getPlayers_games() {
        return players_games;
    }

    public void setPlayers_games(Collection<Player> players_games) {
        this.players_games = players_games;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDp() {
        return gameDp;
    }

    public void setGameDp(String gameDp) {
        this.gameDp = gameDp;
    }

    public List<ChallengeRequest> getGameRequests() {
        return gameRequests;
    }

    public void setGameRequests(List<ChallengeRequest> gameRequests) {
        this.gameRequests = gameRequests;
    }

    public String getGamePackage() {
        return gamePackage;
    }

    public void setGamePackage(String gamePackage) {
        this.gamePackage = gamePackage;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraInstruction() {
        return extraInstruction;
    }

    public void setExtraInstruction(String extraInstruction) {
        this.extraInstruction = extraInstruction;
    }
}
