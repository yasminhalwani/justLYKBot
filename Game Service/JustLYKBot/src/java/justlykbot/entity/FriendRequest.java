package justlykbot.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@NamedQueries({
    @NamedQuery(name = "FriendRequest.getFriendRequests",
            query = "select fr.sender.playerAppName,fr.sender.DP, fr.receiver.playerAppName, fr.status "
        + "from FriendRequest as fr where fr.receiver.playerAppName = :playerName")})

@Entity
@XmlRootElement(namespace = "justlykbot.model")
@XmlType(propOrder = {"requestId", "sender", "receiver", /*"dateAndTime",*/ "status"})
public class FriendRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)        
    int requestId;
    @ManyToOne
    Player sender;
    @ManyToOne
    Player receiver;
    // is it really needed?!!
    //@Temporal(TemporalType.DATE)
    //Date dateAndTime;
    String status;

    public FriendRequest() {
    }

    public FriendRequest(int requestId, Player sender, Player receiver, /*Date dateAndTime,*/ String status) {
        this.requestId = requestId;
        this.sender = sender;
        this.receiver = receiver;
       // this.dateAndTime = dateAndTime;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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

    /*public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
