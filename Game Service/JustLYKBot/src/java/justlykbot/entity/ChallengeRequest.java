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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@NamedQueries({
    @NamedQuery(name = "ChallengeRequest.getChallengeRequests",
            query = "select cr.sender.playerAppName, cr.receiver.playerAppName, cr.game.gameName, cr.dateOfRequest, cr.status "
        + "from ChallengeRequest as cr where cr.receiver.playerAppName = :playerName")})
@Entity
@XmlRootElement(namespace = "justlykbot.model")
@XmlType(propOrder = {"challengeRequestId", "sender", "receiver", "dateOfRequest", "status"})
public class ChallengeRequest implements Serializable {

    @ManyToOne
    @JoinColumn(name = "gameName")
    Game game;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int challengeRequestId;
    @OneToOne
    Player sender;
    @OneToOne
    Player receiver;
    @Temporal(TemporalType.DATE)
    Date dateOfRequest;
    String status;

    public ChallengeRequest() {
    }

    public ChallengeRequest(int challengeRequestId, Player sender, Player receiver, Date dateOfRequest, String status) {
        this.challengeRequestId = challengeRequestId;
        this.sender = sender;
        this.receiver = receiver;
        this.dateOfRequest = dateOfRequest;
        this.status = status;
    }

    public int getChallengeRequestId() {
        return challengeRequestId;
    }

    public void setChallengeRequestId(int challengeRequestId) {
        this.challengeRequestId = challengeRequestId;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
