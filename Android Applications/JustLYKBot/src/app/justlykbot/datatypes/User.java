package app.justlykbot.datatypes;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	private String name;
	private List<Game> game;
	private String dpURL;
	private String sipID;
	private String sipPass;
	private String xmppID;
	private String xmppPass;
	private List<User> friend;
	private List<Trophy> trophy;
	private boolean isPlaying;
	private String password;
	private List<ChallengeRequest> challengeRequest;
	private List<FriendRequest> friendRequest;
	protected String imageResourceId;

	
	public User(){
		
	}
	public User( String name) {
		this.name = name;

	}


	public List<Game> getGame() {
		return game;
	}

	public void setGame(List<Game> game) {
		this.game = game;
	}

	public String getDpURL() {
		return dpURL;
	}

	public void setDpURL(String dpURL) {
		this.dpURL = dpURL;
	}

	public String getSipID() {
		return sipID;
	}

	public void setSipID(String sipID) {
		this.sipID = sipID;
	}

	public String getXmppID() {
		return xmppID;
	}

	public void setXmppID(String xmppID) {
		this.xmppID = xmppID;
	}

	public List<User> getFriend() {
		return friend;
	}

	public void setFriend(List<User> friend) {
		this.friend = friend;
	}

	public List<Trophy> getTrophy() {
		return trophy;
	}

	public void setTrophy(List<Trophy> trophy) {
		this.trophy = trophy;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public String getSipPass() {
		return sipPass;
	}

	public void setSipPass(String sipPass) {
		this.sipPass = sipPass;
	}

	public String getXmppPass() {
		return xmppPass;
	}

	public void setXmppPass(String xmppPass) {
		this.xmppPass = xmppPass;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasTrophies()
	{
		if(trophy == null)
			return false;
		else
			return true;
	}

	public boolean hasGames()
	{
		if(game == null)
			return false;
		else return true;
	}
	
	public boolean hasFriends()
	{
		if(friend == null)
			return false;
		else
			return true;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean hasFriend(User friend)
	{
		for(int i=0; i<this.getFriend().size(); i++)
		{
			if(friend.getName().equals(this.getFriend().get(i).getName()))
				return true;
		}
		
		return false;
	}


	public List<FriendRequest> getFriendRequest() {
		return friendRequest;
	}


	public void setFriendRequest(List<FriendRequest> friendRequest) {
		this.friendRequest = friendRequest;
	}
	
	//------------
	
	public void addGame(Game newGame)
	{	
		game.add(newGame);	
	}
	
	public boolean hasGame(Game game)
	{
		for(int i=0; i<this.getGame().size(); i++)
		{
			if(game.getGameTitle().equals(this.getGame().get(i).getGameTitle()))
				return true;
		}
		
		return false;
	}
	
	
	public void addFriend(User newFriend)
	{
		friend.add(newFriend);
	}
	public List<ChallengeRequest> getChallengeRequest() {
		return challengeRequest;
	}
	public void setChallengeRequest(List<ChallengeRequest> challengeRequest) {
		this.challengeRequest = challengeRequest;
	}
	public String getImageResourceId() {
		return imageResourceId;
	}
	public void setImageResourceId(String imageResourceId) {
		this.imageResourceId = imageResourceId;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		int i = 0;
		
		string = string + ("Username: "+name+"\n"
				+"SIP ID: "+sipID+"\n"
				+"SIP Pass: "+sipPass+"\n"
				+"XMPP ID: "+xmppID+"\n"
				+"XMPP Pass: "+xmppPass+"\n"
				+"Challenge Requests: "+challengeRequest.size()+"\n"
				+"Friend Requests: "+friendRequest.size()+"\n"
				+"Trophies: "+trophy.size()+"\n"
				+"Games: "+game.size()+"\n"
				+"Friends: "+friend.size()+"\n");
		
		string = string + "[[[[------- CHALLENGE REQUESTS -------]]]]\n";
		
		for(i=0; i<challengeRequest.size(); i++)
		{
			string = string + challengeRequest.get(i).toString();
			string = string + "\n--------\n";
		}
		
		string = string + "[[[[------- FRIEND REQUESTS -------]]]]\n";
		
		for(i=0; i<friendRequest.size(); i++)
		{
			string = string + friendRequest.get(i).toString();
			string = string + "\n--------\n";
		}
		
		string = string + "[[[[------- TROPHIES -------]]]]\n";
		
		for(i=0; i<trophy.size(); i++)
		{
			string = string + trophy.get(i).toString();
			string = string + "\n--------\n";
		}
		
		
		string = string + "[[[[------- GAMES -------]]]]\n";
		
		for(i=0; i<game.size(); i++)
		{
			string = string + game.get(i).toString();
			string = string + "\n--------\n";
		}
		
		string = string + "[[[[------- FRIENDS -------]]]]\n";
		
		for(i=0; i<friend.size(); i++)
		{
			string = string + friend.get(i).toString(1);
			string = string + "\n--------\n";
		}
		
		return string;
	}
	
	public String toString(int x)
	{
		String string = "";
		int i = 0;
		
		string = string + ("Username: "+name+"\n"
				+"SIP ID: "+sipID+"\n"
				+"SIP Pass: "+sipPass+"\n"
				+"XMPP ID: "+xmppID+"\n"
				+"Trophies: "+trophy.size()+"\n"
				+"Games: "+game.size()+"\n");
		
		string = string + "[[[[------- TROPHIES -------]]]]\n";
		
		for(i=0; i<trophy.size(); i++)
		{
			string = string + trophy.get(i).toString();
			string = string + "\n--------\n";
		}
		
		
		string = string + "[[[[------- GAMES -------]]]]\n";
		
		for(i=0; i<game.size(); i++)
		{
			string = string + game.get(i).toString();
			string = string + "\n--------\n";
		}
		
		
		return string;
	}


}
