package app.justlykbot.localdata;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import app.justlykbot.datatypes.ChallengeRequest;
import app.justlykbot.datatypes.FriendRequest;
import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.Server;
import app.justlykbot.datatypes.Trophy;
import app.justlykbot.datatypes.User;
import app.justlykbot.xmlparsers.ChallengeRequestXMLParseHandler;
import app.justlykbot.xmlparsers.FriendRequestXMLParseHandler;
import app.justlykbot.xmlparsers.GameXMLParseHandler;
import app.justlykbot.xmlparsers.ServerXMLParseHandler;
import app.justlykbot.xmlparsers.TrophyXMLParseHandler;
import app.justlykbot.xmlparsers.UserXMLParseHandler;

public class StaticData{

	//Preferences
	SharedPreferences prefs;
	String username, password;

	//Context
	Context context;

	//Constants
	String user1 = "Bluebot";
	String user2 = "GameZombie";
	String user3 = "Silverfire11";
	String user4 = "Greenbot";

	String pass1 = "7e45k";
	String pass2 = "kwcp6";
	String pass3 = "8pd56";
	String pass4 = "ho3xz";

	//XML File Sources
	public String user1File = 						"assets/user1/user.xml";
	public String user1ChallengeRequestsFile = 		"assets/user1/challenge_requests.xml";
	public String user1FriendRequestsFile = 		"assets/user1/friend_requests.xml";
	public String user1FriendsFile =		 		"assets/user1/friends.xml";
	public String user1GamesFile = 					"assets/user1/games.xml";
	public String user1TrophiesFile = 				"assets/user1/trophies.xml";

	public String user2File = 						"assets/user2/user.xml";
	public String user2ChallengeRequestsFile = 		"assets/user2/challenge_requests.xml";
	public String user2FriendRequestsFile = 		"assets/user2/friend_requests.xml";
	public String user2FriendsFile =		 		"assets/user2/friends.xml";
	public String user2GamesFile = 					"assets/user2/games.xml";
	public String user2TrophiesFile = 				"assets/user2/trophies.xml";

	public String user3File = 						"assets/user3/user.xml";
	public String user3ChallengeRequestsFile = 		"assets/user3/challenge_requests.xml";
	public String user3FriendRequestsFile = 		"assets/user3/friend_requests.xml";
	public String user3FriendsFile =		 		"assets/user3/friends.xml";
	public String user3GamesFile = 					"assets/user3/games.xml";
	public String user3TrophiesFile = 				"assets/user3/trophies.xml";

	public String user4File = 						"assets/user4/user.xml";
	public String user4ChallengeRequestsFile = 		"assets/user4/challenge_requests.xml";
	public String user4FriendRequestsFile = 		"assets/user4/friend_requests.xml";
	public String user4FriendsFile =		 		"assets/user4/friends.xml";
	public String user4GamesFile = 					"assets/user4/games.xml";
	public String user4TrophiesFile = 				"assets/user4/trophies.xml";

	public String systemGamesFile = 				"assets/system/system_games.xml";
	public String systemUsersFile = 				"assets/system/system_users.xml";
	public String serversFile = 					"assets/system/servers.xml";

	String userFile, challengeRequestsFile, friendRequestsFile, gamesFile, trophiesFile, friendsFile;

	//Data Objects
	public List<User> appUsers = null;
	public List<User> users = null;
	public List<ChallengeRequest> challengeRequests = null;
	public List<FriendRequest> friendRequests = null;
	public List<Game> games = null;
	public List<Server> servers = null;
	public List<Trophy> trophies = null;	

	InputStream in;

	public User authenticate(String username, String password)
	{
		if((username.equals(user1)&&password.equals(pass1))
				|| (username.equals(user2)&&password.equals(pass2))
				|| (username.equals(user3)&&password.equals(pass3))
				|| (username.equals(user4)&&password.equals(pass4)))
		{
			this.username = username;
			this.password = password;
			setUserFiles();
			return loadAppUser();
		}
		else
			return new User("invalid");
	}

	public StaticData(Context context)
	{
		prefs = context.getApplicationContext().getSharedPreferences("APP_USER", Context.MODE_PRIVATE);
		username = prefs.getString("username", "");
		password = prefs.getString("password", "");
		setUserFiles();
	}

	public User loadAppUser()
	{
		UserXMLParseHandler appUserParser = new UserXMLParseHandler();
		User appUser;

		in = this.getClass().getClassLoader().getResourceAsStream(userFile);		
		appUsers = appUserParser.parse(in);
		appUser = appUsers.get(0);

		//load friends
		appUser.setFriend(loadFriends(appUser.getName()));

		//load trophies
		appUser.setTrophy(loadTrophies(appUser.getName()));

		//load games
		appUser.setGame(loadGames(appUser.getName()));

		//load challenge requests
		appUser.setChallengeRequest(loadChallengeRequests(appUser.getName()));

		//load friend requests
		appUser.setFriendRequest(loadFriendRequests(appUser.getName()));
		
		return appUsers.get(0);
	}

	public List<Game> loadGames(String username)
	{
		GameXMLParseHandler gameParser = new GameXMLParseHandler();
		if(username.equals(user1))
			in = this.getClass().getClassLoader().getResourceAsStream(user1GamesFile);	
		else if(username.equals(user2))
			in = this.getClass().getClassLoader().getResourceAsStream(user2GamesFile);
		else if(username.equals(user3))
			in = this.getClass().getClassLoader().getResourceAsStream(user3GamesFile);
		else if(username.equals(user4))
			in = this.getClass().getClassLoader().getResourceAsStream(user4GamesFile);

		games = gameParser.parse(in);
		return games;
	}

