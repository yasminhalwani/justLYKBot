package app.justlykbot.localdata;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.ChallengeRequest;
import app.justlykbot.datatypes.FriendRequest;
import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.Server;
import app.justlykbot.datatypes.Trophy;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.layouts.Login;
import app.justlykbot.network.HTTPClient;

public class DataLoader {

	//Data Objects
	public User appUser = null;
	public List<ChallengeRequest> challengeRequests = null;
	public List<FriendRequest> friendRequests = null;
	public List<Game> games = null;
	public List<Server> servers = null;
	public List<Trophy> trophies = null;

	//Data Connections
	StaticData staticData;
	HTTPClient httpClient;
	Context context;



	public DataLoader(Context context)
	{		
		staticData = new StaticData(context);
		httpClient = new HTTPClient();

		appUser = new User("invalid");
		this.context = context;
	}

	public boolean authenticate(String username, String password)
	{
		if(Login.isDataStatic)
		{
			appUser = staticData.authenticate(username, password);
		}else
		{
			appUser = httpClient.authenticate(username, password);
		}

		if(appUser.getName().equals("invalid"))
			return false;

		else return true;
	}

	public List<Server> loadServers()
	{
		if(Login.isDataStatic)
		{
			return staticData.loadServers();
		}else		
			return null; //FIXME
	}

	public User getAppUser()
	{
		return appUser;
	}

	public List<ChallengeRequest> getChallengeRequestsOfUser(String username)
	{
		if(Login.isDataStatic)
			return staticData.loadChallengeRequests(username);
		else
			return null; //FIXME
	}

	public List<FriendRequest> getFriendRequestsOfUser(String username)
	{
		if(Login.isDataStatic)
			return staticData.loadFriendRequests(username);
		else
			return null; //FIXME
	}

	public List<Trophy> getTrophiesOfUser(String username)
	{
		if(Login.isDataStatic)
			return staticData.loadTrophies(username);
		else
			return null; //FIXME
	}

	public List<Game> getGamesOfUser(String username)
	{
		if(Login.isDataStatic)
			return staticData.loadGames(username);
		else
			return null;
	}

	public List<User> getFriendsOfUser(String username)
	{
		if(Login.isDataStatic)
			//TODO load more info on friends
			return staticData.loadFriends(username);
		else
			return null; //FIXME
	}

	public List<Game> getSystemGames()
	{
		if(Login.isDataStatic)
			return staticData.loadSystemGames();
		else
			return null; //FIXME
	}

	public List<User> getSystemUsers()
	{
		if(Login.isDataStatic)
			return staticData.loadSystemUsers();
		else
			return null; //FIXME
	}

	public void respondToFriendRequest(int requestId, String response)
	{
		if(Login.isDataStatic)
			showStaticDataErrorMessage();
		else
			httpClient.respondToFriendRequest(requestId, response);
	}

	public void addGameByUser(String username, String gameName)
	{
		if(Login.isDataStatic)
			showStaticDataErrorMessage();
		else
			httpClient.addGameByUser(username, gameName);
	}

	public void sendFriendRequest(String senderName, String receiverName)
	{
		if(Login.isDataStatic)
			showStaticDataErrorMessage();
		else
			httpClient.sendFriendRequest(senderName, receiverName);
	}

	public void sendChallengeRequest(String senderName, String receiverName, String gameName)
	{
		if(Login.isDataStatic)
			showStaticDataErrorMessage();
		else
			httpClient.sendChallengeRequest(senderName, receiverName, gameName);
	}

	public void updateScore(String username, String game, String scoreInSession)
	{
		if(Login.isDataStatic)
			showStaticDataErrorMessage();
		else
			httpClient.updateScore(username, game, scoreInSession);
	}

	public void unfriend(String username, String friendName)
	{
		if(Login.isDataStatic)
			showStaticDataErrorMessage();
		else
			httpClient.unfriend(username, friendName);
	}

	public void deleteGame(String username, String gameName)
	{
		if(Login.isDataStatic)
			showStaticDataErrorMessage();
		else
			httpClient.deleteGame(username, gameName);
	}

	public void test()
	{
		Toast.makeText(context, "Data Loader is here!", Toast.LENGTH_SHORT).show();
	}

	public void showStaticDataErrorMessage()

	{
		Toast.makeText(context, context.getResources().getString(R.string.static_data_error), Toast.LENGTH_LONG).show();
	}

}
