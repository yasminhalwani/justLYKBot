package justlykbot.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Players {
    
    private List<Player> players;

    public Players() {
    }

    public Players(List<Player> players) {
        this.players = players;
    }

    @XmlElement(name = "player")
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
    
}
