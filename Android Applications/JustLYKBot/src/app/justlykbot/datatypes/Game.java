package app.justlykbot.datatypes;

import java.io.Serializable;

import android.app.Activity;
import android.widget.Button;

public class Game implements Serializable {


	private String title;
	private String gamePackage;
	private String downloadURL;
	private String appName;
	private String description;
	private String extraInstruction;
	private String dpURL;
	protected String imageResourceId;

	
	public Game()
	{
		
	}
	
	public Game(String title)
	{
		this.title = title;
	}

	//constructor used for local user
	public Game(int icon, String title, String gamePackage, String downloadURL, String appName) {
		super();
		
		this.title = title;
		this.gamePackage = gamePackage;
		this.downloadURL = downloadURL;
		this.appName = appName;
		this.extraInstruction="";
		this.description="";
	}
	
	//constructor used for user's friends 
	public Game(int icon, String title)
	{
		super();
		
		this.title = title;
	}
	
	public String getAppName()
	{
		return this.appName;
	}
	public String getGameTitle()
	{
		return this.title;
	}
	
	public String getGamePackage()
	{
		return this.gamePackage;
	}
	
	public String getDownloadURL()
	{
		return this.downloadURL;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExtraInstruction() {
		return extraInstruction;
	}

	public void setExtraInstruction(String extraInstruction) {
		this.extraInstruction = extraInstruction;
	}

	public void setGamePackage(String gamePackage) {
		this.gamePackage = gamePackage;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDpURL() {
		return dpURL;
	}

	public void setDpURL(String dpURL) {
		this.dpURL = dpURL;
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
		return "title: "+this.getGameTitle()+"\n"
				+ "package: "+this.getGamePackage()+"\n"
				+ "download link: "+this.getDownloadURL()+"\n"
				+ "app name: "+this.getAppName();
	}

}
