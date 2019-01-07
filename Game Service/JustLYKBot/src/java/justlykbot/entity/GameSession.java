package justlykbot.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@NamedQueries({
    @NamedQuery(name = "GameSession.getAllGameSessions",
            query = "select gs "
            + "from GameSession as gs")})

@Entity
@XmlRootElement(namespace = "justlykbot.model")
@XmlType(propOrder = { "player", "game", "gameSessionId", "rank","dateOfSession","is_winner"})
public class GameSession implements Serializable {
    
    @ManyToOne
    @JoinColumn(name="playerId")
    Player player;
    
    @ManyToOne
    @JoinColumn(name = "gameId")
    Game game;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameSessionId;
    
    @NotNull
    private String rank;
    
    @NotNull @Temporal(TemporalType.DATE)
    private Date dateOfSession;
    
    @NotNull
    private boolean is_winner;

    public GameSession() {
    }

    public GameSession(Player player, Game game, int gameSessionId, Date dateOfSession, boolean is_winner, String rank) {
        this.player = player;
        this.game = game;
        this.gameSessionId = gameSessionId;
        this.dateOfSession = dateOfSession;
        this.is_winner = is_winner;
        this.rank = rank;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getGameSessionId() {
        return gameSessionId;
    }

    public void setGameSessionId(int gameSessionId) {
        this.gameSessionId = gameSessionId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Date getDateOfSession() {
        return dateOfSession;
    }

    public void setDateOfSession(Date dateOfSession) {
        this.dateOfSession = dateOfSession;
    }

    public boolean isIs_winner() {
        return is_winner;
    }

    public void setIs_winner(boolean is_winner) {
        this.is_winner = is_winner;
    }

}
