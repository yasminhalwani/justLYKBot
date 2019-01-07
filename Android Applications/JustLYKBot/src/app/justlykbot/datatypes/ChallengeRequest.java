package app.justlykbot.datatypes;

import java.io.Serializable;
import java.util.Date;

public class ChallengeRequest extends Request implements Serializable{

	private Game game;


	public ChallengeRequest(int requestId, User sender, User receiver, Game game,
			String dateAndTimeSent, String status) {
		

		this.game = game;

	}
	
	public ChallengeRequest()
	{
		super();
	}

	public Game getGame() {
		return game;
	}
	public void setGame(String gamename) {
		
		this.game = new Game(gamename);

	}
	
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	@Override
	public String toString()
	{
		return sender.getName()+" is challenging you to play "+game.getTitle()+"!\n"+
				"date sent: "+dateAndTimeSent;
	}

}
