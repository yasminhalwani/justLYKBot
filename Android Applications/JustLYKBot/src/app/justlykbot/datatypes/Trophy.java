package app.justlykbot.datatypes;

import java.io.Serializable;

import android.app.Activity;

public class Trophy implements Serializable{
	private String name;
	private Game game;
	private String description;

	public Trophy()
	{
		
	}

	public Trophy(String name, Game game) {
		this.name = name;
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "----["+name+"]----" + "\nGame: "+ game.getGameTitle()+
				"\nDescription: "+description;
	}


}