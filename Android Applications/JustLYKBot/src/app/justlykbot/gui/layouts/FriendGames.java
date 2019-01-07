package app.justlykbot.gui.layouts;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.listadapters.GameAdapter;
import app.justlykbot.localdata.DataLoader;

public class FriendGames extends Activity {
		
	//Layout variables
	ListView gamesList;

	//Data variables
	User friend;
	User appUser;
	DataLoader data;
	List<Game> games;
	
	FriendGames handle = this;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		
		Intent intent = getIntent();
		friend = (User) intent.getSerializableExtra("friendObject");
		appUser = (User) intent.getSerializableExtra("appUser");
		data = Login.data;

		loadGamesList();
		guiSetup();
	}
	
	public void loadGamesList()
	{
		games = friend.getGame();
	}

	public void guiSetup()
	{
		gamesList = (ListView) findViewById(R.id.list_listView_items);

		GameAdapter adapter = new GameAdapter
				(this, R.layout.list_item, games);
		gamesList.setAdapter(adapter);
		
		gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {				

				final int location = position;
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked
				        	data.sendChallengeRequest(appUser.getName(), friend.getName(), games.get(location).getTitle());
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked				        	
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(handle);
				builder.setMessage(getResources().getString(R.string.challenge_request_prompt))
				.setPositiveButton(getResources().getString(R.string.message_yes), dialogClickListener)
				    .setNegativeButton(getResources().getString(R.string.message_no), dialogClickListener).show();

			}
		});

	
	}

}
