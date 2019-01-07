package app.justlykbot.gui.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.User;
import app.justlykbot.localdata.DataLoader;

public class GameOverview extends Activity{


	//Layout variables
	TextView gameTitle, gameDescription;
	ImageView gameIcon;
	Button start, moreDetails, deleteGame;

	//Data variables
	User appUser;
	Game game;
	DataLoader data;

	GameOverview handle = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_overview);

		// receive info from caller class
		Intent intent = getIntent();
		appUser = (User) intent.getSerializableExtra("appUser");
		game = (Game) intent.getSerializableExtra("game");
		data = Login.data;

		guiSetup();

		gameTitle.setText(game.getTitle());
		gameDescription.setText(game.getDescription());

		buttonsSetup();



	}

	public void guiSetup()
	{
		gameTitle = (TextView) findViewById(R.id.gameOverview_TextView_gameTitle);
		gameDescription = (TextView) findViewById(R.id.gameOverview_TextView_gameDescription);
		gameIcon = (ImageView) findViewById(R.id.gameOverview_ImageView_gameIcon);
		start = (Button) findViewById(R.id.gameOverview_Button_startGame);
		moreDetails = (Button) findViewById(R.id.gameOverview_Button_viewMore);
		deleteGame = (Button) findViewById(R.id.gameOverview_Button_deleteGame);
	}


	public void buttonsSetup()

	{
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goToGame();
			}
		});

		moreDetails.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				viewGameExtraInstructions();
			}
		});

		deleteGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				data.deleteGame(appUser.getName(), game.getTitle());
			}
		});
	}

	public void goToGame()
	{
		Intent intent = new Intent(GameOverview.this, LoadGame.class);
		intent.putExtra("game", game);
		startActivity(intent);
	}

	public void viewGameExtraInstructions()
	{
		if(game.getExtraInstruction().equals("-") || game.getExtraInstruction().equals(null)){
			Toast.makeText(getApplicationContext(), 
					getResources().getString(R.string.no_instructions), Toast.LENGTH_LONG).show();
		}
		else
		{
			AlertDialog ad = new AlertDialog.Builder(handle).create();  
			ad.setCancelable(false); // This blocks the 'BACK' button  
			ad.setMessage(game.getExtraInstruction());  
			ad.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  
				@Override  
				public void onClick(DialogInterface dialog, int which) {  
					dialog.dismiss();                      
				}  
			});  
			ad.show();
		}
	}
}
