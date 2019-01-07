package justlykbot.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@NamedQueries({
    @NamedQuery(name = "Trophy.getTrophiesByGameName",
            query = "select t from Trophy t where t.gameName = :gameName"),
    /*@NamedQuery(name = "Trophy.getTrophiesByPlayerName",
            query = "select t.gameName , t.trophyName from Trophy t join t.players_trophies playert \n" +
"where playert.player_name = :playerName")*/})

@Entity
@XmlRootElement(name = "trophy", namespace = "justlykbot.model")
@XmlType(propOrder = {"trophyName","gameName","trophyDp"})
public class Trophy implements Serializable {

    @ManyToMany (cascade= {CascadeType.ALL})
    @JoinTable(name = "PlayerTrophies", joinColumns =
            @JoinColumn(name = "trophy_name", referencedColumnName = "trophyName"), inverseJoinColumns =
            @JoinColumn(name = "player_name", referencedColumnName = "playerName"))
    Collection<Player> players_trophies;
    
    private String gameName;
    @NotNull
    private String trophyDp;
    @Id
    private String trophyName;

    public Trophy() {
    }

    public Trophy(String gameName, String trophyDp, String trophyName) {
        this.gameName = gameName;
        this.trophyDp = trophyDp;
        this.trophyName = trophyName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameId(String gameName) {
        this.gameName = gameName;
    }

    public String getTrophyDp() {
        return trophyDp;
    }

    public void setTrophyDp(String trophyDp) {
        this.trophyDp = trophyDp;
    }

    public String getTreophyName() {
        return trophyName;
    }

    public void setTrophyName(String trophyName) {
        this.trophyName = trophyName;
    }
}
