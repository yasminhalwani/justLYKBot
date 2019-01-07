package app.justlykbot.gui.layouts;


import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.User;
import app.justlykbot.localdata.DataLoader;

public class FriendProfile extends Activity{

	//Layout Variables
	Button voipBtn, imBtn, showTrophiesBtn, showGamesBtn, unfriendBtn;
	TextView username, presence;
	ImageView userImage;

	//Data Variables
	User appUser;
	User friend;
	DataLoader data;

	//Network variables
	XMPPConnection connection;

	FriendProfile handle = this;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_profile);

		Intent intent = getIntent();
		friend = (User) intent.getSerializableExtra("friendObject");
		appUser = (User) intent.getSerializableExtra("appUser");
		data = Login.data;

		guiSetup();	
		getPresence();
		buttonsSetup();
		networkSetup();


	}

	public void networkSetup()
	{
		connection = JustLYKBot.xmppConnection;
	}

	public void guiSetup()
	{
		String trophiesBtnText = "";
		String gamesBtnText = "";
		int trophiesCount, gamesCount;

		username = (TextView) findViewById(R.id.friendProfile_textView_username);
		presence = (TextView) findViewById(R.id.friendProfile_textView_presence);
		userImage = (ImageView) findViewById(R.id.friendProfile_imageView_friendImage);
		voipBtn = (Button) findViewById(R.id.friendProfile_Button_voip);
		imBtn = (Button) findViewById(R.id.friendProfile_Button_im);
		showTrophiesBtn = (Button) findViewById(R.id.friendProfile_Button_trophies);
		showGamesBtn = (Button) findViewById(R.id.friendProfile_Button_games);
		unfriendBtn = (Button) findViewById(R.id.friendProfile_Button_unfriend);

		username.setText(friend.getName());
		userImage.setImageResource(R.drawable.new_item); //FIXME

		trophiesCount = countFriendTrophies();
		gamesCount = countFriendGames();

		if(trophiesCount>1)
			trophiesBtnText = friend.getName()+" has "+trophiesCount+" trophies";
		else if(trophiesCount==1)
			trophiesBtnText = friend.getName()+" has "+trophiesCount+" trophy";
		else
			showTrophiesBtn.setVisibility(View.INVISIBLE);

		if(gamesCount>1)
			gamesBtnText = friend.getName()+" has "+gamesCount+" games";
		else if(gamesCount==1)
			gamesBtnText = friend.getName()+" has "+gamesCount+" game";		
		else
			showGamesBtn.setVisibility(View.INVISIBLE);

		showTrophiesBtn.setText(trophiesBtnText);
		showGamesBtn.setText(gamesBtnText);

	}

	public void buttonsSetup()
	{
		showTrophiesBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				viewTrophiesList();				
			}
		});

		showGamesBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				viewGamesList();

			}
		});

		unfriendBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				data.unfriend(appUser.getName(), friend.getName());
			}
		});
		
		imBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(JustLYKBot.xmppConnectionError)
				{
					AlertDialog ad = new AlertDialog.Builder(handle).create();  
					ad.setCancelable(false); // This blocks the 'BACK' button  
					ad.setMessage(getResources().getString(R.string.connection_error_xmpp));  
					ad.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  
						@Override  
						public void onClick(DialogInterface dialog, int which) {  
							dialog.dismiss();                      
						}  
					});  
					ad.show();
				}else
				sendMessage();			
			}
		});
	}

	public int countFriendTrophies()
	{		
		return friend.getTrophy().size(); 
	}

	public int countFriendGames()
	{
		return friend.getGame().size();
	}

	public void getPresence()
	{
		//TODO
	}

	public void initiateCall()
	{

	}

	public void sendMessage()
	{
		Intent x = new Intent(this, ChatClient.class);
		x.putExtra("FriendObject", friend);
		x.putExtra("UserObject", appUser);
		startActivity(x);
	}

	public void viewTrophiesList()
	{
		Intent friendTrophiesIntent = new Intent(FriendProfile.this, FriendTrophies.class);
		friendTrophiesIntent.putExtra("friendObject", friend);
		this.startActivity(friendTrophiesIntent);
	}

	public void viewGamesList()
	{
		Intent friendGamesIntent = new Intent(FriendProfile.this, FriendGames.class);
		friendGamesIntent.putExtra("friendObject", friend);
		friendGamesIntent.putExtra("appUser", appUser);
		this.startActivity(friendGamesIntent);
	}

}
