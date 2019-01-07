package justlykbot.entity;

import java.util.List;


public class Trophies {
    
    private List<Trophy> trophies;

    public Trophies() {
    }

    public Trophies(List<Trophy> trophies) {
        this.trophies = trophies;
    }

    public List<Trophy> getTrophies() {
        return trophies;
    }

    public void setTrophies(List<Trophy> trophies) {
        this.trophies = trophies;
    }
    
}
