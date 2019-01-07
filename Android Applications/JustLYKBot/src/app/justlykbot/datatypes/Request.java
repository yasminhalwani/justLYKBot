package app.justlykbot.datatypes;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
	
	protected int requestId;
	protected User sender;
	protected User receiver;
	protected String dateAndTimeSent;
	protected String status;
	
	public Request()
	{
		
	}
	
	public Request(int requestId, User sender, User receiver, String dateAndTimeSent, String status) {
		this.sender = sender;
		this.receiver = receiver;
		this.dateAndTimeSent = dateAndTimeSent;
		this.status = status;
		this.requestId = requestId;
	}
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public void setSender(String senderName) {
		this.sender = new User(senderName);
	}
	
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}	
	
	public void setReceiver(String receiverName) {
		this.receiver = new User(receiverName);
	}
	public String getDateAndTimeSent() {
		return dateAndTimeSent;
	}
	public void setDateAndTimeSent(String dateAndTimeSent) {
		this.dateAndTimeSent = dateAndTimeSent;
	}
	
	public void setDateAndTimeSent(Date dateAndTimeSent) {
		this.dateAndTimeSent = dateAndTimeSent.toString();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}




}
