package app.justlykbot.gui.layouts;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import app.justlykbot.R;
import app.justlykbot.datatypes.Game;
import app.justlykbot.datatypes.User;
import app.justlykbot.gui.listadapters.GameAdapter;
import app.justlykbot.localdata.DataLoader;

public class GamesTab {

	//GUI variables
	View rootView;	
	View footerView;
	ListView gamesList;

	//Data variables
	User appUser;
	DataLoader data;
	List<Game> games;

	public View loadContent(View rootView, View footerView, User appUser, DataLoader data)
	{
		this.rootView = rootView;
		this.footerView = footerView;

		guiSetup();
		
		this.appUser = appUser;
		this.data = data;

		loadGames();
		displayGames();


		return rootView;
	}

	public void loadGames()
	{
		games = appUser.getGame();
	}

	public void displayGames()
	{
        GameAdapter adapter = new GameAdapter
        (rootView.getContext(), R.layout.list_item, R.layout.add_more_footer, games);
        gamesList.setAdapter(adapter);
        
        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {				

				goToGameOverview(games.get(position));

			}
		});
	}

	public void guiSetup()
	{
		gamesList = (ListView) rootView.findViewById(R.id.games_listView_games);
		gamesList.addFooterView(footerView);
		
		footerView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(rootView.getContext(), GamesList.class);
				intent.putExtra("appUser", appUser);
				rootView.getContext().startActivity(intent);
				
			}
		});
	}
	
	public void goToGameOverview(Game game)
	{
		Intent intent = new Intent(rootView.getContext(), GameOverview.class);
		intent.putExtra("game", game);
		intent.putExtra("appUser", appUser);
		rootView.getContext().startActivity(intent);
	}


}
