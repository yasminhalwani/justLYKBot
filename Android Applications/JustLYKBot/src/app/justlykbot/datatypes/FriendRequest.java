package app.justlykbot.datatypes;

import java.io.Serializable;
import java.util.Date;

public class FriendRequest extends Request implements Serializable{

	
	public FriendRequest()
	{
		
	}
	public FriendRequest(int requestId, User sender, User receiver, Date dateAndTimeSent, String status) {
		

	}
	
	@Override
	public String toString()
	{
		return sender.getName()+" wants to be your friend! \n"+
				"date sent: "+dateAndTimeSent;
	}



}
