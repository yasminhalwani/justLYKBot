package app.justlykbot.gui.layouts;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import app.justlykbot.R;
import app.justlykbot.datatypes.Trophy;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.listadapters.TrophyAdapter;
import app.justlykbot.localdata.DataLoader;

public class FriendTrophies extends Activity{

	//Layout variables
	ListView trophiesList;

	//Data variables
	User friend;
	DataLoader data;
	List<Trophy> trophies;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		Intent intent = getIntent();
		friend = (User) intent.getSerializableExtra("friendObject");
		data = Login.data;

		loadTrophiesList();
		guiSetup();

	}

	public void loadTrophiesList()
	{
		trophies = friend.getTrophy();
	}

	public void guiSetup()
	{
		trophiesList = (ListView) findViewById(R.id.list_listView_items);

		TrophyAdapter adapter = new TrophyAdapter
				(this, R.layout.list_item, trophies);
		trophiesList.setAdapter(adapter);

	}
}
