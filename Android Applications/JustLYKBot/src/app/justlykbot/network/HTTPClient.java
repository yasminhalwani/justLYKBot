package app.justlykbot.network;

import java.io.Serializable;
import java.util.List;

import app.justlykbot.datatypes.ChallengeRequest;
import app.justlykbot.datatypes.FriendRequest;
import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.Trophy;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.layouts.Login;

public class HTTPClient{
	
	public boolean isServerUp()
	{
		//try to send a sample data and get a response
		
		return false; //TODO change this to reflect real response
	}
	
	public User authenticate(String username, String password)
	{
		//authenticate with client
		//get user info from system users 
		return new User("http user"); //FIXME
	}
	
	public List<ChallengeRequest> getChallengeRequestsOfUser(String username)
	{
		return null;
	}
	
	public List<FriendRequest> getFriendRequestsOfUser(String username)
	{
		return null;
	}

	public List<Trophy> getTrophiesOfUser(String username)
	{
		return null;
	}
	
	public List<Game> getGamesOfUser(String username)
	{
		return null;
	}
	
	public List<User> getFriendsOfUser(String username)
	{
		//to be done soon
		return null;
	}
	
	public List<Game> getSystemGames()
	{
		//done
		return null;
	}
	
	public List<User> getSystemUsers()
	{
		//done
		return null;
	}
	
	public void respondToChallengeRequest(int requestId, String response)
	{
		//done
	}
	
	public void respondToFriendRequest(int requestId, String response)
	{
		//done
	}
	
	public void addGameByUser(String username, String gameName)
	{
		//done
	}
	
	public void sendFriendRequest(String senderName, String receiverName)
	{
		//done
	}
	
	public void sendChallengeRequest(String senderName, String receiverName, String gameName)
	{
		//done
	}
	
	public void updateScore(String username, String gameName, String scoreInSession)
	{
		//done
		//trophy logic still missing
	}
	
	public void unfriend(String username, String friendName)
	{

	}
	
	public void deleteGame(String username, String gameName)
	{
		
	}
	
	
}
