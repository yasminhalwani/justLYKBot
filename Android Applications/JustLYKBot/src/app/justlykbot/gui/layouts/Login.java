package app.justlykbot.gui.layouts;

import java.io.Serializable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.User;
import app.justlykbot.localdata.DataLoader;
import app.justlykbot.network.HTTPClient;

public class Login extends Activity implements Serializable{

	//Layout
	Button loginButton;
	EditText usernameEditText, passwordEditText;

	//Data
	String username, password;
	User appUser;
	public static DataLoader data;

	//Preferences
	SharedPreferences dataPref, userPref;
	SharedPreferences.Editor dataPrefEditor, userPrefEditor;
	public static boolean isDataStatic = true;
	static boolean isUserLoggedIn = false;

	//NetworkConnection
	HTTPClient httpClient;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		data = new DataLoader(this);

		guiSetup();
		preferencesSetup();
		gameServerConnectionSetup();
		buttonsSetup();
		
		if(isUserLoggedIn)
		{
			login();
			finish();
		}

	}


	public void guiSetup()
	{
		usernameEditText = (EditText) findViewById(R.id.login_editText_username);
		passwordEditText = (EditText) findViewById(R.id.login_editText_password);

		loginButton = (Button) findViewById(R.id.login_button_login);

	}

	public void preferencesSetup()
	{
		userPref = getSharedPreferences("APP_USER", Context.MODE_PRIVATE);
		userPrefEditor = userPref.edit();

		dataPref = getSharedPreferences("DATA_MODE", Context.MODE_PRIVATE);
		dataPrefEditor = dataPref.edit();
		
		if(userPref.getString("username", "").equals("NONE"))
			isUserLoggedIn = false;
		else
		{
			isUserLoggedIn = true;
			
			username = userPref.getString("username", "");
			password = userPref.getString("password", "");
			
			if(dataPref.getString("mode", "").equals("dynamic"))
				isDataStatic = false;
			else isDataStatic = true;
		}
		
		
			

	}

	public void gameServerConnectionSetup()
	{
		if(!isUserLoggedIn)
		{
			httpClient = new HTTPClient();
			if(!httpClient.isServerUp())
			{
				AlertDialog ad = new AlertDialog.Builder(this).create();  
				ad.setCancelable(false); // This blocks the 'BACK' button  
				ad.setMessage(getResources().getString(R.string.game_server_down));  
				ad.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  
					@Override  
					public void onClick(DialogInterface dialog, int which) {  
						dataPrefEditor.putString("mode", "static").commit();
						isDataStatic = true;
						dialog.dismiss();                      
					}  
				});  
				ad.show();
			}else
			{
				dataPrefEditor.putString("mode", "dynamic").commit();
				isDataStatic = false;
			}
		}

	}

	public void buttonsSetup()
	{
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				username = usernameEditText.getText().toString();
				password = passwordEditText.getText().toString();
				login();
			}
		});
	}

	public void login()
	{
		if(authenticateUser(username, password))
		{
			Intent justLykBotActivity = new Intent(Login.this, JustLYKBot.class); //FIXME
			//Intent justLykBotActivity = new Intent(Login.this, Test.class);
			appUser = data.getAppUser();

			userPrefEditor.putString("username", username).commit();
			userPrefEditor.putString("password", password).commit();

			justLykBotActivity.putExtra("appUser", appUser);
			startActivity(justLykBotActivity);
			finish();



		}else
			Toast.makeText(getApplicationContext(), R.string.incorrect_login, Toast.LENGTH_SHORT).show();

	}

	public boolean authenticateUser(String username, String password)
	{
		return data.authenticate(username, password);
	}



}
