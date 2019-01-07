package app.justlykbot.gui.layouts;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import app.justlykbot.R;
import app.justlykbot.datatypes.Trophy;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.listadapters.TrophyAdapter;
import app.justlykbot.localdata.DataLoader;

public class ProfileTab {
	
	//GUI variables
	View rootView;	
	ListView trophiesList;
	ImageView userDP;
	TextView username;
	TextView noTrophiesMessage;
	Button logout;

	//Data variables
	User appUser;
	DataLoader data;
	List<Trophy> trophies;
	
	
	public View loadContent(View rootView, User appUser, DataLoader data)
	{
		this.rootView = rootView;

		guiSetup();
		
		this.appUser = appUser;
		this.data = data;
		
		username.setText(appUser.getName());
		userDP.setImageResource(R.drawable.new_item); //FIXME
		
		loadTrophies();
		if(trophies.size()!=0)
		{
			displayTrophies();
			noTrophiesMessage.setVisibility(View.INVISIBLE);
		}

		return rootView;
	}
	
	public void loadTrophies()
	{
		trophies = appUser.getTrophy();
	}

	public void displayTrophies()
	{
        TrophyAdapter adapter = new TrophyAdapter
        (rootView.getContext(), R.layout.list_item, trophies);
        trophiesList.setAdapter(adapter);
	}
	
	public void guiSetup()
	{
		trophiesList = (ListView) rootView.findViewById(R.id.profile_listView_trophies);
		username = (TextView) rootView.findViewById(R.id.profile_textView_appUserName);
		userDP = (ImageView) rootView.findViewById(R.id.profile_imageView_appUserImage);
		noTrophiesMessage = (TextView) rootView.findViewById(R.id.profile_textView_noTrophiesMessage);
		logout = (Button) rootView.findViewById(R.id.profile_button_logout);
		
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SharedPreferences prefs = rootView.getContext().getSharedPreferences("APP_USER", Context.MODE_PRIVATE);
				SharedPreferences.Editor prefsEditor = prefs.edit();
				prefsEditor.putString("username", "NONE").commit();
				
				SharedPreferences dataModePrefs = rootView.getContext().getSharedPreferences("DATA_MODE", Context.MODE_PRIVATE);
				SharedPreferences.Editor dataModePrefsEditor = prefs.edit();
				prefsEditor.putString("mode", "NONE").commit();
				
				// disconnectFromServers(); //TODO
				
				((JustLYKBot) rootView.getContext()).finish();
				
			}
		});
		
		//int resID = rootView.getContext().getResources().getIdentifier(appUser.getImageResourceId(), "id", "app.justlykbot");
		//userDP.setImageResource(resID);
		
	}

	public User getAppUser()
	{
		return null; //FIXME
	}


}