	public List<Trophy> loadTrophies(String username)
	{
		TrophyXMLParseHandler trophyParser = new TrophyXMLParseHandler();
		if(username.equals(user1))
			in = this.getClass().getClassLoader().getResourceAsStream(user1TrophiesFile);	
		else if(username.equals(user2))
			in = this.getClass().getClassLoader().getResourceAsStream(user2TrophiesFile);
		else if(username.equals(user3))
			in = this.getClass().getClassLoader().getResourceAsStream(user3TrophiesFile);
		else if(username.equals(user4))
			in = this.getClass().getClassLoader().getResourceAsStream(user4TrophiesFile);

		trophies = trophyParser.parse(in);
		return trophies;
	}

	public List<User> loadFriends(String username)
	{
		UserXMLParseHandler userParser = new UserXMLParseHandler();
		in = this.getClass().getClassLoader().getResourceAsStream(friendsFile);	

		users = userParser.parse(in);
		
		for(User user : users)
		{
			String name = user.getName();
			
			user.setGame(loadGames(name));
			user.setTrophy(loadTrophies(name));
			
		}
		
		return users;
	}

	public List<ChallengeRequest> loadChallengeRequests(String username)
	{
		ChallengeRequestXMLParseHandler challengeRequestParser = new ChallengeRequestXMLParseHandler();
		in = this.getClass().getClassLoader().getResourceAsStream(challengeRequestsFile);		

		challengeRequests = challengeRequestParser.parse(in);

		for(ChallengeRequest request : challengeRequests)
		{
			request.setGame(getGameForChallengeRequest(request)); 
		}

		return challengeRequests;
	}

	public List<FriendRequest> loadFriendRequests(String username)
	{
		FriendRequestXMLParseHandler friendRequestParser = new FriendRequestXMLParseHandler();
		in = this.getClass().getClassLoader().getResourceAsStream(friendRequestsFile);		

		friendRequests = friendRequestParser.parse(in);
		return friendRequests;
	}

	//FIXME this is not called anywhere. test the function to be used.
	public List<Game> loadSystemGames()
	{

		GameXMLParseHandler gameParser = new GameXMLParseHandler();
		in = this.getClass().getClassLoader().getResourceAsStream(systemGamesFile);

		games = gameParser.parse(in);
		return games;
	}

	//FIXME this is not called anywhere. test the function to be used.
	public List<User> loadSystemUsers()
	{

		UserXMLParseHandler userParser = new UserXMLParseHandler();
		in = this.getClass().getClassLoader().getResourceAsStream(systemUsersFile);	

		users = userParser.parse(in);
		return users;
	}

	//FIXME this is not called anywhere. test the function to be used.
	public List<Server> loadServers()
	{
		ServerXMLParseHandler serverParser = new ServerXMLParseHandler();
		in = this.getClass().getClassLoader().getResourceAsStream(serversFile);	

		servers = serverParser.parse(in);
		return servers;
	}

	//FIXME this is not called anywhere. test the function to be used.
	public Game getGameForChallengeRequest(ChallengeRequest request)
	{
		List<Game> sysGames = loadSystemGames();
		for(int i=0; i<sysGames.size(); i++)
		{
			if(request.getGame().getTitle().equals(sysGames.get(i).getTitle()))
				return sysGames.get(i);
		}

		return request.getGame();
	}

	public String getUserFile()
	{
		if(username.equals(user1))
			return user1File;
		else if(username.equals(user2))
			return user2File;
		else if(username.equals(user3))
			return user3File;
		else if(username.equals(user4))
			return user4File;
		else return "";
	}

	public String getGamesFile()
	{
		if(username.equals(user1))
			return user1GamesFile;
		else if(username.equals(user2))
			return user2GamesFile;
		else if(username.equals(user3))
			return user3GamesFile;
		else if(username.equals(user4))
			return user4GamesFile;
		else return "";
	}

	public String getTrophiesFile()
	{
		if(username.equals(user1))
			return user1TrophiesFile;
		else if(username.equals(user2))
			return user2TrophiesFile;
		else if(username.equals(user3))
			return user3TrophiesFile;
		else if(username.equals(user4))
			return user4TrophiesFile;
		else return "";
	}

	public String getFriendsFile()
	{
		if(username.equals(user1))
			return user1FriendsFile;
		else if(username.equals(user2))
			return user2FriendsFile;
		else if(username.equals(user3))
			return user3FriendsFile;
		else if(username.equals(user4))
			return user4FriendsFile;
		else return "";
	}

	public String getChallengeRequestsFile()
	{
		if(username.equals(user1))
			return user1ChallengeRequestsFile;
		else if(username.equals(user2))
			return user2ChallengeRequestsFile;
		else if(username.equals(user3))
			return user3ChallengeRequestsFile;
		else if(username.equals(user4))
			return user4ChallengeRequestsFile;
		else return "";
	}

	public String getFriendRequestsFile()
	{
		if(username.equals(user1))
			return user1FriendRequestsFile;
		else if(username.equals(user2))
			return user2FriendRequestsFile;
		else if(username.equals(user3))
			return user3FriendRequestsFile;
		else if(username.equals(user4))
			return user4FriendRequestsFile;
		else return "";
	}

	public void setUserFiles()
	{
		userFile = getUserFile();
		gamesFile = getGamesFile();
		trophiesFile = getTrophiesFile();
		friendsFile = getFriendsFile();
		challengeRequestsFile = getChallengeRequestsFile();
		friendRequestsFile = getFriendRequestsFile();
	}

}
